#Lesson 5

Last week you implemented the HotelsController and added an endpoint to search for hotels.  

This week we flesh out that search results page.  
  
Start by checking out the `lesson5`branch

Fix the tests in `HotelsControllerSpec` by looking at the clues included
* You can check your progress by running `./sbt run` and then browsing to http://localhost:9000/hotels/search?destination=london&distance=1.2
* You can run the tests on any file change by running this in the terminal:
    * `./sbt "~testOnly *.HotelsControllerSpec"`
* You'll need to edit the `searchResults.scala.html` view
    * Docs for Play's templates: https://www.playframework.com/documentation/2.5.x/ScalaTemplates  
    
You should end up with something like this: https://limitless-lowlands-73789.herokuapp.com/hotels/search?destination=london&distance=1.2
