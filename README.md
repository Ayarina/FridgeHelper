# FridgeHelper
An app made for an assignment for the course: "Android Programming with Kotlin" for the Virtual Exchange to OTH Regensburg with David Drohan as the lecturer.

This app serves as a helper, as its name says, to easily check all the products that you have at home, its price and the best place to buy it. 

- Each user has its own products, and can only view and edit those
- You can Add, Update and Delete the products, that will be stored in the DataBase
- The quantity of the products can be updated with the ElegantButton Feature, which is a number stepper
- You can also add an Image, it is optional though, as well as the location
- Every product has its best location to buy it from, and all the products' locations can be viewed on a map
- Everything you edit, even the quantity with the number stepper, it is instantly updated on the database

## Features used
- ElegantButton from https://github.com/ashik94vc/ElegantNumberButton
- RecyclerView
- FireBase Database and Authentication
- Google Maps

Both of the API keys are not commited

## Bugs
- There is a small bug when you edit something in the database, it doesn't change instantly on the recyclerView, as the `onDataChange Listener` is encapsulated and I didn't manage to put a `notifyDataSetChanged()` when `onDataChange` executes
