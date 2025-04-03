package comp1110.ass1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class WithinBoardTest {


    private static void test(IntPair position, boolean expected ) {
        Assertions.assertEquals((new CatsAndBoxes(1)).withinBoard(position), expected, "Expected " + expected + " for position " + position);
    }

    // simple and advanced tests may not correspond to difficulty of implementation

    @Test
    public void testSimple() {
        assertAll(
                ()->test(new IntPair(-3,-2), false),
                ()->test(new IntPair(6,9), false),
                ()->test(new IntPair(5,6), false),
                ()->test(new IntPair(-1,-1), false),
                ()->test(new IntPair(3, 1), true),
                ()->test(new IntPair(3, 3), true),
                ()->test(new IntPair(3, 4), true),
                ()->test(new IntPair(4, 3), true),
                ()->test(new IntPair(3, 4), true)
        );
    }

    @Test
    public void testAdvanced() {
        assertAll(
                ()->test(new IntPair(0, 0), true),
                ()->test(new IntPair(0, 1), true),
                ()->test(new IntPair(0, 2), true),
                ()->test(new IntPair(0, 3), true),
                ()->test(new IntPair(5,5), false),
                ()->test(new IntPair(-1,0), false),
                ()->test(new IntPair(0,-1), false),
                ()->test(new IntPair(5,0), false),
                ()->test(new IntPair(0,5), false),
                ()->test(new IntPair(5,4), false),
                ()->test(new IntPair(-1,5), false),
                ()->test(new IntPair(5,-1), false)
        );
    }
}
