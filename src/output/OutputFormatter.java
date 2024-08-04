package output;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class used for outputting to the command prompt in a formated name, allowing for colored text,
 * partial or not, and starting with the current time, which is gathered from an Instant
 */
public class OutputFormatter {
    public static final String bigClear = "\n".repeat(50);
    public static final String midClear = "\n".repeat(30);
    public static final String lowClear = "\n".repeat(10);
    public static final Function<Integer, String> customClear = "\n"::repeat;
    /**
     *
     * @param output The String that is printed to the console,
     *               with the Instant.now() at the beginning of the String.
     */
    public static void format(String output){
        System.out.println(formattedNow() +
                ": " + output);
    }

    /**
     * Prints a line to the console, where a part of the text is colored. Valid colors are
     * <li>YELLOW / Y / y</li>
     * <li>RED / R / r</li>
     * <li>GREEN / G / g</li>
     * <li>MAGENTA / M / m || PURPLE / P / p</li>
     * <li>BLUE / B / b</li>
     * @param output The String that is to be printed to the console. Must be
     *               formatted as 'Text\\dCOLOR\\d text text9 other text'.
     */
    public static void format(String output, boolean partial){
        if(partial) {
            Pattern colorPattern = Pattern.compile("([A-Za-z\\s\\[\\].-]*)[0-9]([A-Za-z]+)[0-9]([A-Za-z\\s]+)9" +
                    "([A-Za-z\\s.!]+)");
            Matcher matcher = colorPattern.matcher(output);
            if (matcher.find()) {
                System.out.print(formattedNow()
                        + ": " + matcher.group(1));
                String usedColor = getColor(matcher.group(2));
                System.out.print(usedColor + matcher.group(3));
                System.out.println(ColorCodes.RESET.asColorCode + matcher.group(4));
            } else {
                format(output);
            }
        }else{
            Pattern colorPattern = Pattern.compile("[0-9]([A-Za-z]+)[0-9]([A-Za-z:{}\\s.\\n\\t-\\[\\]]+)");
            Matcher matcher = colorPattern.matcher(output);
            if(matcher.find()){
                format(getColor(matcher.group(1)) + matcher.group(2) + ColorCodes.RESET.asColorCode);
            }
        }
    }
    private static String getColor(String capturedGroup){
        return switch (capturedGroup) {
            case "YELLOW", "y", "Y" -> ColorCodes.YELLOW.asColorCode;
            case "RED", "r", "R" -> ColorCodes.RED.asColorCode;
            case "GREEN", "g", "G" -> ColorCodes.GREEN.asColorCode;
            case "MAGENTA", "m", "M", "PURPLE", "p", "P" -> ColorCodes.MAGENTA.asColorCode;
            case "BLUE", "b", "B" -> ColorCodes.BLUE.asColorCode;
            default -> ColorCodes.RESET.asColorCode;
        };
    }
    private static String formattedNow(){
        return LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
    }
}
