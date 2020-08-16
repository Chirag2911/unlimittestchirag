package com.hellofresh.chiragtest

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.hellofresh.chiragtest.`interface`.LaunchFragmentInterface
import com.hellofresh.chiragtest.fragment.RecipeDetailFragment
import com.hellofresh.chiragtest.fragment.RecipeListFragment


class MainActivity : AppCompatActivity(), LaunchFragmentInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        launchFragment(fragment=RecipeListFragment())
    }

    private fun initView() {

        supportActionBar?.title=getString(R.string.recipes)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }
     override fun launchFragment(bundle: Bundle?,fragment: Fragment?) {
         val fragment1=   supportFragmentManager.findFragmentByTag(fragment?.javaClass?.simpleName)
        if(fragment1==null) {
            fragment?.arguments=bundle
            fragment?.let { it1 ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, it1, it1.javaClass.simpleName)
                        .addToBackStack(it1.javaClass.simpleName)
                        .commit()
                }
            }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount==1){
            finish()
        }else {
            super.onBackPressed()
        }
    }



}
