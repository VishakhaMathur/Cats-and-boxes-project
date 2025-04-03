package comp1110.ass1;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class which defines the shape and position of each piece on the board.
 *
 * Note that in the backend: there is no state in which a piece is off the board.
 */
public class Piece {

    // Arrays that define the shape of each piece (see readme for more information)
    private final static int[] pieceOneRelativePositions = {0,-1,-1,0,0,0,1,0};
    private final static int[] pieceTwoRelativePositions = {-1,-1,-1,0,0,0,1,0};
    private final static int[] pieceThreeRelativePositions = {0,-1,-2,0,-1,0,0,0};
    private final static int[] pieceFourRelativePositions = {1,-1,0,0,1,0,1,1,2,1};


    // Arrays that define where the box/s are on each piece
    // Each number represents an index in the respective arrays above
    private final static int[] pieceOneBox= {2};
    private final static int[] pieceTwoBox= {2};
    private final static int[] pieceThreeBox= {3};
    private final static int[] pieceFourBoxes= {1,4};

    // Defines the shape of the piece (see readme for more information)
    private IntPair[] relativeSegmentPositions;

    // indexes of the above relativeSegmentPositions array that are boxes
    private int[] boxIndexes;

    // transformation that is currently applied to the piece
    private Transform transform;

    // number of the piece: 1,2,3 or 4
    private final int id;


    /**
     * Constructor: creates a new instance of the Piece class
     * @param pieceNum number of the piece: 1,2,3 or 4
     */
    public Piece(int pieceNum, Transform transform){
        this.id = pieceNum;
        switch (pieceNum) {
            case 1 -> {
                relativeSegmentPositions = intsToIntPairs(pieceOneRelativePositions);
                boxIndexes = pieceOneBox;
            }
            case 2 -> {
                relativeSegmentPositions = intsToIntPairs(pieceTwoRelativePositions);
                boxIndexes = pieceTwoBox;
            }
            case 3 -> {
                relativeSegmentPositions = intsToIntPairs(pieceThreeRelativePositions);
                boxIndexes = pieceThreeBox;
            }
            case 4 -> {
                relativeSegmentPositions = intsToIntPairs(pieceFourRelativePositions);
                boxIndexes = pieceFourBoxes;
            }
        }
        this.transform = transform;
    }

    /**
     * Creates an array of positions (in the form of IntPars) from an array of int
     * @param intArray array of type int
     * @return new array of IntPairs
     */
    IntPair[] intsToIntPairs(int[] intArray){
        // checks that the input array has an even number of elements
        assert intArray.length % 2 == 0;

        IntPair[] positionArray = new IntPair[intArray.length / 2];
        for (int i=0; i<intArray.length; i=i+2){
            int currX = intArray[i];
            int currY = intArray[i+1];
            positionArray[i/2] = new IntPair(currX,currY);
        }
        return positionArray;
    }

    /**
     * setter method for transform
     * @param transform
     */
    public void setTransform(Transform transform){
        this.transform = transform;
    }

    /**
     * getter method for transform
     * @return
     */
    public Transform getTransform(){
        return transform;
    }

    /**
     * @return absolute positions for the current piece (see readme for more info)
     */
    public IntPair[] getAbsolutePositions(){
        return calcNewAbsolutePositions(relativeSegmentPositions,transform);
    }


    /**
     * This method converts the relative position of the segments to absolute positions using the given transform.
     * See definition of relative and absolute positions in the readme.
     *
     * @param relativeSegmentPositions relative position of the segments of a particular piece. Note that this method must _not_ modify this array.
     * @param transform transformation to applied to the relative positions
     * @return a new array of absolute positions after transformation in the same order as relativeSegmentPositions
     */
    public static IntPair[] calcNewAbsolutePositions(IntPair[] relativeSegmentPositions, Transform transform){
        IntPair[] array = new IntPair[relativeSegmentPositions.length];
        for (int i = 0; i < relativeSegmentPositions.length; i++) {
            IntPair x = transform.applyTransform(relativeSegmentPositions[i]);
            array[i] = x;
        }



        //current position of ach segment in the piece
        //rotate based on transform
        //add int[] in transform to current piece position.


        return array; // FIXME Task 6
    }

    /**
     * Calculates the new potential absolute positions given a new transform
     * @param newTransform transformation which represents a new move
     * @return array of absolute positions in the same order as the piece is defined (relativeSegmentPositions)
     */
    public IntPair[] calcNewAbsolutePositions(Transform newTransform){
        return calcNewAbsolutePositions(relativeSegmentPositions,newTransform);
    }

    /**
     * Checks if the index provided corresponds to a box segment.
     * In other words, checking if the index is in boxIndexes
     * @param index to check
     * @return true if segment is a box, false otherwise
     */
    public boolean indexCorrespondsToBox(int index) {
        for (int i = 0; i < boxIndexes.length; i++) {
            if (index == boxIndexes[i]) {
                return true;
            }

        }
        return false;
        }





        /*if ((pieceOneRelativePositions [index] == 0) && (pieceOneRelativePositions [index] == 0)) {
            return true;
        }

        else if ((pieceTwoRelativePositions [index] == 0) && (pieceTwoRelativePositions [index] == 0)) {
            return true;
        }

        else if ((pieceThreeRelativePositions [index] == 0) && (pieceThreeRelativePositions [index] == 0)) {
            return true;
        }

        else if ((pieceFourRelativePositions [index] == 0) && (pieceFourRelativePositions [index] == 0) &&
                (pieceFourRelativePositions [index] == 2) && (pieceFourRelativePositions [index] == 1)){
            return true;
        }*/


         // FIXME Task 4




    /**
     * Calculates the absolute position of the boxes for the piece.
     * @return an array of positions that is either 1 or 2 elements long
     */
    public IntPair[] getAbsoluteBoxPositions() {
        IntPair[] boxPositions = new IntPair[boxIndexes.length];
        IntPair[] absolutePositions =  getAbsolutePositions();
        for (int i=0; i<boxIndexes.length; i++){
            boxPositions[i] = absolutePositions[boxIndexes[i]];
        }
        return boxPositions;
    }

    /**
     * getter method for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter method
     * @return length of the relativeSegmentPositions array
     */
    public int getSegmentLength() {
        return relativeSegmentPositions.length;
    }

    /**
     * getter method for the box indexes
     * @return boxIndexes
     */
    public int[] getBoxIndexes() {
        return boxIndexes;
    }

    /**
     * Boilerplate method to ensure that .equals() method compares two objects correctly
     * @param o other object that might be of type Piece
     * @return true if this object is equal/equivalent to the other object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return id == piece.id && Arrays.equals(relativeSegmentPositions, piece.relativeSegmentPositions) &&
                Arrays.equals(boxIndexes, piece.boxIndexes) && Objects.equals(transform, piece.transform);
    }


    /**
     * Converts Piece object to a string which can be printed out
     * @return string representation
     */
    @Override
    public String toString() {
        return "Piece{" +
                ", id=" + id +
                "relativeSegmentPositions=" + Arrays.toString(relativeSegmentPositions) +
                ", boxIndexes=" + Arrays.toString(boxIndexes) +
                ", transform=" + transform +
                '}';
    }

    public static void main(String[] args) {

    }
}
