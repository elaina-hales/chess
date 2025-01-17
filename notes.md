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