/**
 * Company herb2sy
 * Copyright (C) 2017-2019 All Rights Reserved.
 */
package com.herb2sy.media.lib.recorder.utils

import android.util.Log

/**
 * @author HerbLee
 * @date Created on 2019/7/15 23:09
 * @function:
 */
class ZoomPager(val maxSize:Int) {

    private var sizes = 0

    fun plus(float: Double):Int{
        if (float <=0){
            return sizes
        }
        val fl = float / 10
        val toInt = fl.toInt()
        return if (sizes + toInt > maxSize){
            sizes = maxSize
            maxSize
        }  else {
            sizes += toInt
            sizes
        }
    }

    fun minus(float: Double):Int{
        if (float <=0){
            return sizes
        }

        val fl = float / 10
        val toInt = fl.toInt()
        return if (sizes -toInt <= 0){
            sizes = 0
            sizes
        }else{
            sizes -= toInt
            sizes
        }
    }




}