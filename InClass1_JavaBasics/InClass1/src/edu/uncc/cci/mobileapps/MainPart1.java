/////////////////////////////////////////////////////////////////
// In-class assignment #: 1
// Names: Shantanu Rajenimbalkar & Kuldeepsinh Chudasama
// File name: MainPart1.java
/////////////////////////////////////////////////////////////////
package edu.uncc.cci.mobileapps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainPart1 {
    /*
    * Question 1:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - Insert each of the users in a list.
    * - Print out the TOP 10 oldest users.
    * */

	
    public static void main(String[] args) {
    	ArrayList<User> users = new ArrayList<User>(); //create arraylist of users

        for (String str : Data.users) {
            String [] data = str.split(",");
            // create custom user and assign data from array
            User user = new User(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5], data[6]); 
            users.add(user); // add user to arraylist
        }
        Collections.sort(users, new SortbyAge()); // sort users by age using custom comparator
        //print top 10 users
        for (int i=0; i < 11; i++) {
            users.get(i).printUser(); 
        }
    }
}

