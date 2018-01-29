/////////////////////////////////////////////////////////////////
// In-class assignment #: 1
// Names: Shantanu Rajenimbalkar & Kuldeepsinh Chudasama
// File name: User.java
/////////////////////////////////////////////////////////////////


package edu.uncc.cci.mobileapps;
import java.util.Comparator;

public class User {
	// user properties
	private String firstname;
	private String lastname;
	int age;
	private String email;
	private String gender;
	private String city;
	private String state;

	//constructor to assign user properties
	public User(String firstname,String lastname, int age, String email, String gender, String city, String state) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.email = email;
		this.gender = gender;
		this.city = city;
		this.state = state;
	}
	
	// custom method to print user details
	public void printUser() {
		System.out.println("First name: " + this.firstname);
		System.out.println("Last name: " + this.lastname);
		System.out.println("Age: " + this.age);
		System.out.println("Email: " + this.email);
		System.out.println("Gender: " + this.gender);
		System.out.println("City: " + this.city);
		System.out.println("State: " + this.state + "\n");
	}
	
}

// custom comparator to compare 2 users by age
class SortbyAge implements Comparator<User>
{
    // Used for sorting in descending order of age
    public int compare(User a, User b)
    {
        return b.age - a.age;
    }
}