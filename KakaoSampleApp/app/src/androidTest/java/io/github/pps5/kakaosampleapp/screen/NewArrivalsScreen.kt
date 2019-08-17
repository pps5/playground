package io.github.pps5.kakaosampleapp.screen

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KTextView
import io.github.pps5.kakaosampleapp.R
import org.hamcrest.Matcher

class NewArrivalsScreen : BaseScreen<NewArrivalsScreen>() {

    val errorMessage = KTextView { withText(R.string.loading_error) }
    val newArrivalsList = KRecyclerView(
        { withId(R.id.new_arrivals_recycler) },
        itemTypeBuilder = { itemType(::NewArrivalItem) }
    )

    class NewArrivalItem(parent: Matcher<View>) : KRecyclerItem<NewArrivalItem>(parent) {
        val title = KTextView(parent) { withId(R.id.title) }
    }

    fun clickFirstItem(): DetailScreen {
        newArrivalsList.firstChild<NewArrivalItem> { click() }
        return DetailScreen()
    }

}
