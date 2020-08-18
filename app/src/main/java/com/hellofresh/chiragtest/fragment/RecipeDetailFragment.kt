package com.hellofresh.chiragtest.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.hellofresh.chiragtest.LoginActivity
import com.hellofresh.chiragtest.R
import com.hellofresh.chiragtest.database.RecipeTable
import com.hellofresh.chiragtest.model.RecipeData
import com.hellofresh.chiragtest.preference.SharedPrefrenceUtil
import com.hellofresh.chiragtest.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.item_ingridient.view.*


class RecipeDetailFragment : Fragment() {
    private var recipeData: RecipeData? = null
    private var isFavBoolean = false
    private var recipeViewModel: RecipeViewModel? = null

    companion object {
        val KEY_RECIPE_DATA = "recipe_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        recipeData = arguments?.get(KEY_RECIPE_DATA) as RecipeData
        recipeData?.let { bindView(recipeData) }
        logout.setOnClickListener {
            SharedPrefrenceUtil.logout()
            navigateLogout()
        }

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
                txtDescription.text = description
            }


            ingredientList?.let {

                for (ingredient in ingredientList!!) {
                    val layoutInflater = LayoutInflater.from(context)
                    val view = layoutInflater.inflate(R.layout.item_ingridient, null, false)
                    view.ingredient_name.text = ingredient
                    ingriedient_list_continer.addView(view)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fav, menu)
        var item = menu.findItem(R.id.fav_menu)
        recipeData?.apply {
            if (isFav) {
                isFavBoolean = true
                item.icon = resources.getDrawable(R.drawable.ic_fav_check)
            }
        }

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_menu -> {
                if (isFavBoolean) {
                    item.icon = resources.getDrawable(R.drawable.ic_like)
                    isFavBoolean = false

                } else {
                    item.icon = resources.getDrawable(R.drawable.ic_fav_check)
                    isFavBoolean = true
                }
                recipeData?.id?.let { RecipeTable(it, isFavBoolean) }?.let {
                    recipeViewModel?.insertFavData(
                        it
                    )
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun navigateLogout() {
        SharedPrefrenceUtil.logout()
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }


}