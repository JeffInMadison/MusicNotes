MusicNotes Mobile Developer Challenge
==========

The sample code I am submitting is for an Android device. The problem specification stated:

> Use his provided UI designs to 
> complete this iteration of the app using objective-c, Android Java or Xamarin C#. He 
> apologizes that his UI looks a lot like iOS so do your best if you choose Android to follow the 
> same basic design. 

The design given is pretty iOS-centric. To make it look and feel like an application that an Android user would expect I followed the Android developer guidelines and tried to make it match closely what the designs looked like.

For example:


-  iOS buttons lost their visual size and went to just words with a default blue text. I replaced those UI elements with the appropriate actionbar actions with icons. When you rotate the view and have a larger actionbar the text will show.
-  In Android the idea that you can go back in a view is signified with a chevron pointing left. In iOS it is typically a button labeled back.
-  The actionbar usually displays the app's logo and page titles to their right. Vs in the middle on iOS.

Persistence
------
In order to persist the created schemes when the app closes and relaunches I created a very basic SQLite database to store them. It is by no means robust and not really an example of my ability to manage data on a device but merely a quick way to store and retrieve data. 

Preview
------
One of the reason's I stuck with Android is this element. I knew the preview page had some elements that are not easily accomplished on Android so I wanted to see how hard it would be to replicate it. The touch event structure let me know when I was touching within a view but didn't tell me when I was leaving the view. I ended up having to do some coordinate checking to accomplish this and the code isn't terribly efficient. I created a custom view to accomplish that. ColorPreviewTabWidget is that control. I also added the preview color view to a pager view. So you can swipe along the ColorPreviewTabWidget and affect the preview or swipe the preview back and forth and effect the position selected in the ColorPreviewTabWidget. 

Edit Mode - Drag Sort and Delete
------
The drag & sort mechanism is also not built into Android in any way. There is a library that is commonly used to do this called the DragSortListView written by Carl Bauer. It is no longer maintained but still used and works pretty well.

Ideally on Android the way to select and manage a list would be through the ActionBar contextual edit. Must users would expect to long click on a listview and have the actionbar change indicating a new mode. Then select or multiselect an item(s) and use the action icons on the actionbar to act on them. This is partially why the DragSortListView is not an active project. I decided to follow the the spec given but could just as easily have made use of the actionbar.

 