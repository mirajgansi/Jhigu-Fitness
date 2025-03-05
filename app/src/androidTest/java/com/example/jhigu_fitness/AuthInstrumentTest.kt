package com.example.jhigu_fitness

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.jhigu_fitness.ui.activity.ui.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AuthInstrumentedTest {

    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLogin() {
        // Enter email
        onView(withId(R.id.etEmail)).perform(
            typeText("ram@gmail.com")
        )

        // Enter password
        onView(withId(R.id.etPassword)).perform(
            typeText("password")
        )

        // Close the soft keyboard
        closeSoftKeyboard()

        // Click the login button
        onView(withId(R.id.btnLogin)).perform(
            click()
        )

        // Check if the login failed message is displayed
        onView(withId(R.id.tvSignInTitle)).check(matches(withText("Jhigu Fitness")))
    }
}