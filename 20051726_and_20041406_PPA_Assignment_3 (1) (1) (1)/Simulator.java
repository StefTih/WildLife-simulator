import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing Animals and Plants
 *
 * @author David J. Barnes, Michael Kölling, Maksymilian Sekula and Tihomir Stefanov
 * @version 03/03/2021
 */

public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a predator will be created in any given grid position.
    private static final double PREDATOR_CREATION_PROBABILITY = 0.02;
    // The probability that a prey will be created in any given grid position.
    private static final double PREY_CREATION_PROBABILITY = 0.08;
    // The probability that a plant will be created in any given grid position.
    private static final double PLANT_CREATION_PROBABILITY = 0.2;

    // List of acting organisms in the field.
    private List<Organism> organisms;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    //Timer for the simulation
    private Timer timer;

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args){
        Simulator simulation1 = new Simulator(100,140);
        simulation1.runLongSimulation();
    }

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        organisms = new ArrayList<>();
        field = new Field(depth, width);
        timer = new Timer();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Antelope.class, Color.BLUE);
        view.setColor(Zebra.class, Color.black);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Lion.class, Color.RED);
        view.setColor(Hyena.class, Color.orange);
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps ; step++) {
            simulateOneStep();
            //delay(100);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each active organism
     */
    public void simulateOneStep()
    {
        step++;
        timer.incrementTime();

        // Provide space for newborn organisms.
        List<Organism> newOrganisms = new ArrayList<>();

        // Let all organisms act.
        // Uses two streams to increase efficiency over iterator method
        Stream<Organism> str = organisms.stream();
        str.forEach(org -> org.act(newOrganisms));
        Stream<Organism> str2 = organisms.stream();
        organisms = str2.filter(Organism::isAlive).collect(Collectors.toList());

        // Add the newly born foxes and rabbits to the main lists.
        organisms.addAll(newOrganisms);

        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        organisms.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {

                // Adds Predators
                if(rand.nextDouble() <= PREDATOR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);

                    // Distributes Lions and Hyenas with roughly equal probability
                    if(rand.nextBoolean()) {
                        Lion lion = new Lion(true, true, field, location, timer);
                        organisms.add(lion);
                    }
                    else {
                        Hyena hyena = new Hyena(true,true, field, location, timer);
                        organisms.add(hyena);
                    }
                }

                // Adds Prey
                else if(rand.nextDouble() <= PREY_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);

                    // Distributes Antelopes and Zebras with roughly equal probability
                    if(rand.nextBoolean()) {
                        Antelope antelope = new Antelope(true,true, field, location, timer);
                        organisms.add(antelope);
                    }
                    else {
                        Zebra zebra = new Zebra(true,true, field, location, timer);
                        organisms.add(zebra);
                    }
                }

                // Adds Plants
                else if(rand.nextDouble() <= PLANT_CREATION_PROBABILITY){
                    Location location = new Location(row, col);

                    Plant plant = new Grass(true, field, location, timer);
                    organisms.add(plant);

                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
