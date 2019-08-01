package io.github.pps5.kakaosampleapp.scenario

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import io.github.pps5.kakaosampleapp.common.MainActivity
import io.github.pps5.kakaosampleapp.di.MOCK_NEW_ARRIVALS
import io.github.pps5.kakaosampleapp.di.dataStoreModule
import io.github.pps5.kakaosampleapp.di.dispatchersModule
import io.github.pps5.kakaosampleapp.screen.DetailScreen
import io.github.pps5.kakaosampleapp.screen.NewArrivalsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class NewArrivalsScenario : KoinTest {

    @get:Rule
    val activityScenario = activityScenarioRule<MainActivity>()

    @Before
    fun setUp() = loadKoinModules(listOf(dataStoreModule, dispatchersModule))

    @Test
    fun showDetailFromNewArrivals() {
        loadKoinModules(module(override = true) {
            factory(named(MOCK_NEW_ARRIVALS)) { true }
        })

        onScreen<NewArrivalsScreen> {
            errorMessage { isNotDisplayed() }
            assertEventTitle(first = "No.0 Title", last = "No.19 Title")
            clickFirstItem()
        }

        onScreen<DetailScreen> {
            openEventPageButton.isCompletelyDisplayed()
            assertWebViewContains("No.0 Title")
            navigateUpTo<NewArrivalsScreen>()
        }
    }

    @Test
    fun showErrorOnFetchFailure() {
        loadKoinModules(module(override = true) {
            factory(named(MOCK_NEW_ARRIVALS)) { false }
        })
        onScreen<NewArrivalsScreen> { errorMessage.isDisplayed() }
    }
}
