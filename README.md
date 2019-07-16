# k_mediaRecorder
MediaRecorder 录制视频,支持缩放,分段录制
> 此版本为初始版本,后续持续更新

[![](https://www.jitpack.io/v/HerbLee/k_mediaRecorder.svg)](https://www.jitpack.io/#HerbLee/k_mediaRecorder)
[![official project](https://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![License](https://img.shields.io/badge/license-MIT-lightgrey.svg?maxAge=2592000)](https://opensource.org/licenses/MIT)
## 效果

![text](https://github.com/HerbLee/k_mediaRecorder/blob/master/c5fa474a314ffe2bf5fd219be8b255b.jpg)
![text](https://github.com/HerbLee/k_mediaRecorder/blob/master/a1e0a7b685374dd0aac0ddbce57c453.jpg)



## 快速引入
> gradle

  
  
	项目 build
    allprojects {
      repositories {
        ...
        maven { url 'https://www.jitpack.io' }
      }
    }
    
    app build
   
    dependencies {
	        implementation 'com.github.HerbLee:k_mediaRecorder:1.0'
	}

> maven
	
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://www.jitpack.io</url>
		</repository>
	</repositories>
	
	<dependency>
	    <groupId>com.github.HerbLee</groupId>
	    <artifactId>k_mediaRecorder</artifactId>
	    <version>1.0</version>
	</dependency>
	
	
## 快捷使用

> layout
	
	 <com.herb2sy.media.lib.recorder.view.RecorderView
	      android:id="@+id/rv_test"
	      android:layout_width="match_parent"
	      android:layout_height="match_parent"/>
	      
> activity

	
	// 1 初始化参数 RecorderHelper(context, RecorderView对象, 接口回调)
	
	 recorderHelper = RecorderHelper(this, rv_test, object :OnRecorderHelperChangeListener{
            override fun startRecorder(file: String) {
	    	//开始录制 files 为返回的录制视频的地址+名称
	    
            }

            override fun endRecorder(file: String) {
		//停止录制  点击了停止按钮后才调用, 如果为分段视频,中间自行分段不调用files 为返回的录制视频的地址+名称
            }

            override fun saveVideo(file: String) {
		//保存录制的文件 每次录制完成都调用 files 为返回的录制视频的地址+名称
            }

            override fun onBack() {
		//点击界面返回按钮调用
            }

            override fun message(msg: String) {
		//录制过程出现异常消息,调用
            }

        })
	
	// 2 初始化各种参数(都为可选)
	
		recorderHelper.setFilePath(path:String) //设置视频保存的地址 默认为 根目录/herb2sy/temp
		recorderHelper.setMaxDuration(duration:Int) //设置最大录制时长单位为min 默认为 10min 
		recorderHelper.setMaxQualityInt(quality:Int) //设置录制视频大小 默认为 720p, 目前仅支持 720 或 1080
		recorderHelper.setVideoEncodingBitRate(size:Int) // 设置比特率 默认为5 特别清楚, 可以设置 1-5之间的值数值越大越清晰
		recorderHelper.reset() // 重置所有界面信息
		recorderHelper.setNotifySys(flag:Boolean) // 是否要通知系统数据库刷新视频信息, 默认为false 
		recorderHelper.setRecordVoice(flag:Boolean) // 是否有录制声音, true 为有声音,flase 为录制无声视频, 默认为true 有声音 
	
	
	// 3 请在activity设置
		
		 override fun onDestroy() {
			recorderHelper.onDestory()
			super.onDestroy()
		    }

		    override fun onStop() {
			recorderHelper.onStop()
			super.onStop()
		    }
	
	// 4 启动 recorderHelper(必须要有的,不然没办法使用)
		recorderHelper.start()
	

	// 5 权限 本项目使用如下权限，请自行动态申请权限
		<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
	    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
	    <uses-permission android:name="android.permission.CAMERA"/>
	    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	     <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>


> 有问题请邮箱联系 herb2sy@gmail.com

## License

	The MIT License (MIT)
	
	Copyright © 2019 <copyright holders>

	Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
	documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
	the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and 
	to permit persons to whom the Software is furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all copies or substantial portions of 
	the Software.

	THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
	TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
	THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
	CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
	IN THE SOFTWARE.
