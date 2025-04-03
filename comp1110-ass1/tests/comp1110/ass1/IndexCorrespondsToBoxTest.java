package comp1110.ass1;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class IndexCorrespondsToBoxTest {

    @Test
    public void testOne(){
        assertAll(
                () -> assertTrue((new Piece(1, null)).indexCorrespondsToBox(2), "Expected True for piece 1, index 2"),
                () -> assertTrue((new Piece(2, null)).indexCorrespondsToBox(2), "Expected True for piece 2, index 2"),
                () -> assertFalse((new Piece(2, null)).indexCorrespondsToBox(3), "Expected False for piece 2, index 1"),
                () -> assertFalse((new Piece(3, null)).indexCorrespondsToBox(1), "Expected False for piece 3, index 1"),
                () -> assertFalse((new Piece(4, null)).indexCorrespondsToBox(2), "Expected False for piece 4, index 2")
        );
    }

    @Test
    public void testTwo() {
        assertAll(
                () -> assertFalse((new Piece(1, null)).indexCorrespondsToBox(1), "Expected False for piece 1, index 1"),
                () -> assertTrue((new Piece(3, null)).indexCorrespondsToBox(3), "Expected True for piece 3, index 3"),
                () -> assertTrue((new Piece(4, null)).indexCorrespondsToBox(1), "Expected True for piece 4, index 1"),
                () -> assertTrue((new Piece(4, null)).indexCorrespondsToBox(4), "Expected True for piece 4, index 4")
        );
    }
}
