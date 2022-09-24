package wtf.spark.impl.account.ui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import wtf.spark.util.core.fs.FileSystemUtil;
import wtf.spark.util.core.http.HttpUtil;

public class AccountManagerScreen extends GuiScreen {

    public static final String AUTH_SERVER = "https://authserver.mojang.com/";
    public static final String SESSION_SERVER = "https://sessionserver.mojang.com/";

    private Thread checkerThread;
    private String auth = TextFormatting.GREEN + "Online";
    private String session = TextFormatting.GREEN + "Online";

    @Override
    public void initGui() {
        buttonList.clear();

        checkerThread = new Thread(() -> {
            HttpUtil.get(AUTH_SERVER, (conn) -> {
                if (conn.getResponseCode() != 200) {
                    auth = TextFormatting.RED + "Offline";
                } else {
                    if (conn.getInputStream() != null) {
                        String content = FileSystemUtil.read(conn.getInputStream());
                        if (content != null && !content.isEmpty()) {
                            JsonObject obj = JsonParser.parseString(content).getAsJsonObject();
                            if (obj.has("Status")) {
                                String status = obj.get("Status").getAsString();
                                if (!status.equalsIgnoreCase("ok")) {
                                    auth = TextFormatting.RED + "Offline";
                                }
                            }
                        }
                    } else {
                        auth = TextFormatting.YELLOW + "No Response";
                    }
                }
            });

            HttpUtil.get(SESSION_SERVER, (conn) -> {
                if (conn.getResponseCode() != 200) {
                    auth = TextFormatting.RED + "Offline";
                } else {
                    if (conn.getInputStream() != null) {
                        String content = FileSystemUtil.read(conn.getInputStream());
                        if (content != null && !content.isEmpty()) {
                            JsonObject obj = JsonParser.parseString(content).getAsJsonObject();
                            if (!obj.has("Path")) {
                                auth = TextFormatting.RED + "Offline";
                            }
                        }
                    } else {
                        auth = TextFormatting.YELLOW + "No Response";
                    }
                }
            });
        }, "Server Check Thread");
        checkerThread.start();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        mc.fontRenderer.drawStringWithShadow("Session Server: " + auth, 2, 2, 8421504);
        mc.fontRenderer.drawStringWithShadow("Auth Server: " + session, 2, 12, 8421504);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        checkerThread.interrupt();
        checkerThread = null;
    }
}
