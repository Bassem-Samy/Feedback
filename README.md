# Feedback
## App Description:
A simple app that loads a list of users, sorts them by last interaction's date (desc) and the users with interactions older than 2 weeks or never interacted with has the option to give them a feedback with current time

## Technical Description:
* The app is structured in MVP.
* My most valuable reference for implementing MVP in android is [Antonio Leiva's Post](https://github.com/antoniolg/androidmvp)
* I used Dagger2 to inject dependencies in userslisting module.
* Added Android Unit Test to test users listing module. (for the tests to pass make sure the dates for the last interacted items to be recent i.e. less than two weeks old)
* Added unit test to test users' sorting according to their latest interaction in descending order.
* Added landscape layout (item-detail) in layout-land directory.
* 3rd party libraries used: RxJava/RxAndroid 2, Dagger2, Butterknife, joda time, Gson, glide and hodenhof's circular image view.
