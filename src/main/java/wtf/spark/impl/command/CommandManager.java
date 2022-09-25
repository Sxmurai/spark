package wtf.spark.impl.command;

import me.bush.eventbus.annotation.EventListener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.lwjgl.input.Keyboard;
import wtf.spark.core.Spark;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.impl.command.impl.*;
import wtf.spark.impl.command.parse.CommandParseResult;
import wtf.spark.impl.command.parse.CommandParser;
import wtf.spark.impl.config.Config;
import wtf.spark.impl.event.KeyInputEvent;
import wtf.spark.impl.event.PacketEvent;
import wtf.spark.util.core.ClientImpl;
import wtf.spark.util.core.Printable;
import wtf.spark.util.core.fs.FileSystemUtil;
import wtf.spark.util.core.io.InputUtil;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements ClientImpl, Printable {

    private static final int MESSAGE_ID = -133769420;

    private final List<Command> commandList = new ArrayList<>();
    private final CommandParser commandParser;
    private String prefix = "'";

    public CommandManager() {
        Spark.BUS.subscribe(this);

        commandList.add(new Bind());
        commandList.add(new FPS());
        commandList.add(new Help());
        commandList.add(new Prefix());
        commandList.add(new HClip());
        commandList.add(new VClip());
        commandList.add(new ViewDistance());

        new Config("command_prefix.txt") {

            @Override
            public void save() {
                FileSystemUtil.write(getFile(), prefix);
            }

            @Override
            public void load() {
                String content = FileSystemUtil.read(getFile());
                if (content == null || content.isEmpty()) {
                    return;
                }

                prefix = content
                        .trim()
                        .replaceAll("\n", "")
                        .replaceAll("\n\r", "");
            }
        };

        commandParser = new CommandParser(this);
    }

    @EventListener
    public void onKeyInput(KeyInputEvent event) {
        if (prefix.length() == 1 && mc.currentScreen == null) {
            int keyCode = InputUtil.getKeyCodeForSpecial(prefix.toUpperCase());
            if (keyCode != Keyboard.KEY_NONE && event.getKeyCode() == keyCode && !event.isState()) {
                mc.displayGuiScreen(new GuiChat(prefix));
            }
        }
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if (event.isClientBound() && event.getPacket() instanceof CPacketChatMessage) {
            CPacketChatMessage packet = event.getPacket();
            if (packet.getMessage().startsWith(prefix)) {
                String message = packet.getMessage();

                // we dont want us to actually send the message
                event.setCancelled(true);

                CommandParseResult result = commandParser.parse(message);
                if (result != null) {
                    if (result.getErrorMessage() != null) {
                        print(result.getErrorMessage(), MESSAGE_ID);
                        return;
                    }

                    try {
                        CommandContext ctx = result.getCommandContext();
                        if (ctx != null) {
                            String dispatched = ctx.getCommand().dispatch(ctx);
                            if (dispatched == null) {
                                dispatched = "Command dispatched successfully.";
                            }

                            print(dispatched, MESSAGE_ID);
                        } else {
                            print("Error: context was null", MESSAGE_ID);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        print("Failed to run command", MESSAGE_ID);
                    }
                } else {
                    print("Could not parse that command.", MESSAGE_ID);
                }
            }
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
