package com.hellofresh.chiragtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.hellofresh.chiragtest.R
import com.hellofresh.chiragtest.model.RecipeData
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.item_ingridient.view.*


class RecipeDetailFragment : Fragment() {

    private var recipeData: RecipeData? = null

    companion object {
        val KEY_RECIPE_DATA = "recipe_data"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeData = arguments?.get(KEY_RECIPE_DATA) as RecipeData
        recipeData?.let { bindView(recipeData) }

    }

    private fun bindView(recipeData: RecipeData?) {
        recipeData?.apply {
            image?.let {
                Glide.with(recipeImage)
                        .load(it)
                        .centerCrop()
                        .into(recipeImage)
            }

            name?.let { txtHeading.text = it }
            headline?.let { txtSubHeading.text = it }

            time?.let {
                prep_time.visibility = View.VISIBLE
                txtMin.text = getString(R.string.min, it.replace("[^0-9]+".toRegex(), " "))
            }

            calories?.let {
                if (it.isNotEmpty()) {
                    calories_container.visibility = View.VISIBLE
                    calories_value.text = calories
                }
            }
            difficulty?.let {
                if (it.isNotEmpty()) {
                    difficulty_container.visibility = View.VISIBLE
                    diff_value.text = getString(R.string.level, difficulty)
                }
            }

            description.let {
                txtDescription.text=description
            }


           ingredientList?.let {

               for (ingredient in ingredientList!!) {
                   val layoutInflater=LayoutInflater.from(context)
                   val view =layoutInflater.inflate(R.layout.item_ingridient, null, false)
                   view.ingredient_name.text = ingredient
                   ingriedient_list_continer.addView(view)
               }
           }
        }
    }






}