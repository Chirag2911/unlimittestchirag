package com.hellofresh.chiragtest.preference

import android.content.Context
import android.content.SharedPreferences
import com.hellofresh.chiragtest.App

object SharedPrefrenceUtil {
    private var sharedPreferences: SharedPreferences
    private const  val IS_USER_LOGIN="is_user_login"
    private const  val EMAIL_KEY="email_key"
    private const  val  PASS_KEY="pass_key"

    init {
        val sharedPrefFile = "sharedpreference"
        sharedPreferences= App.context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        setUserIdNPassword()
    }

    fun login(){
        sharedPreferences.edit()?.putBoolean(IS_USER_LOGIN,true)?.apply()
    }
    fun logout(){
        sharedPreferences.edit()?.putBoolean(IS_USER_LOGIN,false)?.apply()
    }


   private fun setUserIdNPassword(){
        sharedPreferences.edit()?.putString(EMAIL_KEY,"chirag@hellofresh.com")?.apply()
        sharedPreferences.edit()?.putString(PASS_KEY,"newemployee")?.apply()
    }

    fun getUserID():String{
        return sharedPreferences?.getString(EMAIL_KEY,"")
    }

    fun getPassword():String{
        return sharedPreferences?.getString(PASS_KEY,"")

    }

    fun isUserLogin(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_LOGIN,false)
    }




}