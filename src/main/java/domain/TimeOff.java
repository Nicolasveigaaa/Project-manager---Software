// Jacob Knudsen (s224372)

package domain;

// assumes timeof is an unbroken interval
public class TimeOff {
    private int startWeek;
    private int startYear;
    private int endWeek;
    private int endYear;
    // potentially add a user reference if needed, but maybe not necessary

    public TimeOff(int startWeek, int startYear, int endWeek, int endYear) {
        this.startWeek = startWeek;
        this.startYear =startYear;
        this.endWeek = endWeek;
        this.endYear = endYear;
    }


    public boolean overlaps(int week, int year) {
        // see if the given week/year falls within this time off period
        if (startYear == endYear) { 
            return week >=startWeek && week <= endWeek; // check for one year
        }
        // check for multiple years
        if (year < startYear || year > endYear) return false; // strictly outside year range
        if (year > startYear && year < endYear ) return true; // strictly inside year range
        if (year == startYear) { 
           return week >= startWeek; // year and startyear overlaps
        }
        if ( year == endYear) {  
            return week <= endWeek; // year and endyear overlaps
        }
        return false;
    }
}