package comp1110.ass1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformTest {

    @Test
    void add() {
        int x=2;
        int y=1;
        Assertions.assertEquals(3,Transform.add(x,y),"Expected 3 for 2+1");
    }

    @Test
    void testAdd() {
        int a = -5;
        int b = 9;
        Assertions.assertEquals(4,Transform.add(a,b),"Expected 4 for -5+9");
    }
}