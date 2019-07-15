package com.herb2sy.media.lib.recorder.utils

import android.os.Handler
import android.os.Message

class RecorderTimer(val times:Int,val timer:()->Unit) {


    var currentTime = 0

    private val mHandler = object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                5 ->{

                    if (currentTime == times){
                        timer()
                        currentTime = 0
                    }else{
                        currentTime += 1
                        sendEmptyMessageDelayed(5,1000)
                    }
                }
                else ->{}
            }


        }
    }



    fun start(){
        mHandler.sendEmptyMessageDelayed(5,1000)
    }

    fun refresh(){
        currentTime = 0
        mHandler.removeCallbacksAndMessages(null)
    }

    fun destroy(){
        mHandler.removeCallbacksAndMessages(null)
    }

}