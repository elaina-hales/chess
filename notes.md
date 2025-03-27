# My notes
### Phase 0:
- code for this phase goes in the shared folder
- the tests for this phase are also in the shared folder
- ChessMove: promotionPiece is what you want to turn the pawn into when it hits the back row
- have a data structure where you can store the pieces on your board (he recommends a 2D array/matrix)
- ChessPiece:
  - return a collection of all the valid moves
  - don't subclass the chess piece
  - to get a PieceType use PieceType.QUEEN etc
  - create a new interface called PieceMovesCalculator 
  - pieceMoves checks pieceType and then creates an instance of it accordingly
  - if statements in the ChessPiece class
  - PieceMovesCalculator is one interface (calculator) and then the KingMovesCalculator is a class that calls PieceMovesCalculator

### Phase 1:
- valid moves -- delegate to chess piece classes
- remove movements that thought could make but may be check moves
- Stalemate: no possible moves that would not put my king in check

### Phase 2 and 3:
- create sequence diagrams to represent your understanding of the phase 3 spec
- can receive 0, 50, 100 on it. You have one week after receiving feedback to resubmit if you do poorly
- requests come in and hit the server as handlers, which you will write
- those requests can be plugged into your server, and they create an instance of a service class which you create
- should be able to clear database, register, login
- gson needs a class that matches gson string
- Model is a set of record classes, they should be strung together in a package
- Data Access object-- arraylist of game data, another package called dataaccess - info about how to access data
- Service classes: business logic goes here, does the actual logic for data access and models
- request objects are json related classes and objects, use gson to parse that, gson then sends back appropriate result
- Handler classes: receive a request from a client and then looks at the request data that comes in, convert into a json object then passes into the service classes
- requests and responses should be record classes

### Phase 4:
- you must write jdbc code to create your database
- take a look at DatabaseAccessExample.java in the slides for relational databases 
- database creator should be called every time your server is started, use a parent class with a static initializer and then three separate auth, user, and game classes
- have to add code to databaseManager.java bc it does not create your tables 
- you must hash passwords when you store them in the db
- you cannot know what your password is based on the hash

### Phase 5:
To do:
- draw menus and handle input
- draw chess board -- do this first
- invoke server API endpoints 
- write tests
Other:
- you must allow multiple instances to run things
- three menus -- login/register, quit ; join game menu; move menu
- observe game is just draw board in its initial state -- do not have to make moves
- serverFacade class goes in a .net package (network)
  - when they choose login method, create a LoginRequest obj and call serverFacade class (returns loginResult)
  - when they choose register, take a registerRequest and return registerResult
  - create a UI package for the console ui
  - ui.chessboard should have black/white passed in as an enum so you can figure out how to draw it 
  - there are unicode chess characters
  - try to not hardcode the rows, check and see if a piece needs to be rendered there
  - client should not know how to draw the board

### Phase 6:
- you are adding notifications this time
- User Game commands (from client)
  - connect -- connect to the game as a player or observer
  - make_move -- player move
  - leave -- abandon game
  - resign -- admit defeat
- WS communicator receives messages and displays notifications 
- see the slides for the websocket request handler class
- work on make move last 
- ServerMessage (from Server)
  - load_game
  - notification
  - error
- see code online for details ^
- this is a longer phase -- takes the most time