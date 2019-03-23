package io.github.pps5.popkotlin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import io.github.pps5.popkotlin.page.LoginPage
import org.junit.Rule
import org.junit.Test

class LoginTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loginAsUser1() {
        val completePage = LoginPage()
            .inputUserId("user1")
            .inputPassword("Password1111")
            .clickLogin()

        val loginPage = completePage
            .assert { onView(withId(R.id.user_info)).check(matches(withText("Your user ID is user1"))) }
            .clickLogout()

        loginPage.assert {
            onView(withId(R.id.user_id)).check(matches(withText("")))
            onView(withId(R.id.password)).check(matches(withText("")))
        }
    }

    @Test
    fun tryLoginWithInvalidId() {
        LoginPage()
            .inputUserId("InvalidUser")
            .inputPassword("Password1111")
            .clickLogin()
            .assert { onView(withText(R.string.invalid_id_or_password)).check(matches(isDisplayed())) }
    }

    @Test
    fun tryLoginWithInvalidPassword() {
        LoginPage()
            .inputUserId("user1")
            .inputPassword("InvalidPassword")
            .clickLogin()
            .assert { onView(withText(R.string.invalid_id_or_password)).check(matches(isDisplayed())) }
    }
}