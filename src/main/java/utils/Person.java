package utils;

public class Person{
	String email;
	String name;
	String password;
	
	public Person(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void updateEmail(String email) {
		this.email = email;
	}
	
    public String getName() {
    	return this.name;
    }
    
    public void updateName(String name) {
    	this.name = name;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public void updatePassword(String password) {
    	this.password = password;
    }
    
    public boolean verifyPassword(String password) {
    	return this.password.equals(password);
    }
	
}