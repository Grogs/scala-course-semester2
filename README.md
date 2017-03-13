#Lesson 6

Start by checking out the `lesson6`branch

###Step 1 - Use ScalaTags
* Fix the tests in `HotelsControllerSpec` by looking at the clues included
* You can check your progress at [http://localhost:9000/hotels/search?destination=London&distance=1.2](http://localhost:9000/hotels/search?destination=London&distance=1.2)
* You'll need to edit the `apply` method in `views.HotelListingTable`
    * ScalaTags documentation: http://www.lihaoyi.com/scalatags/

###Step 2 - Interactive Search
* Add event handlers to the destination and distance inputs.
    * add logging to verify...
* Make them call the reload function with the new destination and distance
    * add logging to verify...
* Implement to the render function, it should
  * Fetch the new search results using the Autowire `Client`
    * Use `Client[HotelsService]` to call methods on the HotelsService we implemented a few weeks ago. 
    * Autowire documentation: https://github.com/lihaoyi/autowire#minimal-example
  * Generate the new table HTML
  * Replace the previous table with the new table
* Remove the Search button, it's not needed anymore.

##Extensions

###Step 3 - Autocompletion of destinations
Take a look at `fss.Autocomplete` and hook it up to the destination input
