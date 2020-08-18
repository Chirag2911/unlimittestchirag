package com.hellofresh.chiragtest.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hellofresh.chiragtest.R
import com.hellofresh.chiragtest.network.LoginValidatorStatus
import com.hellofresh.chiragtest.preference.SharedPrefrenceUtil

open class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var context: Context? = null
    private val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    val validateLoginLiveData = MutableLiveData<LoginValidatorStatus>()

    init {
        context = application.applicationContext
    }

    fun validateLoginFlow(email: String, password: String) {
        if (email.isEmpty()) {
            validateLoginLiveData.postValue(LoginValidatorStatus.error(context?.getString(R.string.email_empty_error)))
            return
        }

        if (!EMAIL_REGEX.toRegex().matches(email)) {
            validateLoginLiveData.postValue(LoginValidatorStatus.error(context?.getString(R.string.email_valid_error)))
            return
        }
        if (password.isEmpty()) {
            validateLoginLiveData.postValue(LoginValidatorStatus.error(context?.getString(R.string.pass_empty_error)))
            return
        }
        if (password.length <= 3) {
            validateLoginLiveData.postValue(LoginValidatorStatus.error(context?.getString(R.string.pass_valid_error)))
            return
        }
        if (!(SharedPrefrenceUtil.getUserID().equals(email) &&
                    SharedPrefrenceUtil.getPassword().equals(password))
        ) {
            validateLoginLiveData.postValue(LoginValidatorStatus.error(context?.getString(R.string.please_enter_valid_credentia)))
            return
        }
        validateLoginLiveData.value = LoginValidatorStatus.success()

    }
}