package io.github.pps5.kakaosampleapp.scenario

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import io.github.pps5.kakaosampleapp.common.MainActivity
import io.github.pps5.kakaosampleapp.di.IS_SUCCESS_NEW_ARRIVALS
import io.github.pps5.kakaosampleapp.di.dataStoreModule
import io.github.pps5.kakaosampleapp.di.dispatchersModule
import io.github.pps5.kakaosampleapp.screen.DetailScreen
import io.github.pps5.kakaosampleapp.screen.NewArrivalsScreen
import io.github.pps5.kakaosampleapp.screen.NewArrivalsScreen.NewArrivalItem
import io.github.pps5.kakaosampleapp.screen.utils.assert
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
            factory(named(IS_SUCCESS_NEW_ARRIVALS)) { true }
        })

        NewArrivalsScreen()
            .assert { errorMessage { isNotDisplayed() } }
            .assert {
                newArrivalsList {
                    firstChild<NewArrivalItem> { title.hasText("No.0 Title") }
                    lastChild<NewArrivalItem> { title.hasText("No.19 Title") }
                }
            }
            .clickFirstItem()

        DetailScreen()
            .assert { openEventPageButton { isCompletelyDisplayed() } }
            .assert { eventTitle { containsText("No.0 Title") } }
            .assert { summary { containsText("概要") } }
            .navigateUpTo<NewArrivalsScreen>()

        NewArrivalsScreen()
            .assert { errorMessage { isNotDisplayed() } }
    }

    @Test
    fun showErrorOnFetchFailure() {
        loadKoinModules(module(override = true) {
            factory(named(IS_SUCCESS_NEW_ARRIVALS)) { false }
        })
        onScreen<NewArrivalsScreen> { errorMessage.isDisplayed() }
    }
}
