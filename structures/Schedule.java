/**
 * Schedule.java
 * Christina Buffo and Midori Yang
 * Last updated: 12/16/2016
 * CS230 Final Project
 * The schedule object contains a hashtable, which stores all the courses added so far.
 * The keys of the hashtable are the course department and number.
 */
import java.io.*;
import java.util.*;

public class Schedule{
  
  Hashtable<String, Course> sch;
  
  /*
   * Constructor for schedule class
   */ 
  public Schedule(){
    sch = new Hashtable<String, Course>(20);  
  }
  
  /**
   * Adds the course if it is not already present in the schedule.
   * @param c : the course to be added
   */ 
  public void addCourse(Course c){
    String current = c.getDeptNum();
    
    if (!sch.containsKey(current)){
      sch.put(current, c);
    }
    else{
     sch.remove(current);
     sch.put(current, c);
    }
  }
  
  /**
   * Removes the course if it was in the schedule.
   * Otherwise, does nothing.
   * @param c : the course to be removed
   */
  public void removeCourse(Course c){
    String current = c.getDeptNum();
    
    if(sch.containsKey(current)){
      sch.remove(current);
    }
  }
  
  /**
   * Returns the course corresponding to the given key
   * @return the course
   */
  public Course getCourse(String key){
   return sch.get(key); 
  }
  
  /**
   * Returns a list of all course department names and numbers.
   * @return the list of all course names and number
   * FIX THIS ASAP CAUSE toArray ISN'T WORKING PROPERLY WITH STRING[] PARAM
   */
  public String[] getAll(){
   Set temp =  sch.keySet();
   Object[] s = temp.toArray(new String[0]);
   String[] result = new String[s.length];

   for(int i=0; i<s.length; i++){
    result[i] = (String) s[i];     
   }
   
   return result;
  }
  
  /**
   * Returns a string represntation of all keys contained in the hashtable.
   * @return the string 
   */ 
  public String toString(){
    String s = "Schedule contains:\n";
    s += sch.keySet();
    return s;
  }
  
  /**
   * Main driver method for testing purposes.
   */ 
  public static void main(String[] args){
    Schedule s = new Schedule();
    //System.out.println("Newly created schedule:\n");
    //System.out.println(s);
    String[] mwth = {"Monday", "Wednesday", "Thursday"};
    String[] tf = {"Tuesday", "Friday"};
    
    Course cs230 = new Course("CS230", "Data Structures in Java", "9:50-11:10", tf);
    Course math215 = new Course("MATH215", "Math for the Sciences I", "8:30-9:40", mwth);   
    Course phys107 = new Course("PHYS107", "Mechanics", "9:50-11:10", mwth);
    Course rel263 = new Course("REL263", "Islam in the Modern World", "8:30-9:40", tf);
    Course pol221 = new Course("POL3221", "World Politics", "11:10-12:20", tf);
    Course chin201 = new Course("CHIN201", "Intermediate Chinese", "11:10", mwth);
    
    //System.out.println("\nAdding the first course:");
    s.addCourse(cs230);
    //System.out.println(s);
    
    //System.out.println("\nAdding additional courses:");
    s.addCourse(math215);
    s.addCourse(phys107);
    s.addCourse(rel263);
    s.addCourse(pol221);
    //System.out.println(s);
    
    //System.out.println("\nTrying to add CS230 again (should overwrite):");
    s.addCourse(cs230);
    //System.out.println(s);
    
    //System.out.println("\nRemoving REL263:");
    s.removeCourse(rel263);
    //System.out.println(s);
    
    //System.out.println("\nRemoving CHIN201 (not present in schedule - should produce no change):");
    s.removeCourse(chin201);
    //System.out.println(s);
    
    String[] names = s.getAll();
    for(int i=0; i < names.length; i++){
     System.out.println(names[i]);     
    }
  }
}