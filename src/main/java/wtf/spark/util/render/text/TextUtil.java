package wtf.spark.util.render.text;

public class TextUtil {

    public static String formatEnumName(Enum<?> enumValue) {
        String name = enumValue.toString();
        String[] parts = name.split("_");

        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(Character.toString(part.charAt(0)).toUpperCase() + part.substring(1).toLowerCase()).append(" ");
        }

        return builder.toString().trim();
    }
}
