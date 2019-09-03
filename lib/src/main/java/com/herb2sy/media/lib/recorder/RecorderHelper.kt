package com.herb2sy.media.lib.recorder

import android.content.Context
import android.content.Intent
import android.hardware.Camera
import android.media.AudioManager
import android.media.MediaRecorder
import android.net.Uri
import android.util.Log
import android.view.SurfaceHolder
import com.herb2sy.media.lib.R
import com.herb2sy.media.lib.R.id.*
import com.herb2sy.media.lib.recorder.`interface`.OnRecorderChangeListener
import com.herb2sy.media.lib.recorder.`interface`.OnRecorderHelperChangeListener
import com.herb2sy.media.lib.recorder.utils.MyTimerDuration
import com.herb2sy.media.lib.recorder.utils.RecorderTimer
import com.herb2sy.media.lib.recorder.utils.Utils
import com.herb2sy.media.lib.recorder.utils.ZoomPager
import com.herb2sy.media.lib.recorder.view.RecorderView
import java.io.File
import java.lang.Exception

/**
 * 主要使用类
 */
class RecorderHelper(val context:Context,val recorderView:RecorderView, val listener:OnRecorderHelperChangeListener?): SurfaceHolder.Callback {



    private var maxDuration:Int? = 10 // recorder max time

    private var maxQualityInt:Int = 720 // recorder max time

    private var width:Int = 0
    private var height:Int = 0

    private var sufaceHolder: SurfaceHolder? = null


    private var mCamera:Camera? = null


    private var maxZoom:Int = 0

    private var mediaRecorder:MediaRecorder? =null

    private var fileName:String?= ""

    private var filePath:String? = Utils.fileRootPath()+"/herb2sy/temp"


    private var mTimer:RecorderTimer? = null
    private var myTimerDuration: MyTimerDuration? = null

    private var recordState = false

    private var recordVoice = true

    private var zoomPage:ZoomPager? = null
    private var videoEncodingBitRate:Int = 5

    private var notifiySys:Boolean = false
    
    private var defMode = 0


    private fun listener() {
        recorderView.addOnRecorderChangeListener(object :OnRecorderChangeListener{
            override fun onPlay() {
                recordState = true
                startRecord()
                 if(!recordVoice){
                     val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    defMode = am.mode
                    am.mode = AudioManager.STREAM_MUSIC
                    am.isMicrophoneMute = true
                }
            }

            override fun onStop() {
                recordState = false
                endRecord()
            }

            override fun onBack() {
                listener?.onBack()
            }

            override fun onZoomPlus(double: Double) {

                val parameters = mCamera?.parameters

                parameters?.zoom = zoomPage?.plus(double)!!
                mCamera?.parameters = parameters

            }

            override fun onZoomMinus(double: Double) {
                val parameters = mCamera?.parameters

                parameters?.zoom = zoomPage?.minus(double)!!
                mCamera?.parameters = parameters
            }

        })



        mTimer = RecorderTimer(maxDuration!!*60) {
            tmepEndRecord()
        }

        myTimerDuration = MyTimerDuration(maxDuration!!*60){ t1, t2 ->

            recorderView.setDuration("$t1 / $t2")

        }

    }


    private fun endRecord() {
         if(!recordVoice){
             val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            am.mode =defMode
            am.isMicrophoneMute = false
        }
        try {
            mCamera?.lock()
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
            if (fileName != null &&fileName != ""){
              listener?.endRecorder("$filePath/$fileName")
            }

            startPreView(recorderView.getSufaceView().holder)

            listener?.saveVideo("$filePath/$fileName")

            refreshSysDb()


            mTimer?.refresh()
            myTimerDuration?.refresh()
        }catch (e:Exception){
            e.printStackTrace()
            recorderView.reset()
            listener?.message("停止录制错误")
        }

    }

    private fun refreshSysDb() {
        if (notifiySys){
            val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            scanIntent.data = Uri.fromFile(File("$filePath/$fileName"))
            context.sendBroadcast(scanIntent)
        }

    }


    private fun tmepEndRecord(){

        
        mCamera?.lock()
        mediaRecorder?.stop()
        mediaRecorder?.release()
        if (fileName != null &&fileName != ""){
            listener?.saveVideo("$filePath/$fileName")
        }
        startPreView(recorderView.getSufaceView().holder)
        mTimer?.refresh()
        myTimerDuration?.refresh()
        startRecord()

    }


    /**
     * start recorder
     */
    private fun startRecord() {
        
        try {
            initMediarecorder()
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            mTimer?.start()
            myTimerDuration?.start()
            if (fileName != ""){
                listener?.startRecorder("$filePath/$fileName")
            }

        }catch (e:Exception){
            e.printStackTrace()
            recorderView.reset()
            listener?.message("开始录制错误")
        }

    }

    private fun initMediarecorder() {

        val file = File(filePath)

        if (!file.exists() && !file.isDirectory){
            file.mkdirs()
        }
        
        mediaRecorder = MediaRecorder()

        mCamera?.unlock()
        mediaRecorder?.setCamera(mCamera)

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)


        mediaRecorder?.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        mediaRecorder?.setVideoFrameRate(25)
        mediaRecorder?.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        mediaRecorder?.setVideoEncodingBitRate(videoEncodingBitRate* width*height)

        mediaRecorder?.setVideoSize(width,height)
        mediaRecorder?.setPreviewDisplay(recorderView.getSufaceView().holder.surface)

        fileName = System.currentTimeMillis().toString()+".mp4"
        mediaRecorder?.setOutputFile("$filePath/$fileName")


    }



    /**
     * set max recorder duration
     */
    fun setMaxDuration(duration:Int){
        maxDuration = duration
        // set duration
        recorderView.setDuration("${Utils.min2Str(0)} / ${Utils.min2Str(duration*60)}")
    }

    fun setMaxQualityInt(quality:Int) {
        maxQualityInt = quality
    }

    fun setVideoEncodingBitRate(size:Int){
        videoEncodingBitRate = size
    }
    

    /**
     * reset all
     */
    fun reset(){
        recorderView.reset()
    }

    fun setFilePath(path:String){
        filePath = path
    }


    fun onDestory(){
        mTimer?.destroy()
        myTimerDuration?.destroy()
         if(!recordVoice){
             val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            am.mode =defMode
            am.isMicrophoneMute = false
        }

//        if (!recordVoice){
//            val systemService = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//            systemService.isMicrophoneMute = true
//        }

        if (mCamera != null){
            mCamera?.release()
            mCamera = null
        }
        if (sufaceHolder != null){
            sufaceHolder = null
        }

        if (mediaRecorder != null){
            mediaRecorder = null
        }
    }

     fun onStop() {
        // when switch app stop record
        if (recordState){
            endRecord()
            recorderView.reset()
        }

    }


    fun start(){
        initSurface()
        listener()
       
    }



    fun setRecordVoice(flat:Boolean){
        recordVoice = flat


    }

    public fun setNotifySys(flag:Boolean){
        notifiySys = flag
    }



    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        sufaceHolder = holder
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        sufaceHolder = null
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        sufaceHolder = holder
        startPreView(recorderView.getSufaceView().holder)
    }


    private fun startPreView(holder: SurfaceHolder) {
        try {
            if (mCamera == null){
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK)
            }

            mCamera?.setPreviewDisplay(holder)
            val parameters = mCamera?.parameters
            val focusModes = parameters?.supportedFocusModes
            focusModes?.forEach {
                it.contains("continuous-video")
                parameters?.focusMode = "continuous-video"
            }
            parameters?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
            parameters?.previewFrameRate = 25
            mCamera?.parameters = parameters
            mCamera?.startPreview()

        }catch (e:Exception){
            e.printStackTrace()
            recorderView.reset()
            listener?.message("设置界面错误")
        }

    }
    private fun initSurface() {
        val holder = recorderView.getSufaceView().holder
        try {
            val size = Camera.open().parameters.supportedPreviewSizes
            //get max zoom

            maxZoom = Camera.open().parameters.maxZoom

            zoomPage = ZoomPager(maxZoom)

            width = size[0].width
            height = size[0].height

            //说明支持
            if (height >= maxQualityInt){

                if (maxQualityInt == 720){
                    width = 1280
                    height = 720
                }else{
                    width = 1920
                    height = 1080
                }

            }
            holder.setFixedSize(width,height)

            val screenHeight = Utils.getScreenHeight(context)
            val newWidth = (screenHeight * width / height).toInt()
            val lp = recorderView.getSufaceView().layoutParams
            lp.width = newWidth
            lp.height = screenHeight

            recorderView.getSufaceView().layoutParams = lp
            recorderView.getSufaceView().setZOrderOnTop(true)
            recorderView.getSufaceView().setZOrderMediaOverlay(true)


        }catch (e:Exception){}

        holder.addCallback(this)
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

    }



}
