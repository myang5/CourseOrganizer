/**
 * Course.java
 * Christina Buffo and Midori Yang
 * Last updated: 12/16/2016
 * CS230 Final Project
 * Course objects store information about a particular course,
 * including a linkedlist holding the appropriate number of meeting objects.
 * It also contains information corresponding to the course department and 
 * number, as well as the course name.
 */
import java.util.LinkedList;

public class Course{
  
  public String deptNum; // department and number e.g. CS230
  public String name;
  public String time;
  public String notes;
  public LinkedList<Meeting> meetings;
  public int numMeetings; // the number of times per week that the course meets
  
  
  /**
   * Constructor for Course class, with notes
   * @param deptNum : the course department and number
   * @param name : the course name
   * @param time : the time the class starts
   */ 
  public Course(String deptNum, String name, String time, String[] days){
    this.deptNum = deptNum;
    this.name = name;
    this.time = time;
    this.notes = "";
    meetings = new LinkedList<Meeting>();
    numMeetings = 0;
    
    for (int i=0; i< days.length; i++){
     addMeeting(days[i]);      
    }
  }
  
  
  public Course(String deptNum, String name, String time, String[] days, String notes){
   this(deptNum, name, time, days); 
   this.notes = notes;
  }
  
  /**
   * Returns the course department and number 
   * @return the course department and number
   */ 
  public String getDeptNum(){
    return deptNum; 
  }
  
  /**
   * Returns the course name
   * @return the course name
   */
  public String getName(){
    return name; 
  }
  
  
  /**
   * Returns the time the meeting starts.
   * @return the start time
   */ 
  public String getTime(){
    return time; 
  }
  
  /**
   * Sets the notes of the course (any additional information).
   * @param note : the notes
   */ 
  public void setNotes(String note){
    notes = note;
  }
  
  /**
   * Returns the notes of the course.
   * @return the notes
   */
  public String getNotes(){
   return notes;    
  }
  
  /**
   * Returns the meeting list.
   * @return the meeting list
   */ 
  public LinkedList<Meeting> getMeetings(){
   return meetings;   
  }
  
  /**
   * Adds a meeting to the list of meetings, and increases the number of meetings by 1.
   * If the meeting is already in the list, nothing is added.
   * @param day : the day the meeting takes place
   */ 
  public void addMeeting(String day){
    Meeting temp = new Meeting(day, time);
    boolean add = true;
    
    for (int i=0; i < meetings.size(); i++){
     Meeting current = meetings.get(i);
     if (current.getDay() == day){
      add = false; 
     }
    }
    
    if(add){
     meetings.add(temp); 
    }
  }
  
  /**
   * If the specified meeting time is already contained in the list of meetings, 
   * removes the meeting. Otherwise, does nothing.
   * @param day : the day the meeting takes place
   */ 
  public void removeMeeting(String day){    
    for (int i=0; i < meetings.size(); i++){
      Meeting current = meetings.get(i);
      if (current.getDay() == day){
       meetings.remove(current); 
      }
    }
  }
  
  /**
   * Returns a string representation of the course class.
   * @return the string containing all the information of the course
   */ 
  public String toString(){
    String s = deptNum + ": " + name + "\n";
    
    if (!meetings.isEmpty()){ // if at least one meeting has been added to the course
      s+= "Meets on: ";
      
      for(int i=0; i < meetings.size(); i++){
        s+= meetings.get(i).getDay() + " "; 
      }
    }    
    
    else{ // if no meetings have been added
      s+="No meeting days specified."; 
    }
    if(!notes.equals("")){
     s +="\n" + notes; 
    }
    return s;
  }
  
  /** 
   * Main method for testing 
   */ 
  public static void main(String[] args){
    String[] mwth = {"Monday", "Wednesday", "Thursday"};
    String[] tf = {"Tuesday", "Friday"};
    Course test1 = new Course("CS230", "Data Structures in Java", "9:50", tf);
    System.out.println("Creating a new object:\n" + test1 +"\n");
    
    System.out.println("\nAdding a meeting on Saturday:");
    test1.addMeeting("Saturday");
    System.out.println(test1);
    
    System.out.println("\nRemoving the Saturday meeting");
    test1.removeMeeting("Saturday");
    System.out.println(test1);
    
    System.out.println("\nAdding a second meeting on Friday (should not work)");
    test1.addMeeting("Friday");
    System.out.println(test1);
    
    System.out.println("\nAdding notes");
    test1.setNotes("Lab registration also required");
    System.out.println(test1);
    
    System.out.println("\nTesting constructor with notes");
    Course test2 = new Course("MATH215", "Mathematics for the Sciences I", "8:30", mwth, "Prerequisites: MATH 116");
    System.out.println(test2);
    
    System.out.println("\nMeetings of MATH215:");
    System.out.println(test2.getMeetings());
  }
}