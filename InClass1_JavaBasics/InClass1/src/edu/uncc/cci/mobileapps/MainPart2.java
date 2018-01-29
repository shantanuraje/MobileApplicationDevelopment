/////////////////////////////////////////////////////////////////
// In-class assignment #: 1
// Names: Shantanu Rajenimbalkar & Kuldeepsinh Chudasama
// File name: MainPart2.java
/////////////////////////////////////////////////////////////////

package edu.uncc.cci.mobileapps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainPart2 {
    /*
    * Question 2:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - The goal is to count the number of users living each state.
    * - Print out the list of State, Count order in ascending order by count.
    * */

    public static void main(String[] args) {

    	ArrayList<User> users = new ArrayList<User>();
    	HashMap<String , Integer> stateCounts = new HashMap<String, Integer>();

        for (String str : Data.users) {
            String [] data = str.split(",");
            User user = new User(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5], data[6]);
            users.add(user);
            // condition to check if key exists in hashmap, if not add to hashmap and initialize count as 0
           if (!stateCounts.containsKey(data[6])) {
				stateCounts.put(data[6], 0);
			}else {
				stateCounts.put(data[6], stateCounts.get(data[6]) + 1);
			}
        }
        Map<String, Integer> sortedStateCounts = sortByValue(stateCounts);
        System.out.println("States sorted by count:");
        System.out.println(sortedStateCounts);
    }
    
    
    // Reference: https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    	List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
    	Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
    		@Override
    		public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
    			return (e1.getValue()).compareTo(e2.getValue());
    		}
    	});
     
    	Map<K, V> result = new LinkedHashMap<>();
    	for (Map.Entry<K, V> entry : list) {
    		result.put(entry.getKey(), entry.getValue());
    	}
     
    	return result;
    }

    
}