# Security and sustainability

Building secure environment for application users.

## Economy of Mechanism

Economy of mechanism is about making things as simple as possible.<br>
--> avoid complex solutions.<br>
--> Don't build anything, you don't need it because you have to protect it over entire life of your app.

## Least Privilege

More privilegs (like camera, location, etc.) you takes, more potebtial thereis that these privileges can accidentally leaked to somebody else.
```
Once you take the privilege you become responsible for it.
```
``` xml
Example: Using
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
for zipcode, we are voilating Least privilege rules.
       --> for zipcode we accessing location of user.
       --> Uses Gps costs battery life too.
       --> we can find zipcode by cell tower or wifi used by user.

<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## Complete Mediation

Building application may store sensitive data or sensitive capabilities. So, while designing such system we use the principle Complete Mediation.
<br>
```
Everytime accessing sensitive data
                    --> Check(valid access)
                            --> access Granted.
                    --> else Rejected.
```

## Secure Defaults

Default behaviour (Security) of the app must be secure.

Secure default is really about thinking through the design of your application and implementation. So that in every Line of code; in every configuration settings
in every installation decision, whatever you're doing, if the user or developer or system admin or whosoever, does nothing else, they will get security rights.

``` java
 example :
 
 public void onClick(View view)
 {
     if( v==LoginButton)
     { 
        // do login process
     }
     else
         if(v== RegisterButton)
         {
            // do register process
          }
         else
           {
               // forget password
            }
 }
 
 In above case, default case will going to run if none of the case is true. Creating loophole for attacker to attack.
 
 Developer should specify the particular case for it and avoiding default cases.
                by replacing default case by,
                      if(v==ForgetButton)
                      { 
                         //Forget password
                       }
                       else
                       { 
                          // Invalid click/ operation
                        }
```


       
