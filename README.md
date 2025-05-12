📖 # READ ME #

👋 // Welcome User, Initiate Project //

1) To initiate the project it is very important that all java packages are available. ⚙️
2) To launch the software select the `Main.java` inside the `app` folder ▶️
3) Before pressing "play" ensure that the file `module-info.java` is only called `module-info.java` (if not read below) 🔍
4) Press the "play" button. ▶️

🛠️ // Problems //

If the project is not running it is probably because of `module-info.java` is called `module-info-disable.java`. ❌

* Rename the file to the correct name `module-info.java` to run the program. 🔄

🚀 // Running the program //

After starting the software a UI screen will pop up, and ask for a "Username/Initials"

The available initials are:

```
1. admin (manager)
2. huba (employee)
3. nico (employee)
```

🔄 If in any case you want to change profile/user, press the "log out" button and login with one of the following initials from above.
This way you can test out multiple features that are available, and see how the software and user interacts with each other.

❓ \* User Questions \*

1. Why can't I see the project I created when logging in as another user?

   * Try logging in as another user. 🔄

2. I am trying to log time in an activity but an error keeps popping up!

   * You have to add yourself to the activity to be able to log time ⏱️

🍃 // Running Cucumber //

1) Setup cucumber before run,  change the file name from `module-info.java` to `module-info-disable.java` this allows for some UI tests to run. 🧪
2) ! DO NOT TRY TO CHANGE TABS WHILE THE TEST IS RUNNING OR SYSTEM CRASH IS POSSIBLE ! ⚠️
3) To start the cucumber tests, select the file `/src/test/java/hellocucumber/RunCucumberTest.java` ▶️
4) Right-click on the file and press "Run Tests" or "Run Tests With Coverage" ▶️
5) Again do not touch any of the opening tabs. 🤚
6) You can now see the test code coverage. 📊
