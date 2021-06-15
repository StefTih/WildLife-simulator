public class Grass extends Plant{
    // The maximum number of plants that will grow around the current one
    private static final int MAX_SEEDS = 8;
    // The maximum age of the plant - it dies once past this age
    private static final int MAX_AGE = 8;
    // The current nutritional value the grass has
    private static final int FOOD_VALUE = 22;
    // The probability of a new plant growing
    private static final double GROWTH_PROBABILITY = 0.15;

    public Grass (boolean randomAge, Field field, Location location, Timer timer) {
        super(randomAge, field, location, timer);
    }
    /**
     * Creates a new grass object - used for growing new grass
     * @param randomAge Whether or not the grass will be instantiated with a randomly-generated age or not
     * @param field The field object in which the grass is located
     * @param location The location of the grass within the field
     * @param timer The timer object used in the simulation
     * @return A new object of type Grass
     */
    protected Grass makeNewOffspring(boolean randomAge, Field field, Location location, Timer timer)
    {
        return new Grass(randomAge, field, location, timer);
    }

    /**
     * Returns the nutritional value that the plant has
     * (essentially the number of steps that an animal eating this plant can take)
     */
    protected int getFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Returns the maximum age grass can live.
     * @return int the age number.
     */
    protected int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * Returns the probability of grass growing.
     * @return A double containing the growth probability.
     */
    protected double getGrowthProbability(){
        return GROWTH_PROBABILITY;
    }

    /**
     * Checks whether the animal is awake or not.
     * @return true if awake, false otherwise.
     */
    @Override
    protected boolean isAwake() {
        return getTimerObject().isDayTime();
    }

     @Override protected int getMaxSeeds(){ return MAX_SEEDS;}
}
