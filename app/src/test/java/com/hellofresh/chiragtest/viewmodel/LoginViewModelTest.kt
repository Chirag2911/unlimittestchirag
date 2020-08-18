package com.hellofresh.chiragtest.viewmodel

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.hellofresh.chiragtest.R
import com.hellofresh.chiragtest.network.LoginValidatorStatus
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

import junit.framework.TestCase.assertEquals as assertEquals



class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule var mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var loginViewModel:LoginViewModel
    @Mock
    var application: Application= Application()
    @Mock
    var context: Context=mock(Application::class.java)


    @Before
     fun setUp() {
        MockitoAnnotations.initMocks(this)
        loginViewModel= LoginViewModel(application)
    }

    @Test
    fun validateLiveData(){
        val validateLoginLiveData = MutableLiveData<String>()
        validateLoginLiveData.postValue("test")
        assertEquals("test", validateLoginLiveData.value)
    }

    @Test
    fun valdiateEmailEmpty(){
        val validateLoginLiveData=MutableLiveData<LoginValidatorStatus>()
        `when`(context.getString(Mockito.anyInt())).thenReturn("empty")
       val loginValidatorStatus= LoginValidatorStatus.error(context.getString(R.string.email_empty_error))
        validateLoginLiveData.postValue(loginValidatorStatus)
        loginViewModel.validateLoginFlow("","")

        assertEquals(loginValidatorStatus.status, validateLoginLiveData.value?.status)
        assertEquals(loginValidatorStatus.message, validateLoginLiveData.value?.message)
    }
    @Test
    fun incorrectEmailTest(){
        val validateLoginLiveData=MutableLiveData<LoginValidatorStatus>()
        `when`(context.getString(Mockito.anyInt())).thenReturn("invalid")
        val loginValidatorStatus= LoginValidatorStatus.error(context.getString(R.string.email_valid_error))
        validateLoginLiveData.postValue(loginValidatorStatus)
        loginViewModel.validateLoginFlow("gahsdfghdghd","")
        assertEquals(loginValidatorStatus.status, validateLoginLiveData.value?.status)
        assertEquals(loginValidatorStatus.message, validateLoginLiveData.value?.message)
    }

}