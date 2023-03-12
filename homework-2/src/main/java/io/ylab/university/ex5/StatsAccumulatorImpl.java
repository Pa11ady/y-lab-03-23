package io.ylab.university.ex5;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int minValue = Integer.MAX_VALUE;
    //Если взять начальное значение 0, то будут проблемы с отрицательными числами
    private int maxValue = Integer.MIN_VALUE;
    private int count = 0;
    private int sum = 0;

    @Override
    public void add(int value) {
        minValue = Math.min(minValue, value);
        maxValue = Math.max(maxValue, value);
        count++;
        sum += value;
    }

    @Override
    public int getMin() {
        if (minValue == Integer.MAX_VALUE) {
            return 0;
        }
        return minValue;
    }

    @Override
    public int getMax() {
        //некрасиво возвращать MIN_VALUE
        if (maxValue == Integer.MIN_VALUE) {
            return 0;
        }
        return maxValue;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count == 0) {
            return 0.0;
        }
        return (double)sum/count;
    }
}
