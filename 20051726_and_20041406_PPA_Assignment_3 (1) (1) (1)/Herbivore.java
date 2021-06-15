/**
 * A class representing the functionality unique to herbivores.
 * They eat plants, unlike predators.
 *
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */
abstract public class Herbivore extends Animal{

    /**
     * Constructor class
     *
     * @param randomAge Whether the animal is created with a randomly-generated age, or not
     * @param randomFoodLevel Whether the animal is created with a randomly-generated hunger, or not
     * @param field A reference to field object of the simulation (The field in which the animal is in)
     * @param location The Location object representing the location of the organism within the field
     * @param timer A reference to the Timer object used by the simulation
     */
    public Herbivore(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer){
        super(randomAge, randomFoodLevel, field, location, timer);
    }

    /**
     * Checks whether the herbivore can eat a plants at a location before moving to that location.
     *
     * @param placeOfFood Location object representing where the organism is located.
     * @param food The organism object in the set location.
     * @return true if the herbivore has eaten, false otherwise.
     */
    protected boolean hasEaten(Location placeOfFood, Object food) {
        if (food instanceof Plant){
            Plant plant = (Plant) food;
            if (plant.isAlive()){
                plant.setDead();
                setFoodLevel(plant.getFoodValue());
                return true;
            }

        }
        return false;
    }

    /**
     * Returns the nutritional value of the animal
     *
     * @return The number of steps that an animal-eater
     * can move without needing to eat again as an integer
     */
    abstract protected int getFoodValue();
}

