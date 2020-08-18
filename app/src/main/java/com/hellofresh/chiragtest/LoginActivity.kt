package com.hellofresh.chiragtest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hellofresh.chiragtest.network.LoginValidatorStatus
import com.hellofresh.chiragtest.preference.SharedPrefrenceUtil
import com.hellofresh.chiragtest.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.toolbar


class LoginActivity : AppCompatActivity() {
    private var loginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (SharedPrefrenceUtil.isUserLogin()) {
            navigateHomeFlow()
        } else {
            initView()
            bindView()
        }
    }

    private fun bindView() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        login.setOnClickListener {
            hideKeyboard(it)
            loginViewModel?.validateLoginFlow(email.text.toString(), password.text.toString())
        }

        loginViewModel?.validateLoginLiveData?.observe(this, Observer<LoginValidatorStatus> {
            it?.let { output ->
                when (output.status) {
                    LoginValidatorStatus.Status.SUCCESS -> {
                        navigateHomeFlow()
                    }
                    LoginValidatorStatus.Status.ERROR -> {
                        Toast.makeText(
                            this, output.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    private fun navigateHomeFlow() {
        SharedPrefrenceUtil.login()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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

    private fun hideKeyboard(v:View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0)
    }

}