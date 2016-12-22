/**
 * FILE NAME: Schedule.java
 * WHO: Christina Buffo
 * WHAT: Contains a hashtable, which stores all the courses added so far.
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
   * Constructor for schedule class given a file.
   * Note: this is solely used for testing purposes and will only function
   * with specific formats of files.  It will not be available to the user.
   * @param infile_name: the name of the input file
   */
  public Schedule(String infile_name){
    this();
    try{
      Scanner fileReader = new Scanner(new File(infile_name));
      String s = "";
      fileReader.nextLine();// skip the first 2 lines of the page
      fileReader.nextLine();
      
      while(fileReader.hasNext()){
        s = fileReader.nextLine();
        
        if(!s.equals(" ")){
          // deptNum and name on same line
          String[] temp = s.split(":");
          String deptNum =  temp[0];
          String name = temp[1];
          
          // time
          String time = fileReader.nextLine();
          
          // list of meeting days
          s = fileReader.nextLine();
          String days = s.split(":")[1];
          String[] daysList = days.split(" ");
          
          // credits
          s = fileReader.nextLine();
          temp = s.split(": ");
          double credit = Double.parseDouble(temp[1]);
          
          String notes = "";
          
          // for reading in notes: may not be any
          if(fileReader.hasNext()){
            s = fileReader.nextLine();
            
            if(!s.equals(" ")){
              notes = s;
            }
          }
          Course c = new Course(deptNum, name, time, daysList, credit, notes);
          addCourse(c);
        }
      }
      
    } catch (IOException ex){
      System.out.println("****ERROR**** The file does not exist: " + ex);
    }
  }
  
  /**
   * Adds the course if it is not already present in the schedule.
   * @param c: the course to be added
   */ 
  public void addCourse(Course c){
    String current = c.getDeptNum();
    
    if (!sch.containsKey(current)){
      sch.put(current, c);
    }
    else{ // should overwrite the previous version
      sch.remove(current);
      sch.put(current, c);
    }
  }
  
  /**
   * Removes the course if it was in the schedule.
   * Otherwise, does nothing.
   * @param c: the course to be removed
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
   * Returns the list of all courses that are currently visible.
   * @return the visible courses in linkedlist form
   */
  public LinkedList<Course> getVisibleList(){
    LinkedList<Course> list = new LinkedList<Course>();
    String[] temp = getAll();
    
    for (int i=0; i< temp.length; i++){
      Course c = sch.get(temp[i]);
      
      if(c.isVisible()){
        list.add(c);
      }
    }
    
    return list;
  }
  
  /**
   * Returns a list of all visible department names and numbers
   * @return the visible course names and numbers in array form
   */
  public String[] getVisibleArray(){
    LinkedList<Course> list = getVisibleList();
    String[] result = new String[list.size()];
    
    for (int i=0; i< list.size(); i++){
      result[i] = list.get(i).getDeptNum();
    }
    
    return result;
  }
  
  /**
   * Saves a txt file of the currently visible courses.
   * @param output_name: the name of of the file to be printed
   */
  public void saveVisible(String output_name){
    try{
      PrintWriter writer = new PrintWriter(new File(output_name));
      writer.println("Current schedule includes: \n");
      
      LinkedList<Course> list = getVisibleList();
      
      for (int i=0; i<list.size(); i++){
        Course c = list.get(i);
        writer.println(c.toString());
        writer.println();
      }
      writer.close();
    } catch (IOException ex) {
      System.out.println("***ERROR*** The file could not be written: " + ex); 
    }   
  }
  
  /**
   * Returns the total number of credits of all visible classes
   * @return the total credits
   */ 
  public double sumVisible(){
    LinkedList<Course> list = getVisibleList();
    double sum = 0;
    
    for (int i=0; i < list.size(); i++){
     Course c = list.get(i);
     double temp = c.getCredits();
     sum += temp;
    }
    
    return sum;
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
    
    Course cs230 = new Course("CS230", "Data Structures in Java", "9:50-11:10", tf, 1);
    Course math215 = new Course("MATH215", "Math for the Sciences I", "8:30-9:40", mwth, 1);   
    Course phys107 = new Course("PHYS107", "Mechanics", "9:50-11:10", mwth, 1.25);
    Course rel263 = new Course("REL263", "Islam in the Modern World", "8:30-9:40", tf, 1);
    Course pol221 = new Course("POL3221", "World Politics", "11:10-12:20", tf, 1);
    Course chin201 = new Course("CHIN201", "Intermediate Chinese", "11:10", mwth, 1.25);
    
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
    
    //System.out.println("\nAll courses in the schedule:");
    String[] names = s.getAll();
    for(int i=0; i < names.length; i++){
      //System.out.println(names[i]);     
    }
    
    phys107.hide();
    math215.hide();
    //System.out.println("\nVisible courses: (should be only CS230 and POL3221)");
    String[] vis = s.getVisibleArray();
    for (int i=0; i < vis.length; i++){
      //System.out.println(vis[i]); 
    }
    
    //System.out.println("\nTesting saving the visible in a file");
    s.saveVisible("test.txt");
    //System.out.println("File saved as 'test.txt'.");
    
    System.out.println("\nTesting reading in from a file (reading in from test.txt):");
    Schedule s1 = new Schedule("test.txt");
    System.out.println(s1);
    
    System.out.println("\nTesting sumVisible: totals the credits of all visible classes");
    System.out.println("Credits: " + s1.sumVisible());
  }
}