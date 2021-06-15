
/**
 * An abstract class representing shared characteristics of a predator, which eats other animals.
 *
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */
public abstract class Predator extends Animal
{
    /**
     * Constructor method
     * @param randomAge Whether or not the age should be randomised.
     * @param randomFoodLevel Whether or not the food level should be randomised.
     * @param field A reference to field object of the simulation (The field in which the organism is in).
     * @param location A reference to the location object of the simulation (The coordinates of the animal in the field).
     * @param timer A refrence to the timer object of the simulation (The time of day).
     */
    public Predator(boolean randomAge, boolean randomFoodLevel, Field field, Location location, Timer timer)
    {
        super(randomAge, randomFoodLevel, field, location, timer);
    }

    /**
     * Checks whether the predator has eaten any animals by moving to another location.
     * @param placeOfFood Location object representing where the organism is located.
     * @param food The organism object in the set location.
     * @return true if the predator has eaten, false otherwise.
     */
    protected boolean hasEaten(Location placeOfFood, Object food) {
        if (food instanceof Herbivore) {
            Herbivore prey = (Herbivore) food;
            if (prey.isAlive()) {
                prey.setDead();
                setFoodLevel(prey.getFoodValue());
                return true;
            }
        }
        return false;
    }
}
