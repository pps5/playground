package io.github.pps5.slidingpanel

import android.content.Context
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet

class BottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BottomNavigationView(context, attrs) {

    private val bottomNavHeight = resources.getDimensionPixelOffset(R.dimen.bottom_navigation_height)
    private var hidden: Boolean = false
    private var defaultTop: Int = 0

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (defaultTop == 0) {
            defaultTop = top
        }
    }

    private val onSlideListener = object : SlidingPanel.OnSlideListener {
        override fun onSlide(slideOffset: Float) {
            if (!hidden) {
                y = defaultTop + (measuredHeight * slideOffset)
            }
        }

        override fun onStateChanged(newState: SlidingPanel.PanelState) {}
    }

    private val onCollapsedHeightChangeListener = object : SlidingPanel.OnCollapsedHeightChangeListener {
        override fun onChanged(offset: Float) {
            y = defaultTop + (measuredHeight * offset)
        }

    }

    fun setOnSlideListenerFor(slidingPanel: SlidingPanel) {
        slidingPanel.setOnSlideListener(onSlideListener)
        slidingPanel.setOnHeightChangeListener(onCollapsedHeightChangeListener)

        this.setOnNavigationItemSelectedListener {
            hidden = true
            slidingPanel.hideBottomNav()
            Handler().postDelayed({
                slidingPanel.showBottomNav()
                hidden = false
            }, 2000)
            true
        }
    }


}
