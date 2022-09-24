package wtf.spark.impl.account;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.util.Session;
import wtf.spark.asm.mixins.duck.IMinecraft;
import wtf.spark.core.Spark;
import wtf.spark.impl.config.Config;
import wtf.spark.util.core.ClientImpl;
import wtf.spark.util.core.fs.FileSystemUtil;

import java.util.ArrayList;
import java.util.List;

public class AccountManager implements ClientImpl {

    private final MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
    private final List<Account> accountList = new ArrayList<>();

    public AccountManager() {
        new Config("accounts.txt") {

            @Override
            public void save() {
                StringBuilder builder = new StringBuilder();

                if (!accountList.isEmpty()) {
                    for (Account account : accountList) {
                        String ln = account.getEmail() + ":" + (account.getPassword() == null ? "n/a" : account.getPassword());

                        if (account.getName() != null) {
                            ln += ":" + account.getName();
                        }

                        builder.append(ln).append("\n");
                    }

                    FileSystemUtil.write(getFile(), builder.toString());
                }
            }

            @Override
            public void load() {
                String content = FileSystemUtil.read(getFile());
                if (content == null || content.isEmpty()) {
                    return;
                }

                for (String ln : content.split("\n")) {
                    String[] parts = ln.split(":");
                    if (parts == null || parts.length == 0) {
                        continue;
                    }

                    String email = null;
                    String password = null;
                    String name = null;

                    try {
                        email = parts[0];
                        password = parts[1];
                        name = parts[2];
                    } catch (IndexOutOfBoundsException ignored) {

                    }

                    if (email != null) {
                        Account account = new Account(email, password);
                        if (name != null) {
                            account.setName(name);
                        }

                        accountList.add(account);
                    }
                }

                Spark.LOGGER.info("Loaded {} accounts", accountList.size());
            }
        };
    }

    public void login(Account account) {
        if (account.isPremium()) {
            Spark.SPARK_EXECUTOR.execute(() -> {
                try {
                    MicrosoftAuthResult result = microsoftAuthenticator.loginWithCredentials(account.getEmail(), account.getPassword());

                    Session session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "mojang");
                    ((IMinecraft) mc).setSession(session);
                } catch (MicrosoftAuthenticationException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Session session = new Session(account.getEmail(), "", "", "legacy");
            ((IMinecraft) mc).setSession(session);
        }
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}
