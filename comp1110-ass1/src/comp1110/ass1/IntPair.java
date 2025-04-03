package comp1110.ass1;

import java.util.Objects;

/**
 * A Pair of ints (primitive Integers) that is used for variety of purposes in this assignment
 */
public class IntPair {
    // x int
    private final int x;
    // y int
    private final int y;

    /**
     * Constructor to create an instance of IntPair
     * @param x
     * @param y
     */
    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new IntPair object that is the element-wise sum
     *
     * E.g., IntPair(2, 3).add(IntPair(-4, 5)) is equal to IntPair(-2, 8)
     *
     * @param other IntPair to add to this IntPair
     * @return a new IntPair which is the element-wise addition of this and other
     */
    public IntPair add(IntPair other) {
        return new IntPair(( this.x+other.x), (this.y+other.y));
        // return pair; // FIXME Task 2
    }


    /**
     * getter method for x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * getter method for y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Creates an IntPair from a string encoding
     * @param commaSepIntPair integers seperated by a comma for example "2,3" or "45,64"
     * @return new IntPair object
     */
    public static IntPair constructFromString(String commaSepIntPair) {
        String[] axis = commaSepIntPair.split(",");
        int x = Integer.parseInt(axis[0]);
        int y = Integer.parseInt(axis[1]);
        return new IntPair(x,y);
    }

    /**
     * return new IntPair that only contains positive values
     * @return new object that is the absolute value of current
     */
    public IntPair abs() {
        return new IntPair(Math.abs(x),Math.abs(y));
    }

    /**
     * Boilerplate method to ensure that .equals() method compares two objects correctly
     * @param o other object that might be an IntPair
     * @return true if this object is equal/equivalent to the other object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntPair position = (IntPair) o;
        return x == position.x && y == position.y;
    }

    /**
     *  Boilerplate method to ensure that an array of IntPair can be sorted
     * @return hash of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Converts IntPair object to a string which can be printed out
     * @return string representation
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}