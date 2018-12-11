# ShortLinks


## Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

## Add the dependency

```
	dependencies {
    	        implementation 'com.github.cuihp:ShortLinks:v1.0.0'
    	}
```
## How to use
```
	  ShortLinkUtils.newIns().toTransformation("https://blog.csdn.net/jennyliliyang/article/", new ShortLinkUtils.CallBack() {
                 @Override
                 public void onResult(RespResult respResult) {
                     Toast.makeText(MainActivity.this, respResult.getUrl_short(), Toast.LENGTH_SHORT).show();
                 }
     
                 @Override
                 public void onError() {
     
                 }
             });
```
