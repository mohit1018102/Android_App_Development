# Content Provider
A content provider manages access to a central repository of data. A provider is part of an Android application, which often provides 
its own UI for working with the data. However, content providers are primarily intended to be used by other applications, which access 
the provider using a provider client object.<br>
<p align="center"><img  src="../image/contentprovider.png"/></p>

## 1. Content Resolver
By using content provider, users can choose to share only some part of the data in one application, thus can avoid privacy data leak in the
program. At present, the use of content provider is the standard way for Android to share data across applications.<br>
```ContentResolver cr= context.getContentResolver();```
## 2. Content URI
Content URI is a unique resource identifier that content provider app provides for client app to access it’s shared data.
```content://authority/path/_id```

```content://content://com.student.details/students/3```

***1.schema :*** This is the content URI protocol, it’s value is content in general.<br>
***2.authority :*** This part is used to distinguish different content provider app to avoid conflict, so generally it is the app package name.<br>
***3.path :*** This part is used to distinguish different shared data in one android content provider app. Different path will return different data.
