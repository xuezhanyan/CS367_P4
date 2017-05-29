/////////////////////////////////////////////////////////////////////////////
// Semester: CS367 Spring 2017
// PROJECT: Program 4
// FILE: GenerateData.java, Interval.java, IntervalADT.java, IntervalNode.java,
//       IntervalNotFoundException.java, IntervalTree.java, 
//       IntervalTreeADT.java, IntervalTreeGUI.java, IntervalTreeMain.java
//
// TEAM: Team 57a import teamName
// Authors: Matthew Schmude, Xuezhan Yan
// Author1: Matthew Schmude, schmude@wisc.edu, 9074395576, Lec 002
// Author2: Xuezhan Yan, xyan56@wisc.edu, 9074973794, Lec 002
//
// ---------------- OTHER ASSISTANCE CREDITS
// Persons: Identify persons by name, relationship to you, and email.
// Describe in detail the the ideas and help they provided.
//
// Online sources: avoid web searches to solve your problems, but if you do
// search, be sure to include Web URLs and description of
// of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

public class Interval<T extends Comparable<T>> implements IntervalADT<T> {
    T start;
    T end;
    String label;

    public Interval(T start, T end, String label) {
    	this.start = start;
    	this.end = end;
    	this.label = label;
    }

    /** Returns the start value (must be Comparable<T>) of the interval. */
    @Override
    public T getStart() {
    	return start;
    }

    /** Returns the end value (must be Comparable<T>) of the interval. */
    @Override
    public T getEnd() {
    	return end;
    }

    /** Returns the label for the interval. */
    @Override
    public String getLabel() {
    	return label;
    }

	/**
	 * Return true if this interval overlaps with the other interval. 
	 * 
	 * <p>Note: two intervals [a, b], [c, d] will NOT overlap if either b &lt; c or d
	 * &lt; a. </p>
	 * 
	 * <p>In all other cases, they will overlap.</p>
	 * 
	 * @param other target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
    @Override
    public boolean overlaps(IntervalADT<T> other) {
    	if (other == null)
    		throw new IllegalArgumentException();
    	return !(this.end.compareTo(other.getStart()) < 0 || other.getEnd().compareTo(this.start) < 0);		
    }

	/**
	 * Returns true if given point lies inside the interval.
	 * 
	 * @param point
	 *            to search
	 * @return true if it contains the point
	 */
    @Override
    public boolean contains(T point) {
    	return this.start.compareTo(point) <= 0 && point.compareTo(this.end) <= 0;
    }

	/**
	 * Compares this interval with the other and return a negative value 
	 * if this interval comes before the "other" interval.  Intervals 
	 * are compared first on their start time.  The end time is only considered
	 * if the start time is the same.
	 * 
	 * <p>For example, if start times are different:</p>
	 * 
	 * <pre>
	 * [0,1] compared to [2,3]: returns -1 because 0 is before 2
	 * [2,3] compared to [0,1]: return 1 because 2 is after 0
	 * [0,4] compared to [2,3]: return -1 because 0 is before 2
	 * [2,3] compared to [0,4]: return 1 because 2 is after 0
	 * [0,3] compared to [2,4]: return -1 because 0 is before 2
	 * [2,4] compared to [0,3]: return 1 because 2 is after 0
	 * </pre>
	 * 
	 * <p>If start times are the same, compare based on end time:</p>
	 * <pre>
	 * [0,3] compared to [0,4]: return -1 because start is same and 3 is before 4
	 * [0,4] compared to [0,3]: return 1 because start is same and 4 is after 3</pre>
	 * 
	 * <p>If start times and end times are same, return 0</p>
	 * <pre>[0,4] compared to [0,4]: return 0</pre>
	 *
	 * @param other
	 *            the second interval to which compare this interval with
	 *            
	 * @return negative if this interval's comes before the other interval, 
	 * positive if this interval comes after the other interval,
	 * and 0 if the intervals are the same.  See above for details.
	 */
    @Override
    public int compareTo(IntervalADT<T> other) {
        if (other == null)
        	throw new IllegalArgumentException();
        if (this.start.compareTo(other.getStart()) == 0){
        	// if start times are same
        	return this.end.compareTo(other.getEnd());
        }
        else
        	// if start times are different
        	return this.start.compareTo(other.getStart());
    }

	@Override
	public String toString() {
		return this.label + " [" + this.start + ", " + this.end + "] ";
	}
}
