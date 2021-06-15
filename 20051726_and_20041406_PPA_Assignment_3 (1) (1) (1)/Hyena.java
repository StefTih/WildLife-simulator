
/**
 * A simple model of a Hyena.
 * Hyenas age, move, eat Herbivores and eventually die.
 *
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */
public class Hyena extends Predator {
    // Characteristics shared by all hyenas (class variables).

    // The age at which a hyena can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a hyena can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a hyena breeding.
    private static final double BREEDING_PROBABILITY = 0.8;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // Probability of the hyena being a male
    private static final double MALE_PROBABILITY = 0.5;

    // Individual characteristics (instance fields).


    /**
     * Create a hyena. A hyena can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the hyena will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Hyena(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer)
    {
        super(randomAge, randomFoodLevel, field, location, timer);
    }

    /**
     * Make a new young hyena
     * @return An object of subtype of Animal,
     * representing a newly born hyena
     */
    protected Hyena makeNewYoung(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer)
    {
        return new Hyena (randomAge, randomFoodLevel, field, location, timer);
    }


    /**
     * Returns the likelihood that the hyena will breed
     * @return The likelihood that the hyena will breed as a double
     */
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Returns the maximum number of births
     * @return The maximum number of births as an integer
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * Returns the age at which the hyena can breed
     * @return The age at which the hyena can breed as an integer
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Returns the maximum age of the hyena
     * This is different for every animal
     * @returns An integer representing the maximum
     * possible age of the hyena.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Returns the probability of the hyena being a male
     * @return The probability of the hyena being a male as a double
     */
    public double getMaleProbability(){return MALE_PROBABILITY;}

    /**
     * Checks whether the hyena is awake or not.
     * @return true if awake, false otherwise.
     */
    protected boolean isAwake()
    {
        return getTimerObject().isNightTime();
    }
}

