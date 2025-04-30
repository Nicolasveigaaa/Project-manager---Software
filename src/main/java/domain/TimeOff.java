package domain;

public class TimeOff {
    private int startWeek;
    private int startYear;
    private int endWeek;
    private int endYear;
    // potentially add a User reference if needed, but maybe not necessary

    public TimeOff(int startWeek, int startYear, int endWeek, int endYear) {
        this.startWeek = startWeek;
        this.startYear = startYear;
        this.endWeek = endWeek;
        this.endYear = endYear;
    }


    public boolean overlaps(int week, int year) {
         // Logic to check if the given week/year falls within this time off period
         if (year < startYear || year > endYear) return false;
         if (year > startYear && year < endYear) return true; // Spans multiple years
         if (startYear == endYear) { // Single year
             return week >= startWeek && week <= endWeek;
         }
         if (year == startYear) { // First year of multi-year span
             return week >= startWeek;
         }
         if (year == endYear) { // Last year of multi-year span
             return week <= endWeek;
         }
         return false; // Should not happen if initial checks pass
    }
}