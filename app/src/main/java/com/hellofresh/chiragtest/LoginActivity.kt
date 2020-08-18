package com.hellofresh.chiragtest

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hellofresh.chiragtest.preference.SharedPrefrenceUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(SharedPrefrenceUtil.isUserLogin()){
            navigateHomeFlow()
        }else {
            initView()
            bindView()
        }
    }

    private fun bindView() {

        login.setOnClickListener {
            valdiateLoginFlow()
        }
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

    }

  private fun valdiateLoginFlow(){
       if (email.text.toString().isEmpty()) {
           Toast.makeText(this,resources.getString(R.string.email_empty_error),
           Toast.LENGTH_SHORT
           ).show()
           return
       }

       val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
           if(!EMAIL_REGEX.toRegex().matches(email.text.toString())){
               Toast.makeText(this,resources.getString(R.string.email_valid_error),
                   Toast.LENGTH_SHORT
               ).show()
               return
           }
      if(password.text.toString().isEmpty()){
          Toast.makeText(this,resources.getString(R.string.pass_empty_error),
              Toast.LENGTH_SHORT
          ).show()
          return
      }
      if(password.text.toString().length<=3){
          Toast.makeText(this,resources.getString(R.string.pass_valid_error),
              Toast.LENGTH_SHORT
          ).show()
          return
      }

      if(SharedPrefrenceUtil.getUserID().equals(email.text.toString()) &&
          SharedPrefrenceUtil.getPassword().equals(password.text.toString())){
             navigateHomeFlow()

          }else{
          Toast.makeText(this,resources.getString(R.string.please_enter_valid_credentia),
              Toast.LENGTH_SHORT
          ).show()
      }
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

}