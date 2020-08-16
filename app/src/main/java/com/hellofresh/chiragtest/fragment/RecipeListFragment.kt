package com.hellofresh.chiragtest.fragment

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
import com.hellofresh.chiragtest.adapter.RecipeListAdapter
import com.hellofresh.chiragtest.model.RecipeData
import com.hellofresh.chiragtest.network.CallResponseStatus
import com.hellofresh.chiragtest.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_recipie_list.*

class RecipeListFragment:Fragment() {
    private var recipeListAdapter:RecipeListAdapter?=null
    private var arrayList=ArrayList<RecipeData.Items>()
    private var recipeViewModel:RecipeViewModel?=null

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

    private fun bindView() {
        setRecipeObserver()
    }

    private fun setRecipeObserver() {
        recipeViewModel?.recipeMutableLiveData?.observe(viewLifecycleOwner, Observer<CallResponseStatus<List<RecipeData.Items>>> {
            it?.let { output->
                when(output.status){
                    CallResponseStatus.Status.SUCCESS->{
                        progressbar.visibility=View.GONE
                        it.data?.let {
                            setAdapter(it)
                        }

                    }
                    CallResponseStatus.Status.ERROR->{
                        progressbar.visibility=View.GONE
                        setErrorMessage(it)

                    }
                }
            }
        })
    }

    private fun setErrorMessage(it: CallResponseStatus<List<RecipeData.Items>>?) {
        activity?.let { it1 ->
            AlertDialog.Builder(it1).setTitle(R.string.error)
                .setMessage(it?.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> activity?.finish()}
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun setAdapter(it: List<RecipeData.Items>) {
        recipeListAdapter?.dataSetChanged(it)

    }

    private fun initView() {
        recipeViewModel=ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        recipeListAdapter= RecipeListAdapter(arrayList)
        val linearLayoutManager=LinearLayoutManager(activity)
        listView.layoutManager=linearLayoutManager
        listView.adapter=recipeListAdapter
    }

    private  fun retrieveRepositories() {
        progressbar.visibility=View.VISIBLE
        recipeViewModel?.getRecipeList()
    }

    override fun onDestroy() {
        recipeViewModel?.onCleared()
            super.onDestroy()

    }

}