package output;

/**
 * Enum used by output.OutputFormatter to output colored text, according to the situation.
 * <br>Ex: Red for errors, Yellow for warnings and Green for successful operations.
 * <h3>Available colors</h3>
 * <ul>
 *     <li>RESET (default)</li>
 *     <li>RED</li>
 *     <li>GREEN</li>
 *     <li>YELLOW</li>
 *     <li>BLUE</li>
 *     <li>MAGENTA</li>
 * </ul>
 */
public enum ColorCodes {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m");
    public final String asColorCode;
    ColorCodes(String color){
        this.asColorCode = color;
    }
}
