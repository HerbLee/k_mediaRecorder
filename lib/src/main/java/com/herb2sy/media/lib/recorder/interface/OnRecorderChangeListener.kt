package com.herb2sy.media.lib.recorder.`interface`

interface OnRecorderChangeListener {

    /**
     * 点击播放时启用
     */
    fun onPlay()

    /**
     * 点击停止时启用
     */
    fun onStop()

    /**
     * 点击返回时启用
     */
    fun onBack()

    /**
     * 放大
     */
    fun onZoomPlus(double: Double)

    /**
     * 缩小
     */
    fun onZoomMinus(double: Double)
}