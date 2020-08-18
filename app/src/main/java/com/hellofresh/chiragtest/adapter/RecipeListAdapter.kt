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

class RecipeListAdapter(var recipeList: List<RecipeData>, context: OnClickRecipeInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var interfaceRecipeClickHandler: OnClickRecipeInterface? = null

    init {
        interfaceRecipeClickHandler = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recepie_layout, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoModel = recipeList.get(position)
        setRecipeItemView(holder, repoModel)
    }

    private fun setRecipeItemView(
        holder: RecyclerView.ViewHolder,
        repoDto: RecipeData?
    ) {
        holder.itemView.setOnClickListener {
            interfaceRecipeClickHandler?.onClick(repoDto)
        }
        repoDto?.apply {
            thumb.let {
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
                if (!calories.isNullOrEmpty())
                    str?.append(" |")

            }
            time.let {
                str?.append(formatTime(it))
            }
            holder.itemView.recipe_highlight.text = str
            isFav.let {
                if (it) {
                    holder.itemView.favImage.setImageResource(R.drawable.ic_fav_check)
                } else {
                    holder.itemView.favImage.setImageResource(R.drawable.ic_like)

                }
            }


        }

    }

    override fun getItemCount() = recipeList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun dataSetChanged(newValues: List<RecipeData>) {
        recipeList = newValues
        notifyDataSetChanged()
    }

    private fun formatTime(time: String?): String {
        return time?.replace("[^0-9]+".toRegex(), " ") + "Min"
    }

    interface OnClickRecipeInterface {
        fun onClick(repoDto: RecipeData?)
    }

}