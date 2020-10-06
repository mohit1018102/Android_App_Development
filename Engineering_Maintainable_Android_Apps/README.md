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
     
     
        
        
