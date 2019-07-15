package com.herb2sy.media.lib.recorder.utils

import android.os.Handler
import android.os.Message

class MyTimerDuration(val times:Int, val timer:(t1:String,t2:String)->Unit) {

    var currentTime = 0

    private val mHandler = object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg?.what){
                4 ->{


                    timer(Utils.min2Str(currentTime),Utils.min2Str(times-currentTime))
                    if (currentTime == times){


                        currentTime = 0
                    }else{

                        currentTime += 1
                        sendEmptyMessageDelayed(4,1000)
                    }
                }
                else ->{}
            }


        }
    }



    fun start(){
        mHandler.sendEmptyMessageDelayed(4,1000)
    }

    fun refresh(){
        currentTime = 0
        mHandler.removeCallbacksAndMessages(null)
        timer(Utils.min2Str(currentTime),Utils.min2Str(times-currentTime))
    }

    fun destroy(){
        mHandler.removeCallbacksAndMessages(null)
    }

}