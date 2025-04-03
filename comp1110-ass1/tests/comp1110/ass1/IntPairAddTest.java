package comp1110.ass1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
class IntPairAddTest {
    private static IntPair nPos(int x, int y) {
        return new IntPair(x, y);
    }

    private static void test(
            IntPair expect,
            IntPair pos1,
            IntPair pos2) {
        Assertions.assertEquals(
                expect,
                pos1.add(pos2),
                "calling pos1.add(pos2) where pos1 = "
                        + pos1
                        + " and pos2 = "
                        + pos2
        );
    }
    @Test
    void example() {
        test(nPos(-2, 8), nPos(2, 3), nPos(-4, 5));
    }

    @Test
    void positive() {
        test(nPos(1, 35), nPos(1, 35), nPos(0, 0));
        test(nPos(1, 35), nPos(0, 0), nPos(1, 35));
        test(nPos(21, 16), nPos(20, 15), nPos(1, 1));
        test(nPos(21, 16), nPos(1, 1), nPos(20, 15));
    }

    @Test
    void mixedSign() {
        test(nPos(-8, 65), nPos(-5, 35), nPos(-3, 30));
        test(nPos(-2, -104), nPos(-5, -5), nPos(3, -99));
    }

    @Test
    void withSelf() {
        IntPair pos1 = new IntPair(10, 35);
        test(nPos(20, 70), pos1, pos1);
    }

    @Test
    void newObject() {
        test(nPos(1, 2), nPos(1, 2), nPos(0, 0)); // Guarding default
        IntPair pos1 = new IntPair(10, 35);
        IntPair pos2 = new IntPair(1, 1);
        Assertions.assertNotSame(pos1, pos1.add(pos2));
        Assertions.assertNotSame(pos2, pos1.add(pos2));
        Assertions.assertNotSame(pos1, pos2.add(pos1));
        Assertions.assertNotSame(pos2, pos2.add(pos1));
        // With self
        Assertions.assertNotSame(pos1, pos1.add(pos1));
        Assertions.assertNotSame(pos2, pos2.add(pos2));
    }
}

