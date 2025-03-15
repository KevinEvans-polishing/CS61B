package ngrams;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int key : ts.keySet()) {
            if (key < startYear || key > endYear) {
                continue;
            }
            this.put(key, ts.get(key));
        }
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        var keys = this.keySet();
        return new ArrayList<>(keys);
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        ArrayList<Double> values = new ArrayList<>();
        for (int key : this.keySet()) {
            values.add(this.get(key));
        }
        return values;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // 两个ts都不包含任何年份
        if (this.years().isEmpty() && ts.years().isEmpty()) {
            return new TimeSeries();
        }
        int minStartYear = getMinStartYear(ts);
        int maxEndYear = getMaxEndYear(ts);
        TimeSeries newTs = new TimeSeries();
        for (int i = minStartYear; i <= maxEndYear; i++) {
            if (this.containsKey(i) && ts.containsKey(i)) {
                newTs.put(i, this.get(i) + ts.get(i));
            } else if (this.containsKey(i) && !ts.containsKey(i)) {
                newTs.put(i, this.get(i));
            } else if (!this.containsKey(i) && ts.containsKey(i)) {
                newTs.put(i, ts.get(i));
            }
        }
        return newTs;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTs = new TimeSeries();
        for (int key : this.keySet()) {
            if (!ts.containsKey(key)) {
                throw new IllegalArgumentException();
            } else if (ts.get(key) != 0){
                newTs.put(key, this.get(key) / ts.get(key));
            }
        }
        return newTs;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.

    private int getMinStartYear(TimeSeries ts) {
        int thisYear = this.firstKey();
        int tsYear = ts.firstKey();
        if (thisYear - tsYear > 0) {
            return tsYear;
        } else {
            return thisYear;
        }
    }

    private int getMaxEndYear(TimeSeries ts) {
        int thisYear = this.lastKey();
        int tsYear = ts.lastKey();
        if (thisYear - tsYear > 0) {
            return thisYear;
        } else {
            return tsYear;
        }
    }
}
