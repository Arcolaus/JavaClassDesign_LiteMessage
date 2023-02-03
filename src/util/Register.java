package util;

public class Register extends Data{
	private String userPassword;

	public Register(String owner, String password) {
		super(owner);
		this.userPassword = password;
	}
	
	public String getPassword() {
		return this.userPassword;
	}
}
