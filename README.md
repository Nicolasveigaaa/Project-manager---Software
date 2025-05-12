 # Instructions to use application and run tests #

1) To initiate the project it is very important that all java packages are available. 
2) To launch the software select the `Main.java` inside the `app` folder 
3) Before pressing "play" ensure that the file `src/main/java/module-info.java` or something similar is only called `module-info.java`. If not simply rename it to module-info.java
4) Press the "play" button. 

## Running the program

After starting the software a UI screen will pop up, and ask for a "Username/Initials"

The available initials are:

```
1. admin (manager)
2. huba (employee)
3. nico (employee)
```

If you want to change profile/user, press the "log out" button and login with a different user from the list above.
This way you can test out multiple features that are available, and see how the software and user interacts with each other.

## Potential problems

1. If you can't see the project you created when logging in as another user, try logging in as another user. 
2. If an error keeps popping up when trying to log time in an activity, ensure that you have added yourself to the activity beforehand

## Running Cucumber 

1) Setup cucumber before run,  change the file name from `module-info.java` to `module-info-disable.java` this allows for some UI tests to run. 
2)  ! DO NOT TRY TO CHANGE TABS WHILE THE TEST IS RUNNING OR SYSTEM CRASH IS POSSIBLE ! 
3) To start the cucumber tests, select the file `/src/test/java/hellocucumber/RunCucumberTest.java` 
4) Right-click on the file and press "Run Tests" or "Run Tests With Coverage" 
5) Again do not touch any of the opening tabs. 
6) You can now see the test code coverage. 
