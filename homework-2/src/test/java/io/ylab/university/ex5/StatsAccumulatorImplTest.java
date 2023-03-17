package io.ylab.university.ex5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatsAccumulatorImplTest {
    private StatsAccumulatorImpl accumulator;

    @BeforeEach
    void setUp() {
        accumulator = new StatsAccumulatorImpl();
    }

    @Test
    void testEmptyAccumulator() {
        assertEquals(0, accumulator.getCount());
        assertEquals(0, accumulator.getAvg());
        assertEquals(0, accumulator.getMin());
        assertEquals(0, accumulator.getMax());
    }

    @Test
    void testAccumulatorWithOneValue() {
        accumulator.add(1);
        assertEquals(1, accumulator.getCount());
        assertEquals(1, accumulator.getAvg());
        assertEquals(1, accumulator.getMin());
        assertEquals(1, accumulator.getMax());
    }

    @Test
    void testAccumulatorWithMultipleValues() {
        accumulator.add(1);
        accumulator.add(2);
        accumulator.add(0);
        accumulator.add(3);
        accumulator.add(8);

        assertEquals(5, accumulator.getCount());
        assertEquals(2.8, accumulator.getAvg(), 0.01);
        assertEquals(0, accumulator.getMin());
        assertEquals(8, accumulator.getMax());
    }
}
