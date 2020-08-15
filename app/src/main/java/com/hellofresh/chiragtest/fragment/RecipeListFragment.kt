package com.hellofresh.chiragtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hellofresh.chiragtest.R
import com.hellofresh.chiragtest.adapter.RecipeListAdapter
import com.hellofresh.chiragtest.model.RecipeData
import com.hellofresh.chiragtest.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_recipie_list.*
import kotlinx.coroutines.*

class RecipeListFragment:Fragment() {
    private var recipeListAdapter:RecipeListAdapter?=null
    private var arrayList=ArrayList<RecipeData.Items>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeListAdapter= RecipeListAdapter(arrayList)
        val linearLayoutManager=LinearLayoutManager(activity)
        listView.layoutManager=linearLayoutManager
        listView.adapter=recipeListAdapter
        retrieveRepositories()
    }

    private  fun retrieveRepositories() {
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            activity?.let {
                AlertDialog.Builder(it).setTitle("Error")
                    .setMessage(exception.message)
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }
        }
        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val resultList = NetworkManager().getRepositories()
            recipeListAdapter?.dataSetChanged(resultList)
        }
    }

}