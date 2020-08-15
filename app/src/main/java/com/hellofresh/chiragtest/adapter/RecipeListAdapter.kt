package com.hellofresh.chiragtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hellofresh.chiragtest.R
import com.hellofresh.chiragtest.model.RecipeData
import kotlinx.android.synthetic.main.item_recepie_layout.view.*
import java.lang.StringBuilder

class RecipeListAdapter(var recipeList: List<RecipeData.Items>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recepie_layout, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoModel = recipeList.get(position)
        setDataOnList(holder, repoModel)
    }

    private fun setDataOnList(
        holder: RecyclerView.ViewHolder,
        repoDto: RecipeData.Items?
    ) {
        repoDto?.apply {
            image.let {
                Glide.with(holder.itemView)
                    .load(it)
                    .centerCrop()
                    .into(holder.itemView.recipeImage)
            }

            name.let {
                holder.itemView.txtHeading.text = it
            }

            headline.let {
                holder.itemView.txtSubHeading.text = it
            }
            val str: StringBuilder? = StringBuilder()

            calories?.let {
                str?.append(it)
            }
            time.let {
                str?.append(" |")
                str?.append(changeTimeFormatIntoTime(it))
            }
            holder.itemView.recipe_highlight.text = str

        }

    }

    override fun getItemCount() = recipeList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun dataSetChanged(newValues: List<RecipeData.Items>) {
        recipeList = newValues
        notifyDataSetChanged()
    }

    fun changeTimeFormatIntoTime(time: String?): String {
        return time?.replace("[^0-9]+".toRegex(), " ")+"Min"
    }

}