package commands;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandsPipeline {
    protected List<Runnable> commands = new ArrayList<>();

    /**
     *Used to process and run an appropriate runnable for each.
     * @param input The input String collected from the terminal.
     */
    public abstract void process(String input);

    /**
     * Computes the input args, as a regex, matching it with possible patterns.
     * @param args The args passed from the process() method
     */
    public abstract void compute(String args);
}
