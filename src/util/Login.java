package util;

public class Login extends Data {
    private String userPassword;
    private boolean loginResult = false;
    
    public Login(String owner, String userPassword) {
        super(owner);
        this.userPassword = userPassword;
        this.receiver = this.owner;
    }

    public void setLoginResult(boolean loginResult) {
        this.loginResult = loginResult;
    }

    public boolean getLoginResult() {
        return this.loginResult;
    }

    public String getUserPassword() {
        return this.userPassword;
    }
}