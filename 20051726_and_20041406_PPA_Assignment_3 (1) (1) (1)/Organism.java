import java.util.List;
import java.util.Random;

/**
 * A high-level representation of shared characteristics of all organism in the simulation.
 * They exist in the field, they age, can be awake or not and reproduce.
 *
 * Features code heavily influenced by the one created by David J. Barnes
 * and Michael Kölling in the Animal, Fox and Rabbit classes.
 *
 * @author Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */
abstract public class Organism {

    // The location of the organism.
    private Location location;
    // The field in which the organism is located
    private Field field;
    // Object reference to the timer used in the simulator
    private final Timer timer;
    // A value to represent whether or not the organism is still alive or not
    private boolean alive;
    // The age of the organism
    private int age;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor method - initalises variables common amongst all organisms in the simulation
     *
     * @param randomAge Whether or not the organism will be instantiated with a randomly-generated age
     * @param location The Location object representing the location of the organism within the field
     * @param field A reference to field object of the simulation (The field in which the organism is in)
     * @param timer A reference to the Timer object used by the simulation
     */
    public Organism(boolean randomAge, Field field, Location location, Timer timer)
    {
        this.location = location;
        this.field = field;
        this.timer = timer;
        alive = true;
        setAge(0);
        if (randomAge){
            setAge(rand.nextInt(getMaxAge()));
        }
        setLocation(location);
    }


    // General Behaviour Methods

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     *
     * @param newOrganisms A list to receive newly created organisms.
     */
    public void act(List<Organism> newOrganisms)
    {
        incrementAge();
        if (isAlive()){
            kingdomSpecificBehaviour(newOrganisms);
        }
    }

    /**
     * Executes behaviour specific to a kingdom of animals (Plants, Animals, etc.)
     *
     * @param newOrganisms A list to receive newly created organisms.
     */
    abstract protected void kingdomSpecificBehaviour(List<Organism> newOrganisms);


    // Alive/Dead Methods

    /**
     * Returns a check whether the organism is alive or not
     *
     * @return true if the organism is alive, false if not
     */
    public boolean isAlive(){return alive;}

    /**
     * Indicate that the plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }


    // Reproduction Methods

    /**
     * Obtain a new object of a subtype of Organism - used to create a new object of an offspring
     *
     * @param randomAge Whether or not the organism will be instantiated with a randomly-generated age.
     * @param field The field in which the organism is in (A reference to field object of the simulation)
     * @param timer An object reference to the timer object used in the simulation
     * @return An object of a subtype of Organism
     */
    abstract protected Organism makeNewOffspring(boolean randomAge, Field field, Location location, Timer timer);

    /**
     * Determine the amount of offspring created when the animal reproduces
     * @return An integer containing the number of offspring
     */
    protected abstract int determineNumberOfOffspring();


    //Age methods

    /**
     * Increment the age of the animal by one when a new day starts (at midnight).
     * The animal will die if its age goes above the maximum age.
     */
    protected void incrementAge(){
        if (timer.getHour() == 0)
        {
            age = ++age;
            if (getAge() > getMaxAge())
            {
                setDead();
            }
        }
    }

    /**
     * Obtain the current age of the organism
     *
     * @return The age of the organism as an integer
     */
    protected int getAge(){return age;}

    /**
     * Set a new age for the organism
     * If the age is over the maximum age permissible,
     * or is below 0, the organism dies.
     *
     * @param newAge An integer for the organism's new age
     */
    protected void setAge(int newAge){
        if (0 > newAge || newAge > getMaxAge()) {
            setDead();
        }
        else{
            age = newAge;
        }
    }

    /**
     * Obtain the maximum age possible for the organism
     *
     * @return The maximum age that the animal can have, as an integer
     */
    abstract protected int getMaxAge();


    // Field and Location Methods

    /**
     * Obtain the organism's location.
     *
     * @return The Location object representing the organism's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Obtain the organism's field.
     *
     * @return The organism's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Place the organism at a new location in the current field.
     *
     * @param newLocation A Location object representing the organism's location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }


    // Timer methods

    /**
     * Obtain a reference to the Timer object used by the organism
     *
     * @return The reference to the Timer object used by the organism
     */
    protected Timer getTimerObject(){
        return timer;
    }

    /**
     * Obtain a reference to the Random object used by the organism to generate random numbers
     *
     * @return A reference to the Random object used by the organism
     */
    protected Random getRandomizerObject(){return rand;}

    /**
     * Checks whether the animal is awake or not
     *
     * @return true if awake, false otherwise
     */
    abstract protected boolean isAwake();
}
