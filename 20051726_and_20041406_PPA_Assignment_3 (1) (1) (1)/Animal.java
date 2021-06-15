import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * A class representing shared characteristics of animals.
 * They have a foodLevel (hunger), and have an assigned sex for mating purposes.
 * 
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */
public abstract class Animal extends Organism
{
    // Class fields

    // The animal's food level, which is increased by eating prey or plants.
    private int foodLevel;
    // Whether or not the animal is a male
    private final boolean male;

    /**
     * Create a new Animal object. Animals can have a random age and food level/hunger.
     *
     * @param randomAge Whether the animal is created with a randomly-generated age, or not
     * @param randomFoodLevel Whether the animal is created with a randomly-generated hunger, or not
     * @param field A reference to field object of the simulation (The field in which the animal is in)
     * @param location The Location object representing the location of the organism within the field
     * @param timer A reference to the Timer object used by the simulation
     */
    public Animal(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer)
    {
        super(randomAge,field,location,timer);
        male = assignSex();

        // Maximum initial food level bound to 10 to prevent animals surviving for too long
        if(randomFoodLevel){
            foodLevel = getRandomizerObject().nextInt(10) + 1;
        }
        else{
            foodLevel = 10;
        }
    }


    // General functionality

    /**
     * Carries out all the functionalities that an animal can do during the simulation
     *
     * @param newAnimal The list of animals which are inside the simulation.
     */
    @Override
    protected void kingdomSpecificBehaviour(List<Organism> newAnimal){
        if (isAwake()){
            incrementHunger();
            if (isAlive()){
                reproduce(newAnimal);
                Location newLocation = findNewLocation();
                tryToMove(newLocation);
            }
        }
    }


    //Location Methods

    /**
     * Checks for a new location in which the animal can move into which might have a food source.
     *
     * @return A object of type Location which represents the location of the new field to move to.
     */
    private Location findNewLocation(){
        Location newLocation = findFood();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = getField().freeWalkableAdjacentLocation(getLocation());
        }
        return newLocation;
    }


    /**
     * Checks whether it can move to a new location and if it cannot, it will die.
     *
     * @param locationToTry Stores the location to test.
     */
    private void tryToMove(Location locationToTry){
        // See if it is possible to move.
        if (locationToTry != null){
            setLocation(locationToTry);
        }
        else {
            // Death by Overcrowding.
            setDead();
        }
    }


    // Food Methods

    /**
     * Look for food adjacent to the current location.
     * Only the first instance of food is eaten.
     *
     * @return An object of type Location representing where
     * food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object food = field.getObjectAt(where);
            if(hasEaten(where, food)){
                return where;
            }
        }

        return null;
    }

    /**
     * Determines if the animal can eat the object at the location provided
     *
     * @param placeOfFood A Location object representing where the food is
     *                    located in the field
     * @param food An object containing possible food for the animal
     * @return true if the animal has eaten the food, false otherwise
     */
    abstract protected boolean hasEaten(Location placeOfFood, Object food);


    // Hunger/Food-level methods

    /**
     * Make this animal more hungry.
     * This could result in the animal's death.
     */
    protected void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Returns the food level of the animal.
     * @return the food level of the animal.
     */
    protected int getFoodLevel()
    {
        return foodLevel;
    }

    /**
     * Sets the food level of the animal.
     * @param newFoodLevel is set for the animal.
     */
    protected void setFoodLevel(int newFoodLevel)
    {
        foodLevel = newFoodLevel;
    }



    // Reproduction methods

    /**
     * Add offspring of the organism to a list
     *
     * @param newOrganisms A list of type Organism to add new organisms too
     */
    private void reproduce(List<Organism> newOrganisms){
        List<Location> freeLocations = getField().getFreeWalkableAdjacentLocations(getLocation());
        int numberOfNewOrganisms = determineNumberOfOffspring();
        for(int i = 0; i < numberOfNewOrganisms && freeLocations.size() > 0; ++i){
            Location locationForOffspring = freeLocations.remove(0);
            Organism newOffspring = makeNewOffspring(false, getField(), locationForOffspring, getTimerObject());
            newOrganisms.add(newOffspring);
        }
    }

    /**
     * Determine the amount of offspring when the animal reproduces
     * @return The number of offspring produced
     */
    @Override
    protected int determineNumberOfOffspring() {
        int births = 0;
        if(!male && meetMale()) {
            if(isOfBreedingAge() && getRandomizerObject().nextDouble() <= getBreedingProbability()) {
                births = getRandomizerObject().nextInt(getMaxLitterSize()) + 1;
            }
        }
        return births;
    }

    /**
     * Obtain an offspring of a subtype of Animal - calls another method with the same functionality
     * to allow for an extra parameter specific to Animals
     *
     * @param randomAge Whether or not the organism will be instantiated with a randomly-generated age.
     * @param field The field in which the organism is in (A reference to field object of the simulation)
     * @param location The Location object representing the location of the organism within the field
     * @param timer An object reference to the timer object used in the simulation
     * @return An object of a subtype of Animal
     */
    protected Animal makeNewOffspring(boolean randomAge, Field field, Location location, Timer timer){
        return makeNewYoung(false, false, field, location, timer);
    }

    /**
     * Obtain an offspring of a subtype of Animal.
     * Animals can have a random age and random hunger
     *
     * @param randomAge Whether or not the organism will be instantiated with a randomly-generated age.
     * @param randomFoodLevel Whether or not the organism will be instantiated with a randomly-generated hunger.
     * @param field The field in which the organism is in (A reference to field object of the simulation)
     * @param location The Location object representing the location of the organism within the field
     * @param timer An object reference to the timer object used in the simulation
     * @return An object of a subtype of Animal
     */
    abstract protected Animal makeNewYoung(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer);

    /**
     * Checks to see if neighboring spaces have a member of the opposite sex of the same
     *
     * @return true if there is a member of the opposite sex in a neighboring cell, false otherwise
     */
    private boolean meetMale()
    {
        Field currentField = getField();
        List<Location> neighboringLocations = getNeighboringPlaces(currentField);

        // Checks type then sex of animals in neighboring locations
        for(Location place:neighboringLocations){
            Object possibleMate = currentField.getObjectAt(place);
            if (checkSameTypeOfAnimal(possibleMate)){
                if (((Animal) possibleMate).isMale()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Obtains the neighboring animals in the field around the animal
     *
     * @return A list of locations with animal objects adjacent to the animal
     */
    private List<Location> getNeighboringPlaces(Field currentField){
        List<Location> neighboringLocations = currentField.adjacentLocations(getLocation());
        return removeNullLocations(currentField, neighboringLocations);
    }

    /**
     * Removes null values from a list of locations
     *
     * @param currentField The Field object representing the field in which
     *                     the Animal is currently in
     * @param locations A list of locations, possibly storing no objects or null.
     * @return A list of locations, with locations with no object/null value removed.
     */
    private List<Location> removeNullLocations(Field currentField, List<Location> locations){
        Iterator<Location> iter = locations.iterator();
        while(iter.hasNext()) {
            Location currentLocation = iter.next();
            if (currentField.getObjectAt(currentLocation) == null) {
                iter.remove();
            }
        }
        return locations;
    }

    /**
     * Checks an object to see if it is the same type as the current object executing this code.
     *
     * @param objectToTest Object who's type is to be tested
     * @return true if the object is of the same type, false otherwise
     */
    private boolean checkSameTypeOfAnimal(Object objectToTest){
        Class classOfNeighbor = objectToTest.getClass();
        return classOfNeighbor.equals(this.getClass());

    }

    /**
     * An animal can breed if it has reached the breeding age.
     *
     * @return true if the animal can breed, false otherwise.
     */
    private boolean isOfBreedingAge()
    {
        return getAge() >= getBreedingAge();
    }

    /**
     * Returns the likelyhood that the animal will breed
     *
     * @return The likelyhood that the animal will breed as a double
     */
    abstract protected double getBreedingProbability();

    /**
     * Returns the maximum number of births
     *
     * @return The maximum number of births as an integer
     */
    abstract protected int getMaxLitterSize();

    /**
     * Returns the age at which the animal can breed
     *
     * @return The age at which the animal can breed as an integer
     */
    abstract protected int getBreedingAge();

    /**
     * Returns the probability of the animal being a male
     *
     * @return The probability of the animal being a male as a double
     */
    abstract public double getMaleProbability();

    /**
     * Randomly assigns and returns the sex of the animal dependent on the probability set
     * @return true if the animal is male, false if the animal is female
     */
    private boolean assignSex()
    {
        return getRandomizerObject().nextDouble() < getMaleProbability();
    }

    /**
     * Determines whether or not the animal is a male or not
     *
     * @return true if the animal is male, false otherwise
     */
    private boolean isMale()
    {
        return male;
    }


}

