package io.github.pps5.popkotlin.page

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.matcher.ViewMatchers.withId
import io.github.pps5.popkotlin.R
import io.github.pps5.popkotlin.page.Page.Label.Companion.withLabel
import io.github.pps5.popkotlin.page.Page.Label.Companion.withoutLabel


class LoginPage : Page() {

    fun inputUserId(userId: String) = action(withoutLabel()) {
        onView(withId(R.id.user_id)).perform(replaceText(userId))
    }

    fun inputPassword(password: String) = action(withoutLabel()) {
        onView(withId(R.id.password)).perform(replaceText(password))
    }

    fun clickLogin() = actionTo(LoginCompletePage(), withLabel("click_login")) {
        onView(withId(R.id.login)).perform(click())
    }
}