package com.herb2sy.media.lib.recorder.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import android.widget.RelativeLayout
import com.herb2sy.media.lib.R
import com.herb2sy.media.lib.R.id.btn_video_start
import com.herb2sy.media.lib.recorder.`interface`.OnRecorderChangeListener
import kotlinx.android.synthetic.main.item_recorder.view.*

class RecorderView : RelativeLayout {


    private  var listener1: OnRecorderChangeListener? = null

    private var playButtonState = false //  if true stop else start
    private var nLenStart:Double = 0.0
    private var nLenEnd :Double = 0.0

    private var actionDown = false

    private var doubleFigure = false

    constructor(context: Context) : super(context) {
        initData(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initData(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initData(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initData(context)
    }

    private fun initData(context: Context) {

        val view = LayoutInflater.from(context).inflate(R.layout.item_recorder, this)
        this.isClickable = true
        initListener()

    }


    /**
     * init btn listener
     */
    private fun initListener() {

        // when click back
        btn_record_back.setOnClickListener {
            listener1?.onBack()
        }

        // when click start play
        btn_video_start.setOnClickListener {

            if (playButtonState){
                // stop recorder
                btn_video_start.setImageResource(R.drawable.play)
                listener1?.onStop()


            }else{
                // start recorder
                btn_video_start.setImageResource(R.drawable.stop)
                listener1?.onPlay()

            }

            playButtonState = !playButtonState

        }

        lly_menu.setOnClickListener {
                if(lly_menu.visibility == View.VISIBLE){
                    lly_menu.visibility = View.INVISIBLE
                }else{
                    lly_menu.visibility = View.VISIBLE
                }



        }
        lly_surface.setOnClickListener {

                if(lly_menu.visibility == View.VISIBLE){
                    lly_menu.visibility = View.INVISIBLE
                }else{
                    lly_menu.visibility = View.VISIBLE
                }

        }


    }


    public fun reset(){
        btn_video_start.setImageResource(R.drawable.play)
        playButtonState = false
    }


    /**
     * set duration
     */
    public fun setDuration(str:String?){
        tv_record_duration.text = str?:"data error"
    }

    /**
     * add on Recorder change listener
     */
    public fun addOnRecorderChangeListener(listeners: OnRecorderChangeListener){
        Log.d("bing","运行么")
        listener1 = listeners
    }

    public fun getSufaceView(): SurfaceView {
        return sfv_surfaceview
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        doubleFigure = true
        // Number of fingers touching a device
        val pCount = event?.pointerCount

        if (pCount != 2){
            return super.onTouchEvent(event)
        }
        // Get touch screen action
        val action = event.action

        if (action == MotionEvent.ACTION_MOVE){
            val xLen = Math.abs(event.getX(0) - event.getX(1)).toDouble()
            val yLen  = Math.abs(event.getY(0) - event.getY(1)).toDouble()

            val temp = Math.sqrt(xLen*xLen + yLen*yLen) ?: return super.onTouchEvent(event)

            if (nLenStart == 0.0){
                nLenStart = temp
                return super.onTouchEvent(event)
            }

            if (nLenStart - temp > 0){
                listener1?.onZoomMinus(nLenStart - temp)
            }else if (nLenStart - temp < 0){
                listener1?.onZoomPlus( temp - nLenStart)
            }
            nLenStart = temp

        } else {
            nLenStart = 0.0
            Log.d("bing","归零")

        }

        return true

    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_POINTER_UP){
            nLenStart = 0.0
            Log.d("bing","外边归零")

        }



        val pCount = ev?.pointerCount ?: return super.onInterceptTouchEvent(ev)
        //If more than or equal to two fingers lift the screen, intercept the click event
        if (pCount >= 2){
            return true
        }

        return super.onInterceptTouchEvent(ev)
    }





}
