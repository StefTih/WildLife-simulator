/**
 * A simple model of a zebra.
 * Each individual animal type has a set of changeable parameters
 *
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */

public class Zebra extends Herbivore{

    // Characteristics shared by all zebras

    // The age at which an zebra can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which an zebra can live.
    private static final int MAX_AGE = 40;
    // The likelihood of an zebra breeding.
    private static final double BREEDING_PROBABILITY = 0.48;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // Food value given to an animal-eater by eating this zebra
    // Essentially the number of steps that the animal-eater can take before needing to eat again
    private static final int FOOD_VALUE = 20;
    // Probability of the zebra being a male
    private static final double MALE_PROBABILITY = 0.5;

    // Individual characteristics (instance fields).

    /**
     * Create a new zebra
     *
     * @param randomAge Whether the animal is created with a randomly-generated age, or not
     * @param randomFoodLevel Whether the animal is created with a randomly-generated hunger, or not
     * @param field A reference to field object of the simulation (The field in which the animal is in)
     * @param location The Location object representing the location of the organism within the field
     * @param timer A reference to the Timer object used by the simulation
     */
    public Zebra(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer) {
        super(randomAge, randomFoodLevel, field, location, timer);
    }


    /**
     * Make a new young zebra
     *
     * @return An Zebra object, representing a newly born zebra
     */
    protected Zebra makeNewYoung(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer)
    {
        return new Zebra(randomAge, randomFoodLevel, field, location, timer);
    }

    /**
     * Returns the likelihood that the zebra will breed
     *
     * @return The likelihood that the zebra will breed as a double
     */
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Returns the maximum number of births
     *
     * @return The maximum number of births as an integer
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * Returns the age at which the zebra can breed
     *
     * @return The age at which the zebra can breed as an integer
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Returns the maximum age of the zebra
     *
     * @returns An integer representing the maximum possible age of the zebra.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Returns the nutritional value of the zebra
     *
     * @return The nutritional value of the zebra (number of steps)
     */
    protected int getFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Returns the probability of the zebra being a male
     *
     * @return The probability of the zebra being a male as a double
     */
    public double getMaleProbability(){return MALE_PROBABILITY;}

    /**
     * Checks whether the zebra is awake or not.
     *
     * @return true if awake, false otherwise.
     */
    protected boolean isAwake()
    {
        return getTimerObject().isDayTime();
    }

}


