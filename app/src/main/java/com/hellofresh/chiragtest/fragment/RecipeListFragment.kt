package com.hellofresh.chiragtest.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import com.hellofresh.chiragtest.R
import com.hellofresh.chiragtest.interfaces.LaunchFragmentInterface
import com.hellofresh.chiragtest.adapter.RecipeListAdapter
import com.hellofresh.chiragtest.database.RecipeTable
import com.hellofresh.chiragtest.model.RecipeData
import com.hellofresh.chiragtest.network.CallResponseStatus
import com.hellofresh.chiragtest.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_recipie_list.*

class RecipeListFragment : Fragment(), RecipeListAdapter.OnClickRecipeInterface {
    private var recipeListAdapter: RecipeListAdapter? = null
    private var recipeList = ArrayList<RecipeData>()
    private var recipeViewModel: RecipeViewModel? = null
    private var launchFragmentInterface: LaunchFragmentInterface? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
        retrieveRepositories()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LaunchFragmentInterface) {
            launchFragmentInterface = context
        }
    }

    private fun bindView() {
        setRecipeObserver()
    }

    private fun setRecipeObserver() {
        recipeViewModel?.recipeMutableLiveData?.observe(
            viewLifecycleOwner,
            Observer<CallResponseStatus<List<RecipeData>>> {
                it?.let { output ->
                    when (output.status) {
                        CallResponseStatus.Status.SUCCESS -> {
                            progressbar.visibility = View.GONE
                            it.data?.let {
                                recipeList = it as ArrayList<RecipeData>
                                setFavStatusObserver()
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

    private fun setFavStatusObserver() {
        recipeViewModel?.repository?.allRepos?.observe(
            viewLifecycleOwner,
            Observer<List<RecipeTable>> {
                it?.let {
                    recipeList = getStatusFromDatabase(
                        recipeList,
                        it.associate { it.id to it.isFavourite } as HashMap<String, Boolean>)
                }
                setAdapter(recipeList)
            })
    }


    private fun setErrorMessage(it: String?) {
        activity?.let { it1 ->
            AlertDialog.Builder(it1).setTitle(R.string.error)
                .setMessage(it)
                .setPositiveButton(android.R.string.ok) { _, _ -> activity?.finish() }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun setAdapter(it: List<RecipeData>) {
        recipeListAdapter?.dataSetChanged(it)

    }

    private fun initView() {
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        recipeListAdapter = RecipeListAdapter(recipeList, this)
        val linearLayoutManager = LinearLayoutManager(activity)
        listView.layoutManager = linearLayoutManager
        listView.adapter = recipeListAdapter
    }

    private fun retrieveRepositories() {
        progressbar.visibility = View.VISIBLE
        recipeViewModel?.getRecipeList()
    }

    override fun onClick(repoDto: RecipeData?) {
        val bundle = Bundle()
        bundle.putParcelable(RecipeDetailFragment.KEY_RECIPE_DATA, repoDto)
        launchFragmentInterface?.launchFragment(bundle, RecipeDetailFragment())
    }

    fun getStatusFromDatabase(
        recipeDataList: List<RecipeData>,
        map: HashMap<String, Boolean>
    ): ArrayList<RecipeData> {
        for (data in recipeDataList) {
            if (map.containsKey(data.id)) {
                val favStatus = map.get(data.id)
                if (favStatus != null) {
                    data.isFav = favStatus
                }
            }
        }
        return recipeDataList as ArrayList<RecipeData>
    }
}