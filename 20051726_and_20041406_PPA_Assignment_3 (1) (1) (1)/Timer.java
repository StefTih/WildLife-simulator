/**
 * A class to represent the time in the simulation.
 *
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */

public class Timer
{
    private int hour;
    private int day;

    /**
     * Constructor sets the hours and days to 0 initially
     */
    public Timer(){
        hour = 0;
        day = 0;
    }
    /**
     * Returns the current hour of the simulation
     * @return The current hour of the simulation as an integer
     */
    public int getHour()
    {
        return hour;
    }

    /**
     * Returns the current day of the simulation. Not currently in use,
     * but intentionally left for extendability purposes
     * @return The current day of the simulation as an integer
     */
    public int getDay()
    {
        return day;
    }

    /**
     * Checks whether it is daytime.
     * @return true if it is daytime, false if it is night time.
     */
    public boolean isDayTime()
    {
        return hour >= 5 && hour <= 20;
    }

    /**
     * Checks whether it is nighttime.
     * @return true if its night time, false if its day time.
     */
    public boolean isNightTime()
    {
        return hour <= 6 || hour >= 18;
    }

    /**
     * Advances the time by an hour.
     */
    public void incrementTime()
    {
        incrementHour();
        if (getHour() > 23)
        {
            incrementDay();
            resetHour();
        }
    }

    /**
     * Advances the day by one.
     */
    private void incrementDay()
    {
        day = day + 1;
    }

    /**
     * Advances the hour by one.
     */
    private void incrementHour()
    {
        hour = hour + 1;
    }

    /**
     * Sets the hours back to 0.
     */
    private void resetHour()
    {
        hour = 0;
    }
}
