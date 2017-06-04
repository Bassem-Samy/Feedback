# Feedback
## App Description:
A simple app that loads a list of users, sorts them by last interaction's date (desc) and the users with interactions older than 2 weeks or never interacted with has the option to give them a feedback with current time

## Technical Description:
* The app is structured in MVP.
* My most valuable reference for implementing MVP in android is [Antonio Leiva's Post](https://www.dropbox.com/s/r0wy953e50q0cra/feedback-app-release.apk?dl=0)
* I used Dagger2 to inject dependencies in userslisting module. 
* I have changed the Json file data to have a user with interaction that's less than 2 weeks to have a full display of functionality on app launch but if you want to start with the original data or any other data simply rename the json file to "users.json" and replace the existing one under src/main/assets directory.
* Added Android Unit Test to test users listing module.
* Added unit test to test users' sorting according to their latest interaction in descending order.
* Added landscape layout (item-detail) in layout-land directory.
* 3rd party libraries used: RxJava/RxAndroid 2, Dagger2, Butterknife, joda time, Gson, glide and hodenhof's circular image view.
