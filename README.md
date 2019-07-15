# k_mediaRecorder
MediaRecorder 录制视频,支持缩放,分段录制

## 使用方法
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
