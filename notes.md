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

### Phase 2:
- create sequence diagrams to represent your understanding of the phase 3 spec
- can receive 0, 50, 100 on it. You have one week after that to do what they said and resubmit if you do bad
- requests come in and hit the server as handlers, which you write
- those can be plugged into your server, and they create an instance of a service class which you create
- should be able to clear database, register, login
- gson needs a class that matches gson string