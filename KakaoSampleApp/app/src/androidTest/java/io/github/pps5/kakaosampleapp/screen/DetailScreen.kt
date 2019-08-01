package io.github.pps5.kakaosampleapp.screen

import androidx.test.espresso.web.webdriver.Locator
import com.agoda.kakao.text.KButton
import com.agoda.kakao.web.KWebView
import io.github.pps5.kakaosampleapp.R

class DetailScreen : BaseScreen<DetailScreen>() {

    val openEventPageButton = KButton { withText(R.string.open_event_page) }
    val detailWebView = KWebView { withId(R.id.detail_webview) }

    init {
        detailWebView { view.forceJavascriptEnabled() }
    }

    fun assertWebViewContains(text: String) = apply {
        detailWebView { withElement(Locator.TAG_NAME, "h1") { hasText(text) } }
    }

}
