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
    System.out.println("Newly created schedule:\n");
    System.out.println(s);
    String[] mwth = {"Monday", "Wednesday", "Thursday"};
    String[] tf = {"Tuesday", "Friday"};
    
    Course cs230 = new Course("CS230", "Data Structures in Java", "9:50", tf);
    cs230.addMeeting("Tuesday");
    cs230.addMeeting("Friday");
    
    Course math215 = new Course("MATH215", "Math for the Sciences I", "8:30", mwth);
    math215.addMeeting("Monday");
    math215.addMeeting("Wednesday");
    math215.addMeeting("Thursday");
    
    Course phys107 = new Course("PHYS107", "Mechanics", "9:50", mwth);
    phys107.addMeeting("Monday");
    phys107.addMeeting("Wednesday");
    phys107.addMeeting("Thursday");
    
    Course rel263 = new Course("REL263", "Islam in the Modern World", "8:30", tf);
    rel263.addMeeting("Tuesday");
    rel263.addMeeting("Friday");
    
    Course pol221 = new Course("POL3221", "World Politics", "11:10", tf);
    pol221.addMeeting("Tuesday");
    pol221.addMeeting("Friday");
    
    Course chin201 = new Course("CHIN201", "Intermediate Chinese", "11:10", mwth);
    
    System.out.println("\nAdding the first course:");
    s.addCourse(cs230);
    System.out.println(s);
    
    System.out.println("\nAdding additional courses:");
    s.addCourse(math215);
    s.addCourse(phys107);
    s.addCourse(rel263);
    s.addCourse(pol221);
    System.out.println(s);
    
    System.out.println("\nTrying to add CS230 again (should overwrite):");
    s.addCourse(cs230);
    System.out.println(s);
    
    System.out.println("\nRemoving REL263:");
    s.removeCourse(rel263);
    System.out.println(s);
    
    System.out.println("\nRemoving CHIN201 (not present in schedule - should produce no change):");
    s.removeCourse(chin201);
    System.out.println(s);
  }
}