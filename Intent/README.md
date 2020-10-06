# Intent : Android System Glue 
__Android uses Intent as a glue that simplifies the integration of applications that reuse existing components.__
**or** ***(message that provides description of an operation to perform or an event has occurred.)***

## Elements of an Android Intent
**1. NAME** : Name for a component to handle intent ***(optional)***<br>
**2. ACTION** : Operation to perform or performed operation(BRD).<br>
**3. DATA** : URI & MIME type of that data.<br>
**4. CATEGORY** : String giving addition info about action to execute.<br>
**5. EXTRAS** : Key-value pair additional info.<br>
**6. FLAGS** : flags of various sorts.<br>
**Components Interest** : ACTION DATA EXTRAS.<br>
**Acivity Manager Service Interest** : Name category flags.<br>

## There are two types of Intent
### 1. Explicit : (Tightly coupled)(Services)
   ```java
   Intent intent=new Intent(MainActivity.this, AnotherActivity.class);
   ```
### 2. Implicit : (Loosely coupled) (Dynamic resolution) (Late runtime binding) (Not for services)
```xml
 AndroidManifest.xml

 <component> 
  <Intent-Filter>
      <action android: name="package.example.DOWNLOAD_DATA"/>
      <data android: mimeType="*/*"/>
    </Intent-Filter>
  </component> 
  ```
  
  ```java
   main.java
   
   private final String DOWNLOAD_DATA="package.example.DOWNLOAD_DATA";
   ...
   Intent intent=new Intent(DOWNLOAD_DATA, data);
   ```
   


### To start activity use : 
   ```
   startActivity(intent);
   ```
    

Note: use factory methods makeIntent(Context context) to make Intent.
