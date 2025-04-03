package comp1110.ass1;



public class CatsAndBoxes {
    // The width of the board (left to right)
    public final static int BOARD_WIDTH = 5;

    // The height of the board (top to bottom)
    public final static int BOARD_HEIGHT = 5;

    // number of pieces in the game
    public final static int NUMBER_OF_PIECES = 4;

    // number of cats in the game
    public final static int NUMBER_OF_CATS = 5;

    // maximum number of free spaces on the board
    public final static int MAX_FREE_SPACES = (BOARD_WIDTH * BOARD_HEIGHT) - 12;

    // an upper bound on the number of possible moves a piece can make
    public final static int MOVES_UPPER_BOUND = MAX_FREE_SPACES * Angle.values().length;


    // The matrix of tiles representing the puzzle board
    // For boardMatrix[x][y]:
    //   x corresponds to the tile column, working left to right, and
    //   y corresponds to the tile row, working top to bottom.
    public final Tile[][] boardMatrix;


    // Array of Piece objects (instances of the piece class)
    private final Piece[] pieces;

    // The specific puzzle challenge to solve
    public final Challenge challenge;

    // Array of the positions of the cats on the board
    public final IntPair[] catPositions = new IntPair[NUMBER_OF_CATS];


    /**
     * Generate a new random instance of Cats And Boxes with the provided difficulty.
     *
     * @param difficulty the given challenge difficulty
     */
    public CatsAndBoxes(int difficulty) {
        this.challenge = Challenge.randomChallenge(difficulty);
        boardMatrix = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
        pieces = new Piece[NUMBER_OF_PIECES];
        populateBoard(challenge.getLayout());
    }

    /**
     * Cats And Boxes with a specific challenge
     *
     * @param challenge specific challenge
     */
    public CatsAndBoxes(Challenge challenge) {
        this.challenge = challenge;
        boardMatrix = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
        pieces = new Piece[NUMBER_OF_PIECES];
        populateBoard(challenge.getLayout());
    }


    /**
     * Decodes the string representation of each challenge and converts into objects.
     * More specifically, it populates the boardMatrix, creates the cats and pieces.
     * In assignment 2 you will have to write a similar method!
     *
     * @param challengeLayout string encoding of a challenge - see section in Readme
     */
    public void populateBoard(String challengeLayout) {
        // creating empty tiles
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                boardMatrix[x][y] = new Tile(new IntPair(x, y));
            }
        }
        //     decoding the challenge string:
        String[] layoutParts = challengeLayout.split(" ");
        // creating the cats
        for (int i = 0; i < NUMBER_OF_CATS; i++) {
            IntPair catPosition = IntPair.constructFromString(layoutParts[i]);
            catPositions[i] = catPosition;
            if (getTileFromPos(catPosition) != null) {
                getTileFromPos(catPosition).setCat(true);
            }
        }
        // creating the pieces
        for (int pieceNum = 1; pieceNum <= NUMBER_OF_PIECES; pieceNum++) {
            Transform pieceTransform = Transform.constructFromString(layoutParts[NUMBER_OF_CATS + pieceNum - 1]);
            pieces[pieceNum - 1] = new Piece(pieceNum, pieceTransform);
            for (IntPair piecePos : pieces[pieceNum - 1].getAbsolutePositions()) {
                if (getTileFromPos(piecePos) != null) {
                    getTileFromPos(piecePos).setPossiblePiece(pieces[pieceNum - 1]);
                }
            }
            for (IntPair boxPos : pieces[pieceNum - 1].getAbsoluteBoxPositions()) {
                if (getTileFromPos(boxPos) != null) {
                    getTileFromPos(boxPos).setBox(true);
                }
            }
        }
    }


    /**
     * Helper method to fetch a particular tile given by a position represented as an IntPair
     *
     * @param position in the board matrix (see readme for explanation)
     * @return Tile at that position if on the board, null otherwise
     */
    private Tile getTileFromPos(IntPair position) {
        if (withinBoard(position)) {
            return boardMatrix[position.getX()][position.getY()];
        } else {
            return null;
        }
    }

    /**
     * returns a piece given the number of that piece
     *
     * @param pieceNum number given to the piece: 1,2,3 or 4
     * @return correct piece
     */
    public Piece getPiece(int pieceNum) {
        return pieces[pieceNum - 1];
    }


    /**
     * Checks if a position represented as an IntPair is on the board
     *
     * @param position to check
     * @return true if it is on the board
     */
    public boolean withinBoard(IntPair position) {
        boolean isPositionRight;
        int x = position.getX();
        int y = position.getY();

        if ((x >= 0 && x <= 4) && (y >= 0 && y <= 4)) {
            return true;
        }
        return false; // FIXME Task 3
    }

    /**
     * Checks if the current game state represents a solution to the current challenge.
     * You may assume that all rules of the game have been followed correctly and
     * that the challenge is valid (eg. doesn't contain an incorrect number of cats).
     *
     * @return true if it is a solution, false otherwise
     */
    public boolean isSolution() {
        // position of cats should overlap with position of boxes
        IntPair[] boxPositions = this.pieces[0].getAbsoluteBoxPositions();
        IntPair[] boxPositions1 = this.pieces[1].getAbsoluteBoxPositions();
        IntPair[] boxPositions2 = this.pieces[2].getAbsoluteBoxPositions();
        IntPair[] boxPositions3 = this.pieces[3].getAbsoluteBoxPositions();
        IntPair box1 = this.pieces[0].getAbsoluteBoxPositions()[0];
        IntPair box2 = this.pieces[1].getAbsoluteBoxPositions()[0];
        IntPair box3 = this.pieces[2].getAbsoluteBoxPositions()[0];
        IntPair box4 = this.pieces[3].getAbsoluteBoxPositions()[0];
        IntPair box5 = this.pieces[3].getAbsoluteBoxPositions()[1];

        for (int i = 0; i < this.catPositions.length; i++) {
            if (!(box1.equals(this.catPositions[i]) ||
                    box2.equals(this.catPositions[i]) ||
                    box3.equals(this.catPositions[i]) ||
                    box4.equals(this.catPositions[i]) ||
                    box5.equals(this.catPositions[i]))) {
                return false;
            }
        }
        return true;
    }

           /* if (box1.equals(this.catPositions[i])) {

                return true;
            } else if (box2.equals(this.catPositions[i])) {
                return true;
            } else if (box3.equals(this.catPositions[i])) {
                return true;
            } else if (box4.equals(this.catPositions[i])) {
                return true;
            } else if (box5.equals(this.catPositions[i])) {
                return true;
                // FIXME Task 7
            }*/





    /**
     * Checks if a potential move violates any rules of the game.
     * We represent a move as the combination of:
     * @param pieceToMove the piece that will be moved (which has some transform already applied - see Piece class)
     * @param newTransform a new transform that will be applied that effectively moves the piece
     * <p>
     * This method needs to check the following game rules:
     *   - All segments of the piece need to stay on the board
     *   - All segments of the piece cannot be placed on top of another piece
     *   - Only box segments may sit on top of cats
     * And additionally that newTransform actually moves the piece (ie doesn't stay in the same spot)
     * <p>
     *
     * Hint: you may find calcNewAbsolutePositions() in the Piece class useful
     *
     * @return true if the move is valid, false otherwise
     */
    public boolean isMoveValid(Piece pieceToMove, Transform newTransform){
        return true; // FIXME Task 8
    }

    /**
     * Defines how pieces are moved.
     * <p>
     * isMoveValid() should always be checked before calling this method!
     *
     * @param pieceToMove the piece that will be moved
     * @param newTransform a new transform that will be applied that effectively moves the piece.
     */
    public void movePiece(Piece pieceToMove, Transform newTransform) {
        // removes piece from the tiles in the board matrix
        for (int x = 0; x< BOARD_WIDTH; x++) {
            for (int y = 0; y< BOARD_HEIGHT; y++) {
                if (boardMatrix[x][y].getPossiblePiece() == pieceToMove) {
                    boardMatrix[x][y].setPossiblePiece(null);
                }
            }
        }
        // applying new transform to the piece that defines the move
        pieceToMove.setTransform(newTransform);
        // finding the new positions on the board
        IntPair[] absolutePositions = pieceToMove.getAbsolutePositions();
        for (int i=0; i<absolutePositions.length; i++) {
            IntPair currPos = absolutePositions[i];
            if (getTileFromPos(currPos) == null) {
                continue;
            }
            getTileFromPos(currPos).setPossiblePiece(pieceToMove);
            if (pieceToMove.indexCorrespondsToBox(i)) {
                getTileFromPos(currPos).setBox(true);
            }
        }
    }


    /**
     * Finds all the possible moves that could be made with a given piece given the current board position.
     * We represent a move by the combination of a piece and a transform object (as seen in isMoveValid).
     * <p>
     * Hint 1: you will likely find the isMoveValid() method useful
     * Hint 2: to get an array of all values of an Enum class use EnumClass.values()
     *
     * @param piece the piece for which to find all valid moves
     * @return an array containing transforms objects corresponding to valid moves followed by null values.
     * <p>
     * For example: moves = {transform1,transform2,null,null, ... ,null}.
     */
    public Transform[] findAllValidMovesForPiece(Piece piece){
        Transform[] moves = new Transform[MOVES_UPPER_BOUND];
        return moves; // FIXME Task 9
    }



    /**
     * Finds all the possible moves that could be made given the current board position.
     * @return a 2D array where the first index represents the piece and the second represents a move for that piece.
     * For example: validMoves[0][4] represents the 5th valid move for piece 1.
     * Note that pieces are zero indexed here.
     *
     * like findAllValidMovesForPiece() valid moves should be followed by null values.
     */
    public Transform[][] findAllValidMoves(){
        Transform[][] validMoves = new Transform[NUMBER_OF_PIECES][MOVES_UPPER_BOUND];
        return validMoves; // FIXME Task 9
    }












}
