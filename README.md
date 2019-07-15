# k_mediaRecorder
MediaRecorder 录制视频,支持缩放,分段录制

[![](https://www.jitpack.io/v/HerbLee/k_mediaRecorder.svg)](https://www.jitpack.io/#HerbLee/k_mediaRecorder)

## 快速引入
#### gradle
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

#### maven
	
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

	 recorderHelper = RecorderHelper(this, rv_test,object :OnRecorderHelperChangeListener{
            override fun startRecorder(file: String) {

            }

            override fun endRecorder(file: String) {

            }

            override fun saveVideo(file: String) {

            }

            override fun onBack() {

            }

            override fun message(msg: String) {

            }

        })
	

