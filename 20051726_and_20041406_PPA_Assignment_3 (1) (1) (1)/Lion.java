
/**
 * A simple model of a Lion.
 * Lions age, move, eat Herbivores, can reproduce and will eventually die.
 *
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */
public class Lion extends Predator
{
    // Characteristics shared by all lions (class variables).
    
    // The age at which a lion can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a lion can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a lion breeding.
    private static final double BREEDING_PROBABILITY = 0.8;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // Probability of the lion being a male
    private static final double MALE_PROBABILITY = 0.5;


    /**
     * Create a lion. A lion can be created as a new born (age zero
     * and not hungry) or with a random age and food level
     * 
     * @param randomAge If true, the lion will have random age and hunger level
     * @param field The field currently occupied
     * @param location The location within the field
     */
    public Lion(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer) {
        super(randomAge, randomFoodLevel, field, location, timer);
    }

    /**
     * Make a new young lion
     * @return An object of subtype of Animal,
     * representing a newly born lion
     */
    protected Lion makeNewYoung(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer)
    {
        return new Lion (randomAge, randomFoodLevel, field, location, timer);
    }

    /**
     * Returns the likelihood that the lion will breed
     * @return The likelihood that the lion will breed as a double
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
     * Returns the age at which the lion can breed
     * @return The age at which the lion can breed as an integer
     */
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Returns the maximum age of the lion
     * This is different for every animal
     * @returns An integer representing the maximum
     * possible age of the lion
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }


    /**
     * Returns the probability of the animal being a male
     * @return The probability of the animal being a male as a double
     */
    public double getMaleProbability(){return MALE_PROBABILITY;}

    /**
     * Checks whether the animal is awake or not
     * @return true if awake, false otherwise
     */
    protected boolean isAwake()
    {
        return getTimerObject().isDayTime();
    }
}

