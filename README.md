Auction System Sample App
================================
It is an standalone Android application that uses the latest Android SDK 5.0.1, Ormlite 4.48 to provide its an Auction System. 
The application uses the famous Navigation Drawer pattern to provide an easy navigation flow. The application uses Fragment based 
approach to give the feeling of a single page application and a very smooth transition between screens. Ormlite is used to build 
an extensive application backend.

The main idea is to allow users to submit Auction items on which other app users can bid. Every auction item has a bidding period. The landing 
screen of the app is basically a auctioning feed for all the items available for bidding. User can pick item from that feed and submit its bid.
Bid with highest amount becomes the winner after the bidding closes. There are other options for user to view auctions submitted by him/her, 
option to view the Auctions won by user and the related seller details. User can view all the bids on an auction item submitted by user along with
bid details and username of bidder.

Target SDK: API 21
Min SDK: API 10

PS: The application uses AppCompat v7 support library, so I have included that in the Source folder as well.
The main source code for our project is in 'AuctionSystem' folder.

Main entities:
User - models an the app user
Auction Item - models an auction item, auctions have a start and end time based on which they shown to user for bidding
Item Photo - user can submit item photo as well, this entity saves an auction item photo
Bid - models a bid by user, for submitting a new bid, user must quote higher amount than the existing bids

System Bot
The application has an inbuilt system bot user. It automatically bids everytime a new Auction is created.
username: system
password: password

The system has following main screens
- Splash: a small welcome splash is added for couple of seconds

- Login screen: This screen helps valid users access the application. New users can navigate to the Signup screen to create new account
for themselves and there by start using the application.

- Signup screen: User can create a new account to start using the application. User needs to enter fullname, email, username (unique), password 
to create an account. The password is stored in md5 encrypted form.

- Home screen (Auctioning now!): This is landing screen after successful login. Auctions submitted by other which are available for bidding at 
time user logs into the app are shown on this screen. User can select any item from list and move to Item details screen to bid.

- Create Auction screen: Second option in navigation drawer takes user to this screen. From this screen, user can submit new Auction items.
Auction item has title, description, bid start time, bidding open duration, option to submit item images using Camera, Item Base Amount input fields.
User can fill in all the details and submit the auction item for bidding by other users.

- My Auctions screen: This is third option on the navigation menu. User can view all the Auction Items submitted into system by him/her. On clicking any item,
a screen with all the bids on that item are shown in a new screen.

- Auction Won screen: 4th option on the navigation menu. Here all the auctions won by user shown. On clicking any the item, the corresponding seller details are 
shown in a popup menu.

- Auction Item screen: clicking on an item from Auctioning now list of items, a screen with Auction details is shown to user. User can view the item details 
like title, description, base price, item image (if any), and the time remaining before bidding closes for that item. There is also a button to open the dialog 
to submit a bid for this item.

- Submit Bid Dialog: This is where user submits a new bid by entering a higher amount than the existing bids. Base amount and highest bid amount are shown
on this dialog with 2 input fields to enter bid amount and some bid notes (optional). On submitting a bid, the 'Bid now!' freezes for the current user.
User can submit only one bid for an item. Now, user waits for the bidding to close. The highest amount bidder wins when the bidding closes for an item.

- Seller details dialog: On Auctions Won screen, user can click on the item to view the seller details. Username, email, name are show in a dialog.

- Logout option: User can logout of the application using the Logout option from navigation menu

- Remember me option: This option is provided on Login screen for app to remember user login and skip login on next app luanch. This property is checked 
by default, user can uncheck if required.

- The app uses a background service to bid on newly created Auction items. These bids are from a system user.

Technologies:
Java, Android SDK, SQLite

Development Tools:
Eclipse IDE, Android ADT Plugin

Additional Libraries/Platforms:
Android SDK 5.0.1, AppCompat v7, Ormlite, UniversalImageLoader
