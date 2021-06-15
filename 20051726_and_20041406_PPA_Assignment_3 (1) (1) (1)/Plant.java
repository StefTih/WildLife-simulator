import java.util.List;

/**
 * A representation of a plant. They do not move,
 * they grow at a fixed probability on their own, and
 * provide food for the herbivores in the simulation.
 *
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */

abstract public class Plant extends Organism {

    /**
     * Constructor method
     *
     * @param field    The Field object of the simulation
     * @param location The location of the plant within the field
     */
    public Plant(boolean randomAge, Field field, Location location, Timer timer) {
        super(randomAge, field, location, timer);
    }

    /**
     * This is what the plant does most of the time - it grows.
     * After every step, the plant can reproduce by spreading seeds
     * to locations around it, which will grow into plants.
     *
     * @param newPlants A list to return newly born animals of subtype Herbivores.
     */
    protected void kingdomSpecificBehaviour(List<Organism> newPlants){
            reproduce(newPlants);
    }

    /**
     * Add offspring of the organism to a list
     *
     * @param newOrganisms A list of type Organism to add new plants to
     */
    protected void reproduce(List<Organism> newOrganisms){
        // Gets free locations & number of new plants to be placed
        List<Location> freeLocations = getField().getFreeAdjacentLocations(getLocation());
        int numberOfNewOrganisms = determineNumberOfOffspring();

        // Adds a new plant to the list
        for(int i = 0; i < numberOfNewOrganisms && freeLocations.size() > 0; ++i){
            Location locationForOffspring = freeLocations.remove(0);
            if(getField().getObjectAt(locationForOffspring) == null){
                Organism newOffspring = makeNewOffspring(false, getField(), locationForOffspring, getTimerObject());
                newOrganisms.add(newOffspring);
            }
        }
    }


    // Reproduction Methods

    /**
     * Calculate the number of plants to grow around the existing plant
     *
     * @return An integer with the number of plants that will grow around
     * the existing plant (may be zero)
     */
    protected int determineNumberOfOffspring()
    {
        int newPlants = 0;
        if(getRandomizerObject().nextDouble() <= getGrowthProbability()){
            newPlants = getRandomizerObject().nextInt(getMaxSeeds())+1;
        }
        return newPlants;
    }

    /**
     * Returns the probability of grass growing.
     *
     * @return A double precision floating point value
     * containing the probability that the plant will
     * grow new seeds around it.
     */
    abstract protected double getGrowthProbability();

    /**
     * Obtains the nutritional value of the plant,
     * essentially the number of steps that a plant-eater
     * can survive before needing to eat again
     *
     * @return An integer containing the food/nutritional value of the plant
     */
     abstract protected int getFoodValue();

    /**
     * Obtains the maximum number of seeds that the plant can spread
     *
     * @return An integer representing the maximum number of seeds
     * that the plant can produce
     */
     abstract protected int getMaxSeeds();
}
