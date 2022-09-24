package wtf.spark.impl.account;

public class Account {

    private final String email, password;
    private final boolean premium;
    private String name;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        premium = password != null && !password.equals("n/a");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPremium() {
        return premium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
