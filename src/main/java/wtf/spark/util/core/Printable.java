package wtf.spark.util.core;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public interface Printable {
    String PREFIX = TextFormatting.YELLOW + "[Spark] " + TextFormatting.GRAY;

    default void print(String message) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString(PREFIX)
                        .setStyle(new Style().setColor(TextFormatting.GRAY))
                        .appendText(message)
        );
    }

    default void print(int id, String message) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(
                new TextComponentString(PREFIX)
                        .setStyle(new Style().setColor(TextFormatting.GRAY))
                        .appendText(message),
                id
        );
    }
}
