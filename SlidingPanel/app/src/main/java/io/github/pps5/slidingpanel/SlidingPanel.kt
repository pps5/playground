package io.github.pps5.slidingpanel

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import io.github.pps5.slidingpanel.SlidingPanel.PanelState.*
import kotlin.math.max
import kotlin.math.min

class SlidingPanel @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ViewGroup(context, attributeSet) {

    companion object {
        private const val SENSITIVITY = 1f
    }

    enum class PanelState {
        EXPANDED, COLLAPSED, DRAGGING, HEIGHT_CHANGING
    }

    private var viewDragHelper = ViewDragHelper.create(this, SENSITIVITY, Callback())

    private var panelState = COLLAPSED
    private lateinit var content: View

    private var panelHeight = resources.getDimensionPixelOffset(R.dimen.panel_height)
    private var bottomNavHeight = resources.getDimensionPixelOffset(R.dimen.bottom_navigation_height)
    private var currentBottomNavHeight = bottomNavHeight
    private var maximumViewHeight: Int = 0
    private var slideRange: Int = 0
    private var slideOffset: Float = 0f

    private var onSlideListener: OnSlideListener? = null
    private var onCollapsedHeightChangeListener: OnCollapsedHeightChangeListener? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount != 1) {
            throw IllegalStateException("SlidingPanel must have exactly 1 child")
        }
        content = getChildAt(0)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(widthSize, heightSize)
        content.measure(
            MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY)
        )
        maximumViewHeight = heightSize
        slideRange = heightSize - panelHeight - currentBottomNavHeight
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val contentTop = computePanelTop(slideOffset) + paddingTop
        content.layout(
            paddingLeft,
            contentTop.toInt(),
            paddingLeft + content.measuredWidth,
            (contentTop + content.measuredHeight).toInt()
        )
    }

    private fun computePanelTop(targetSlideOffset: Float): Float {
        val slidePxOffset = targetSlideOffset * slideRange
        return measuredHeight - panelHeight - currentBottomNavHeight - slidePxOffset
    }

    private fun computeSlideOffset(top: Int) = (computePanelTop(0f) - top) / slideRange

    override fun computeScroll() {
        super.computeScroll()
        if (viewDragHelper.continueSettling(true)) {
            postInvalidateOnAnimation()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        viewDragHelper.processTouchEvent(event!!)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return when (ev?.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                viewDragHelper.cancel()
                false
            }
            else -> viewDragHelper.shouldInterceptTouchEvent(ev!!)
        }
    }

    private val expandedTop
        get() = computePanelTop(1f)
    private val collapsedTop
        get() = computePanelTop(0f)

    fun setOnSlideListener(onSlideListener: OnSlideListener) {
        this.onSlideListener = onSlideListener
    }

    fun setOnHeightChangeListener(onCollapsedHeightChangeListener: OnCollapsedHeightChangeListener) {
        this.onCollapsedHeightChangeListener = onCollapsedHeightChangeListener
    }

    fun hideBottomNav() {
        val newTop = computePanelTop(0f) + bottomNavHeight
        if (viewDragHelper.smoothSlideViewTo(content, left, newTop.toInt())) {
            panelState = HEIGHT_CHANGING
            postInvalidateOnAnimation()
        }
    }

    fun showBottomNav() {
        val newTop = computePanelTop(0f) - bottomNavHeight
        if (viewDragHelper.smoothSlideViewTo(content, left, newTop.toInt())) {
            panelState = HEIGHT_CHANGING
            postInvalidateOnAnimation()
        }

    }

    private inner class Callback : ViewDragHelper.Callback() {
        override fun tryCaptureView(p0: View, p1: Int) = p0 === content && panelState != HEIGHT_CHANGING
        override fun getViewVerticalDragRange(child: View) = slideRange

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            if (panelState == HEIGHT_CHANGING) {
                val changedSize = top - (maximumViewHeight - panelHeight - bottomNavHeight)
                val changeOffset = changedSize / bottomNavHeight.toFloat()
                onCollapsedHeightChangeListener?.onChanged(changeOffset)
                currentBottomNavHeight = bottomNavHeight - (bottomNavHeight * changeOffset).toInt()
                if (changeOffset == 1f || changeOffset == 0f) {
                    slideRange = maximumViewHeight - panelHeight - currentBottomNavHeight
                    panelState = COLLAPSED
                }
            } else {
                slideOffset = computeSlideOffset(top)
                onSlideListener?.onSlide(slideOffset)
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val nextTop = when {
                yvel < 0 && slideOffset > 0.3f
                        || yvel < 0 && slideOffset <= 0.4f -> expandedTop

                yvel > 0 && slideOffset <= 0.3f
                        || yvel > 0 && slideOffset >= 0.4f -> collapsedTop

                slideOffset >= 0.5f -> expandedTop
                slideOffset < 0.5f -> collapsedTop
                else -> collapsedTop
            }
            viewDragHelper.settleCapturedViewAt(releasedChild.left, nextTop.toInt())
            invalidate()
        }

        override fun onViewDragStateChanged(state: Int) {
            if (viewDragHelper.viewDragState == ViewDragHelper.STATE_IDLE) {
                when (slideOffset) {
                    0f -> panelState = COLLAPSED
                    1f -> panelState = EXPANDED
                }
            }
            onSlideListener?.onStateChanged(panelState)
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val collapsedTop = computePanelTop(0f)
            val expandedTop = computePanelTop(1f)
            return min(max(top.toFloat(), expandedTop), collapsedTop).toInt()
        }
    }

    interface OnSlideListener {
        fun onSlide(slideOffset: Float)
        fun onStateChanged(newState: PanelState)
    }

    interface OnCollapsedHeightChangeListener {
        fun onChanged(offset: Float)
    }

}