package com.unlimit.chiragtest.fragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.unlimit.chiragtest.R
import com.unlimit.chiragtest.adapter.JokesListAdapter
import com.unlimit.chiragtest.model.JokesData
import com.unlimit.chiragtest.network.CallResponseStatus
import com.unlimit.chiragtest.viewmodel.JokesViewModel
import kotlinx.android.synthetic.main.fragment_layout_jokes.*
import java.util.*
import kotlin.collections.ArrayList
import com.unlimit.chiragtest.database.JokesTable


class JokesFragment : Fragment() {
    private var jokeListAdapter: JokesListAdapter? = null
    private var jokeList = ArrayList<JokesData>()
    private var jokeViewModel: JokesViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout_jokes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
        retrieveRepositories()
    }


    private fun bindView() {
        setJokeObservers()
    }

    private fun setJokeObservers() {
        jokeViewModel?.repository?.allJokeRepos?.observe(
            viewLifecycleOwner,
            Observer<List<JokesTable>> { it ->
                it?.let { output ->
                    progressbar.visibility = View.GONE
                    it.let {
                        if (it.isNotEmpty()) {
                            for (i in it) {
                                jokeList.add(JokesData(i.joke))
                            }
                            jokeListAdapter?.submitList(jokeList.toList())
                        }
                    }
                }
            })

        jokeViewModel?.getJokeLiveData()?.observe(
            viewLifecycleOwner,
            Observer<CallResponseStatus<JokesData>> { it ->
                it?.let { output ->
                    when (output.status) {
                        CallResponseStatus.Status.SUCCESS -> {
                            progressbar.visibility = View.GONE
                            it.data?.let {
                                if (jokeList.size > 9) {
                                    jokeList.removeAt(jokeList.size - 1)
                                    jokeList.add(0, it)
                                    jokeListAdapter?.submitList(jokeList.toList())
                                    listView.smoothScrollToPosition(0)
                                } else {
                                    jokeList.add(it)
                                    jokeListAdapter?.submitList(jokeList.toList())
                                }
                            }

                        }
                        CallResponseStatus.Status.ERROR -> {
                            progressbar.visibility = View.GONE
                            setErrorMessage(it.message)
                        }
                    }
                }
            })


    }


    private fun setErrorMessage(it: String?) {
        Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
    }


    private fun initView() {
        jokeViewModel = ViewModelProviders.of(this).get(JokesViewModel::class.java)
        jokeListAdapter = JokesListAdapter()
        val linearLayoutManager = LinearLayoutManager(activity)
        listView.layoutManager = linearLayoutManager
        listView.adapter = jokeListAdapter
    }

    private fun retrieveRepositories() {
        val handler = Handler()
        val timer = Timer()
        val doAsynchronousTask: TimerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    progressbar.visibility = View.VISIBLE
                    jokeViewModel?.getJokesList()
                }
            }
        }
        timer.schedule(doAsynchronousTask, 0, 60000)
    }

    override fun onDestroy() {
        super.onDestroy()
        for (i in jokeList) {
            val jokeTableData = i.joke?.let { JokesTable(it) }
            jokeTableData?.let { jokeViewModel?.insertJokesData(it) }
        }
    }
}
