# Goal : developing Sustainable Application

Sustainable means how well your app can evolve over time and be supported (survive inside AppStore).<br>
(adding new features, fixing bugs, security threats, etc.)

## Software Engineering as a Search

Creating software is a search process. Eg. Let consider an example of HOT/COLD game, in which person has to move from position X to Y(unknown to X).
There is a moderator who will tell you about your moves. Moves are defined as Hotter(means closer to the solution) and colder(farther from the solution). In
this way you have to move from point X to unknown point Y.

In similar way Software Engineering is the process of searching hotter solution which can fullfill the need of the clients according to their requirements.

When we have to create an application/ Software we generally go through following steps:<br>
1. Build or change
2. Measure or evaluate
3. Termination criteria.

## Why Test ?
(sustainable app dev) (evolving apps)

Everytimes we change anything inside an application, we should have to have a way of meaasuring its impacts.
```java
problem: How do we know what to test ?
         --> Test as much as possible.
```

### UNIT TESTING

An application consist of many interconnected components. When we tested the application as a whole, then it will difficult to identify the bugs or error.

-->Unit Testing seperates the component and isolate them. (easy to detect bugs)
-->By unit testing we can pin point error on particular component and fix it.

### Unit Testing using Java JUnit framework.

 ```java 
   -->java
        -->code
        -->AppicationTest
        -->(test)UnitTest 
 ```
    
    
  ```java
     LoginUtils.java
         boolean isValidEmailAddress(String email)
         { 
            boolean hasAtSign=email.index("@")>-1;
            return hasAtSign;
         }
  ``` 
     
     
     
  ```java 
        LoginUtilsTest.java
        
        public class LoginUtilsTest{ // creating new instance for each Test
        
          private LoginUtils utils;  
         
           @Before
           public void setup() // execute before ever test
          {
             utils=new LoginUtils();
          }
         
          @Test
           public void aValidEmailAddressPasses() throws Exception // Test Method
          {
             assertTrue(utils.isValidEmailAddress("foo@bar.com"));
          }
     }
  ```

## The Importance of Test Automation
Manual testing is time consuming task. (slow test->bug->developer)(focus on changes).<br>

### Solution : Test Automation.
Writing code for things user will interact with application. (programmatically click and check).

*Downside : Could be manually Tested.*


## Code Coverage

``` 
How to measure tests themselves ?
        --> some metric needed
```
Code coverage metric senses most things covered by your code or not.

``` java
 public class Foo
 {
         public int bar(int a)
         {
                  if(a>1)
                    ...
                    else
                       ...
          }
   }
```
In above case, 1 method is covered (method wise code coverage is 100%).<br>
But, cases wise we required two values of a [ a>1, a<1 ] to cover all test cases.<br>

***Code coverage tells " how much portion of a software has been tested or hasn't been tested by code. It doesn't mean that your application
is perfect or good, if it achieves particular code coverage score.***


## Functional and Non Functional Properties

***1. Functional properties***<br>
         ->Input to output (does it produce correct output?)<br>
***2. Non Fuctional Properties***<br>
         -> performance<br>
         ->Security<br>
         ->Extensibility<br>

## Refactoring and Regression Testing

Refactoring is a concept in which we don't add any new features, but it improves the non functional properties.

**problem : Refactoring the code might end up breaking previous code.**

Regression Testing is testing for refactored code.<br>
``` java
   A ---> Refactoring ---> a a' a" --->Regression testing.
```

## Integration Testing
Unit Testing alone is not sufficient.<br>

Testing in the context of eachother, how they interact with each other called Integration testing.

### Integration Testing with Android Studio

In android, integration tests are done with what are called Instrumentation tests.<br>

```java
public class GeoUtils{
         private GeoCoder geocoder;
         
         public GeoUtils(Context ctx)
         {
            geocoder=new Geocoder(ctx,Lov=cale.getDefault());
         }
         
         public String getCurrentZipCode(double lat, double lng)
         {
            List<Address> addressloc=geocoder.getFromLocation(lat,lag);
            return adressloc.get(0).getPostalCode();
          }
 }
```
          
``` java
@RunWith(AndroidJUnit4.class) // tells the system that run it as a Instrumental test
public class GeoUtilsIntegrationTest{
         /* Instrumental test runs on emulator or local connected device. build and test */
         
         private GeoUtils geoUtils;
         
         @Before
         public void setup()
         {
            Context ctx=InstrumentationRegistry.getContext();
            geoUtils=new GeoUtils(ctx);
          }
          
          @Test
          public void aTestLocation() throws Exception
          {
            String zipcode=geoUtils.getCurrentZipCode(36.139017,-86.6796924);
            assertEquals("37212", zipcode);
          }
 }
```


   



     
     
        
        
