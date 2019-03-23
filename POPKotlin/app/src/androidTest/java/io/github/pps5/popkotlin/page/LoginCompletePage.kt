package io.github.pps5.popkotlin.page

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import io.github.pps5.popkotlin.R
import io.github.pps5.popkotlin.page.Page.Label.Companion.withLabel

class LoginCompletePage : Page() {

    fun clickLogout() = actionTo(LoginPage(), withLabel("click_logout")) {
        onView(withId(R.id.logout)).perform(click())
    }
}