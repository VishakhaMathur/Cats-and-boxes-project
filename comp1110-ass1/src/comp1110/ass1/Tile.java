package comp1110.ass1;

/**
 * The Tile class defines the state of a particular position on the board
 */

public class Tile {
    // absolute position of tile on the board
    final private IntPair absolutePosition;

    // Enum that define the state of a tile as either being EMPTY or OCCUPIED;
    // it is empty if it doesn't contain a piece segment (box or not) or a cat.
    // it is occupied otherwise
    public enum TileState {
        EMPTY, OCCUPIED;
    }


    // field to store the state of the tile
    private TileState tileState;

    // reference to a piece object which will only be non-null
    // when Tile contains a piece segment
    private Piece possiblePiece = null;

    // boolean that is true when the tile contains a piece segment -
    // that also happens to be a box
    private boolean isBox = false;

    // boolean that is true when the tile contains a cat
    private boolean isCat = false;

    /**
     * Constructor: creates a new empty Tile
     * @param absolutePosition  absolute position of tile on the board
     */
    public Tile(IntPair absolutePosition){
        this.absolutePosition = absolutePosition;
        tileState = TileState.EMPTY;
    }

    /**
     * setter for isCat with extra logic to control state
     * @param isCat
     */
    public void setCat(boolean isCat) {
        if (isCat) {
            setOccupied();
        } else if (possiblePiece == null) {
            setEmpty();
        }
        this.isCat = isCat;
    }


    /**
     * checks if the current tile contains a box
     * @return true if it is a box and false otherwise
     */
    public boolean isBox() {
        return isBox;
    }

    /**
     * setter for isBox
     * @param isBox
     */
    public void setBox(boolean isBox) {
        this.isBox = isBox;
    }

    /**
     * Setter method for possible piece with state updating
     * @param possiblePiece
     */
    public void setPossiblePiece(Piece possiblePiece) {
        if (possiblePiece != null) {
            setOccupied();
        } else {
            isBox = false;
            if (!containsCat()) {
                setEmpty();
            }
        }
        this.possiblePiece = possiblePiece;
    }


    /**
     * getter method for absolutePosition
     * @return absolutePosition
     */
    public IntPair getAbsolutePosition() {
        return absolutePosition;
    }

    /**
     * getter method for tileType
     * @return tileType
     */
    public TileState getTileState() {
        return tileState;
    }

    /**
     * getter method for possiblePiece
     * @return possiblePiece
     */
    public Piece getPossiblePiece(){
        return possiblePiece;
    }

    /**
     * private method to set the state of the tile to occupied
     */
    private void setOccupied() {
        tileState = TileState.OCCUPIED;
    }

    /**
     * private method to set the state of the Tile to empty
     */
    private void setEmpty() {
        tileState = TileState.EMPTY;
    }


    /**
     * @return if the tile state is set to OCCUPIED
     */
    public boolean isOccupied() {
        return tileState == TileState.OCCUPIED;
    }


    /**
     * @return if the Tile has a cat on it
     */
    public boolean containsCat() {
        return isCat;
    }

    /**
     * Converts Transform object to a string which can be printed out
     * @return string representation
     */
    @Override
    public String toString() {
        return "Tile{" +
                "absolutePosition=" + absolutePosition +
                ", tileType=" + tileState +
                ", containsPiece=" + possiblePiece +
                ", isBox=" + isBox +
                '}';

    }

}
