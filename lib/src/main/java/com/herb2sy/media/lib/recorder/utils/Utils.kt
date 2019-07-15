package com.herb2sy.media.lib.recorder.utils

import android.content.Context
import android.os.Environment
import android.view.WindowManager
import java.util.logging.Logger

object Utils {

    fun min2Str(l:Int):String{

        val h = (l % (60*60*24))/(60*60)
        val m = ((l %(60*60*24))%(60*60))/60
        val s = ((l %(60*60*24))%(60*60))%60

        var result = ""

        result += if (h >9){
            h
        }else{
            "0$h"
        }

        result += ":"
        result += if (m>9){
            m
        }else{
            "0$m"
        }
        result += ":"
        result += if (s>9){
            s
        }else{
            "0$s"
        }
        return result
    }


    fun getScreenHeight(context: Context): Int {
        val systemService = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return systemService.defaultDisplay.height
    }

    fun fileRootPath():String{

        val parent = Environment.getExternalStorageDirectory().absolutePath
        if (parent != null){
            return parent
        }
        return "/mnt/sdcard"

    }

}