package output;

import config.Config;
import config.WritableObject;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class used to gather help manuals for commands.
 */
public class HelpManuals{
    private static Config<WritableObject> helpConfig = new Config<>
            (Path.of("configs/helpManuals.dat"));

    /**
     * Prints the help for the requested command
     * @param command Must be the same as the command String used when the WritableObject has been
     *                added.
     */
    public static void printHelp(String command){
        System.out.print(OutputFormatter.lowClear);
        OutputFormatter.format("Printing help manual for " + command + ":");
        System.out.println(helpConfig.get(command));
    }
    public static Map<String,String> getViewAsMap(){
        Map<String,String> map = new LinkedHashMap<>();
        helpConfig.getView().forEach(e -> map.put(e.key(), e.value()));
        return Collections.unmodifiableMap(map);
    }

    /**
     * Changes the file path of the config.
     * <i>Only do this if absolutely needed</i>
     * @param newPath The new path to the config file.
     */
    public static void changeFilePath(Path newPath){
        helpConfig = new Config<>(newPath);
    }

    /**
     * Method used to be able to modify the help manuals.
     * @return Config file used for CRUD operations on the .dat file
     */
    public static Config<WritableObject> getHelpConfig(){
        return helpConfig;
    }

}
