package config;

import java.io.Serial;
import java.io.Serializable;

/**
 * Record used by the Config instances to write in the .dat files using ObjectOutputStream and read from
 * using ObjectInputStream. Can be extended, however it will not be compatible with other WritableObject
 * classes.
 * @param key The key used. Must be unique, as this is the identifier for the record.
 * @param value The value. Can be anything, and does not have to be unique.
 */
public record WritableObject(String key, String value) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    @Override
    public String toString() {
        return "WritableObject{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WritableObject that = (WritableObject) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
