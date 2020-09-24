# Android Runtime Permissions

1. With the introduction of Android 6.0 Marshmallow, Google has changed the way permissions are handled by the app.<br>
  ***if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)***
2. In the case we try to call some function that requires a permission which user has not granted yet, the function 
   will suddenly throw an Exception  **(java.lang.SecurityException)**  that will lead to the application crashing.

## Requesting Android Runtime Permissions
1. The method   **requestPermissions(String[] permissions, int requestCode);**   is a public method that is used to request dangerous permissions. 
  We can ask for multiple dangerous permissions by passing a string array of permission.
2. if the permission has been previously granted it is necessary to check again to be sure that the user did not later revoke that permission. For this
  the following method needs to be called on every permission.<br>
  
   ***int permission= checkSelfPermission(String perm);***  It returns an integer value of PackageManager.PERMISSION_GRANTED or PackageManager.PERMISSION_DENIED.
 
3. Call  ***requestPermissions(String[] perms, int requestCode);***  to intiate the process.
