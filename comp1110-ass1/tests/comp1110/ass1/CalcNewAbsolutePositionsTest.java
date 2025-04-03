package comp1110.ass1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@Timeout(value = 1000000000, unit = TimeUnit.MILLISECONDS)
public class CalcNewAbsolutePositionsTest {

    @Test
    public void compoundTransformations() {
        int i = 0;
        IntPair[] position = startingPosition;
        for (Transform rotation : rotations) {
            IntPair[] oldPosition = position;
            position = Piece.calcNewAbsolutePositions(position, rotation);
            assertArrayEquals(expected[i++], position,
                "Expected " + Arrays.toString(expected[i-1])
                        + " for rotation " + rotation
                        + " after position " + Arrays.toString(oldPosition)
                        + " but got " + Arrays.toString(position));
            for (Transform translation : translations) {
                oldPosition = position;
                position = Piece.calcNewAbsolutePositions(position, translation);
                assertArrayEquals(expected[i++], position, "Expected " + Arrays.toString(expected[i-1])
                        + " for translation " + translation
                        + " after position " + Arrays.toString(oldPosition)
                        + " but got " + Arrays.toString(position));
            }
        }
    }

    @Test
    public void simpleTransformations() {
        System.out.println("rotations");
        int i = 0;
        for(Transform rotation : rotations){
            assertArrayEquals(Piece.calcNewAbsolutePositions(startingPosition, rotation), simpleExpected[i++],
                    "Expected " + Arrays.toString(expected[i-1])
                            + " for rotation " + rotation
                            + " but got " + Arrays.toString(Piece.calcNewAbsolutePositions(startingPosition, rotation)));
        }
        for(Transform translation: translations){
            assertArrayEquals(Piece.calcNewAbsolutePositions(startingPosition,translation), simpleExpected[i++],
                    "Expected " + Arrays.toString(expected[i-1])
                            + " for translation " + translation
                            + " but got " + Arrays.toString(Piece.calcNewAbsolutePositions(startingPosition, translation)));
        }
    }

    private static final Transform[] rotations = new Transform[]{
            new Transform(new IntPair(0,0),Angle.DEG_0),
            new Transform(new IntPair(0,0),Angle.DEG_90),
            new Transform(new IntPair(0,0),Angle.DEG_180),
            new Transform(new IntPair(0,0),Angle.DEG_270),
    };

    private static final Transform[] translations = new Transform[]{
            new Transform(new IntPair(1,0),Angle.DEG_0),
            new Transform(new IntPair(0,1),Angle.DEG_0),
            new Transform(new IntPair(-1,0),Angle.DEG_0),
            new Transform(new IntPair(0,-1),Angle.DEG_0),
            new Transform(new IntPair(1,1),Angle.DEG_0)
    };

    private static final IntPair[] startingPosition = new IntPair[]{
            new IntPair(1, -1),
            new IntPair(0, 0),
            new IntPair(1, 0),
            new IntPair(1,1),
            new IntPair(2,1),
    };

    private static final IntPair[][] simpleExpected = new IntPair[][] {
            new IntPair[]{new IntPair(1, -1), new IntPair(0, 0), new IntPair(1, 0), new IntPair(1, 1), new IntPair(2, 1)},
            new IntPair[]{new IntPair(1, 1), new IntPair(0, 0), new IntPair(0, 1), new IntPair(-1, 1), new IntPair(-1, 2)},
            new IntPair[]{new IntPair(-1, 1), new IntPair(0, 0), new IntPair(-1, 0), new IntPair(-1, -1), new IntPair(-2, -1)},
            new IntPair[]{new IntPair(-1, -1), new IntPair(0, 0), new IntPair(0, -1), new IntPair(1, -1), new IntPair(1, -2)},
            new IntPair[]{new IntPair(2, -1), new IntPair(1, 0), new IntPair(2, 0), new IntPair(2, 1), new IntPair(3, 1)},
            new IntPair[]{new IntPair(1, 0), new IntPair(0, 1), new IntPair(1, 1), new IntPair(1, 2), new IntPair(2, 2)},
            new IntPair[]{new IntPair(0, -1), new IntPair(-1, 0), new IntPair(0, 0), new IntPair(0, 1), new IntPair(1, 1)},
            new IntPair[]{new IntPair(1, -2), new IntPair(0, -1), new IntPair(1, -1), new IntPair(1, 0), new IntPair(2, 0)},
            new IntPair[]{new IntPair(2, 0), new IntPair(1, 1), new IntPair(2, 1), new IntPair(2, 2), new IntPair(3, 2)}
    };

    private static final IntPair[][] expected = new IntPair[][] {
            new IntPair[]{new IntPair(1, -1), new IntPair(0, 0), new IntPair(1, 0), new IntPair(1, 1), new IntPair(2, 1)},
            new IntPair[]{new IntPair(2, -1), new IntPair(1, 0), new IntPair(2, 0), new IntPair(2, 1), new IntPair(3, 1)},
            new IntPair[]{new IntPair(2, 0), new IntPair(1, 1), new IntPair(2, 1), new IntPair(2, 2), new IntPair(3, 2)},
            new IntPair[]{new IntPair(1, 0), new IntPair(0, 1), new IntPair(1, 1), new IntPair(1, 2), new IntPair(2, 2)},
            new IntPair[]{new IntPair(1, -1), new IntPair(0, 0), new IntPair(1, 0), new IntPair(1, 1), new IntPair(2, 1)},
            new IntPair[]{new IntPair(2, 0), new IntPair(1, 1), new IntPair(2, 1), new IntPair(2, 2), new IntPair(3, 2)},
            new IntPair[]{new IntPair(0, 2), new IntPair(-1, 1), new IntPair(-1, 2), new IntPair(-2, 2), new IntPair(-2, 3)},
            new IntPair[]{new IntPair(1, 2), new IntPair(0, 1), new IntPair(0, 2), new IntPair(-1, 2), new IntPair(-1, 3)},
            new IntPair[]{new IntPair(1, 3), new IntPair(0, 2), new IntPair(0, 3), new IntPair(-1, 3), new IntPair(-1, 4)},
            new IntPair[]{new IntPair(0, 3), new IntPair(-1, 2), new IntPair(-1, 3), new IntPair(-2, 3), new IntPair(-2, 4)},
            new IntPair[]{new IntPair(0, 2), new IntPair(-1, 1), new IntPair(-1, 2), new IntPair(-2, 2), new IntPair(-2, 3)},
            new IntPair[]{new IntPair(1, 3), new IntPair(0, 2), new IntPair(0, 3), new IntPair(-1, 3), new IntPair(-1, 4)},
            new IntPair[]{new IntPair(-1, -3), new IntPair(0, -2), new IntPair(0, -3), new IntPair(1, -3), new IntPair(1, -4)},
            new IntPair[]{new IntPair(0, -3), new IntPair(1, -2), new IntPair(1, -3), new IntPair(2, -3), new IntPair(2, -4)},
            new IntPair[]{new IntPair(0, -2), new IntPair(1, -1), new IntPair(1, -2), new IntPair(2, -2), new IntPair(2, -3)},
            new IntPair[]{new IntPair(-1, -2), new IntPair(0, -1), new IntPair(0, -2), new IntPair(1, -2), new IntPair(1, -3)},
            new IntPair[]{new IntPair(-1, -3), new IntPair(0, -2), new IntPair(0, -3), new IntPair(1, -3), new IntPair(1, -4)},
            new IntPair[]{new IntPair(0, -2), new IntPair(1, -1), new IntPair(1, -2), new IntPair(2, -2), new IntPair(2, -3)},
            new IntPair[]{new IntPair(-2, 0), new IntPair(-1, -1), new IntPair(-2, -1), new IntPair(-2, -2), new IntPair(-3, -2)},
            new IntPair[]{new IntPair(-1, 0), new IntPair(0, -1), new IntPair(-1, -1), new IntPair(-1, -2), new IntPair(-2, -2)},
            new IntPair[]{new IntPair(-1, 1), new IntPair(0, 0), new IntPair(-1, 0), new IntPair(-1, -1), new IntPair(-2, -1)},
            new IntPair[]{new IntPair(-2, 1), new IntPair(-1, 0), new IntPair(-2, 0), new IntPair(-2, -1), new IntPair(-3, -1)},
            new IntPair[]{new IntPair(-2, 0), new IntPair(-1, -1), new IntPair(-2, -1), new IntPair(-2, -2), new IntPair(-3, -2)},
            new IntPair[]{new IntPair(-1, 1), new IntPair(0, 0), new IntPair(-1, 0), new IntPair(-1, -1), new IntPair(-2, -1)}
    };

}
