package config;

import output.OutputFormatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The config class, used for managing the .dat files. Has methods for adding, removing and
 * retrieving records from the file.
 * @param <T> The object used in the Object streams. Must extend WritableObject.
 */
public class Config<T extends WritableObject> {
    private final Path configFile;
    private final List<T> objectsList = new ArrayList<>();
    private final boolean suppressExceptions;
    public Config(Path configFile) {
        this.configFile = configFile;
        this.suppressExceptions = false;
        reloadIfNeeded();
    }
    public Config(Path configFile, boolean suppressExceptions){
        this.configFile = configFile;
        this.suppressExceptions = suppressExceptions;
        reloadIfNeeded();
    }

    /**
     * Adds an object to the objectsList, and to the .dat file used for config.
     * @param object the object, of type T (extends WritableObject) to be added.
     * @return true if list did not contain the key, and false otherwise.
     */
    public boolean add(T object){
        if(objectsList.contains(object)){
            return false;
        }
        objectsList.add(object);
        try {
            new ObjectOutputStream(Files.newOutputStream(configFile)).writeObject(objectsList);
        }catch (IOException e){
            OutputFormatter.format("Could not add new object to file.");
        }
        reload();
        return true;
    }

    /**
     * Removes an object from the objectList, and from the .dat file used for config.
     * @param object the object, of type T (extends WritableObject) to be removed.
     * @return true if the object was removed successfully, and false if the object did not exist in
     * the list.
     */
    public boolean remove(T object){
        boolean contained = objectsList.remove(object);
        try {
            new ObjectOutputStream(Files.newOutputStream(configFile)).writeObject(objectsList);
        }catch (IOException e){
            OutputFormatter.format("Could not add new object to file.");
        }
        reload();
        return contained;
    }

    /**
     * @return an unmodifiable view of the objectsList.
     */
    public List<T> getView(){
        return Collections.unmodifiableList(objectsList);
    }
    public T get(String key){
        T foundObject = null;
        for(T t : objectsList){
            if(t.key().equals(key)){
                foundObject = t;
                break;
            }
        }
        return foundObject;
    }

    /**
     * Retrieves a record based on the index in the objectList.
     * @param index the index used for retrieval.
     * @return the object
     * @throws IndexOutOfBoundsException if the index is lower than 0 or higher or equal to the list size.
     */
    public T get(int index){
        return getView().get(index);
    }

    /**
     * Safely retrieves the object, without throwing exception, returning null if index is
     * out of bounds.
     * @param index the index used for retrieval.
     * @return either the object, or null if index is invalid.
     */
    public T tryGet(int index){
        if(index >= getView().size() || index < 0){
            OutputFormatter.format("Trying to get object failed, invalid index.");
            return null;
        }
        return get(index);
    }

    /**
     * Reloads the .dat file into the List. Will throw RuntimeExceptions from the following exceptions if
     * suppressExceptions is set to false.
     * @throws IOException if path is invalid
     * @throws ClassNotFoundException if List< T extends WritableObject > could not be found.
     */
    @SuppressWarnings("unchecked")
    public void reload(){
        try {
            objectsList.clear();
            objectsList.addAll((List<T>) new ObjectInputStream(
                    Files.newInputStream(configFile)).readObject());
        } catch (IOException e) {
            OutputFormatter.format("Error when trying to reload config file for " + configFile);
            if(!suppressExceptions) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            OutputFormatter.format("Could not cast class" +
                    (objectsList.getFirst() != null
                            ? (" to " + objectsList.getFirst().getClass())
                            : ""));
            if(!suppressExceptions) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Safe reloads, only if .dat file is not empty.
     * @return true if reload was needed, false if not.
     */
    protected boolean reloadIfNeeded(){
        long byteLength = 0;
        try (var iStream = Files.newInputStream(configFile)){
            byteLength = iStream.readAllBytes().length;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(byteLength != 0) {
            reload();
            return true;
        }
        return false;
    }
}
