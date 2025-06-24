**1. Calculator App:**

Explanation: 

Created the Calculator App with Operator Precedence for "*" and "/" perform first and "+" and "-" 

User can perform multiple digit number with multiple operators and calculating result with precedence

Displayed the History as TextView in MainActivity when user press the "Advance with History button"

When user press "History in new window" it takes to SecondActivity to display History in TextView

I have validated the Input user entered

User cannot able to enter the operator first and also can't enter two operators continuously.
While press equal if previous input is operator it will display error message and also can't calculate result without entering input.


**2. Cash Register App:****

Explanation:

-Developed a Cash Register App using Constraint Layout with appropriate constraints for both Landscape and Portrait orientations.
-Product data is stored in SharedPreferences to ensure data persistence when the application is restarted.


Main Activity:
-In the Main Activity, users can select a product from the RecyclerView list and enter the quantity they wish to purchase. The app will automatically calculate the total amount, and users can then click the Buy button to complete the purchase.
-User actions are validated by displaying Toast messages:
	-If the quantity exceeds the available stock.
	-If the user clicks the Buy button without selecting a product or quantity.
-After each successful purchase, an Alert Dialog is shown, displaying the purchase details.
-By clicking the Manager button, users can navigate to the Manager Activity, where they can view purchase history and restock items.

Manager Activity:
-The Manager Activity consists of two sections: History and Restock.

History Section:
-The History section displays purchase records using a RecyclerView. The data is retrieved from a History class object within the app.
-When the user selects an item from the purchase history, detailed information about the transaction, including the date, is displayed.

Restock Section:
-In the Restock section, users can select a product from the list and enter the new stock quantity. After clicking OK, the system validates the input before updating the stock.
-Features:
	-A Floating Action Button (FAB) is provided for adding a new product to the inventory.
	-Another FAB allows users to delete a product from the inventory by selecting the product from the list.
-All the information about Product Stored in Shared Preference for Data Persistent.


**3. Quick Notes App:**

Explanation:

-I have created the App with 3 Activities: Main Activity, View and Edit Activity, and Add New Note Activity
	-Main Activity: for View Truncated content and Title of Each Notes using Lazy Column
	-when user click Floating Button Add in Main Activity, it will take to AddNewActivity Screen, where user can enter Title and Content for new note and Save it to the List and came back to Main Activity for successful save. 
		*I have validated the user Input for empty Title and Content
	-when the user click on each note in Main Activity, it will take you to ViewEditActivity, where user can view the Complete content and Title
		*User can edit the Title and Content and click Save to get back to Main Activity 
		*User can delete the note by click Delete button from ViewEdit Screen


**4. Marvel Character App:**

Explanation:

-App with 4 Activities,

*Main Activity: List of Marvel Character

	-Initially display Marvel Characters in Alphabetical order, by getting list from Api
	-Search bar enter 3 characters to search for specific character, it will display all the character available from Api begins with first 3 letters
	-By selecting each character will ask for save to favorite list or not and take you to the Detail Character View Activity
	-Top left there is favorite button to take to Favorite Character Activity 

*Character Detail Activity(Take CharcterId from MainActivity and fetch info from Marvel API)

	-It contains a Navigation bar at the bottom to navigate between Screens - Detail, Comics, and Series in which the specific character appears
	-Detail Screen: It displays Title, Description, Last Modified, Comic Link and related Urls for detailed View will take to the specific page. I have used Android View to display the WebView for Urls
	-Comics Screen: Displays list of comics available for specific character with Button to take you to the Comic Detail Activity
	-Series Screen: Displays set of series available for specific Character

*Comic Detail Activity(Take ComicId from CharacterDetailActivity and fetch info from Marvel API)

	-It contains a Navigation bar at the bottom to navigate between Screens - Detail, Characters, Creators and Promotional Images for the specific Comic Selected
	-Detail Screen: It displays Title, Description, Last Modified, Comic Link and related Urls for detailed View will take to the specific page. I have used Android View to display the WebView for Urls
	-Character Screen: Displays all the characters in the specific comic
	-Creators Screen: Displays all the creators in the specific Comic with their Role
	-Images Screen: Displays list of Promotional Images for the specific Comic

*Favorite List Activity: (Room Database)

	-Fetch list of Characters from Database which is stored from Main Activity
	-Search Bar helps to search from Character List
	-When click on each character take you to the Character Detail Activity with specific CharacterId and fetch information from Marvel API

 
