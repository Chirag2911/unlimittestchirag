package com.hellofresh.chiragtest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityEspressoTest {
    @get:Rule
    var mActivityRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun ensureLoginWork() {
        onView(withId(R.id.email))
            .perform(typeText("HELLO"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("CHIRAG"), closeSoftKeyboard())
        onView(withId(R.id.login)).perform(click())
        onView(withId(R.id.email)).check(matches(withText("HELLO")))
        onView(withId(R.id.password)).check(matches(withText("CHIRAG")))
    }

    @Test
    fun ensureLoginIDIncorrect() {
        onView(withId(R.id.email))
            .perform(typeText("HELLO"), closeSoftKeyboard())
        onView(withId(R.id.email)).check(matches(not(withText("sjdnjksndk"))))
    }

    @Test
    fun ensureLoginPasswordIncorrect() {
        onView(withId(R.id.password)).perform(typeText("CHIRAG"), closeSoftKeyboard())
        onView(withId(R.id.password)).check(matches(not(withText("sjdnjksndk"))))
    }

    @Test
    fun verifyLoginButtonExist() {
        onView(withText(startsWith("Login"))).perform(click())

    }

    @Test
    fun ensureLoginButtonIsEnabled() {
        onView(withId(R.id.login))
            .perform(click())
            .check(matches(isEnabled()))
    }

}