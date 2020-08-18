

Firstly, hard coded credentials used for this assignment:

        userID: chirag@hellofresh.com
        password: newemployee

Kotlin is used for the development

* Network Structure
        Retrofit is used for calling api
        coroutines for android concurreny
        Glide for image loading

* Architecture pattern used:
        MVVM
        LiveData

* Data Storage:
        Room database for favourite recipe feature
        SharedPreferences for login flow

* All drawable assets are svg images

*UI test case for login Screen
*functional testcases for login viewmodel

*Screens in assigment
        Login
        RecipeList
        RecipeDetail


*Screen Flows:
        1> Login Screen (validate credentials) -> RecipeList Screen (shows favourite/ un-favourite recipe item list )
         -> RecipeDetail Screen (
            Detail view for particular item is shown
            * user can set favourite/ un-favourite for the recipe
            * user can logout from app)

