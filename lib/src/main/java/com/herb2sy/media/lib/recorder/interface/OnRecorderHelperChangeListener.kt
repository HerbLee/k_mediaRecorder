package com.herb2sy.media.lib.recorder.`interface`

interface OnRecorderHelperChangeListener {

    fun  startRecorder(file:String)

    fun endRecorder(file:String)

    fun saveVideo(file:String)

    fun onBack()

    fun message(msg:String)

}