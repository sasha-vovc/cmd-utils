package commands;

import output.HelpManuals;

/**
 * Class that all commands should extend if possible.
 */
public abstract class Command {
    public final String cmdName;

    protected Command(String cmdName){
        this.cmdName = cmdName;
    }

    /**
     * The method that will run and be called after input is processed in CommandsPipeline.
     * @return true if the method ran successfully, false if it encountered an error.
     */
    public abstract boolean run();

    /**
     * @return the manual for this command
     */
    public String getManual(){
        return HelpManuals.getViewAsMap().get(cmdName);
    }
}
