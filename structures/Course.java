/**
 * FILE NAME: Course.java
 * WHO: Christina Buffo
 * WHAT: Course objects store information about a particular course,
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
  public boolean visible;
  public double credit;
  
  
  /**
   * Constructor for Course class
   * @param deptNum: the course department and number
   * @param name: the course name
   * @param time: the time the class starts
   * @param days: the days of the week the course meets on
   * @param credit: the number of credits for this course
   */ 
  public Course(String deptNum, String name, String time, String[] days, double credit){
    this.deptNum = deptNum;
    this.name = name;
    this.time = time;
    this.notes = "";
    this.credit = credit;
    meetings = new LinkedList<Meeting>();
    numMeetings = 0;
    visible = true;
    
    for (int i=0; i< days.length; i++){
     addMeeting(days[i]);      
    }
  }
  
  /**
   * Constructor for Course class, with notes
   * @param deptNum: the course department and number
   * @param name: the course name
   * @param time: the time the class starts
   * @param days: the days of the week the course meets on
   * @param notes: any additional information about the class
   */
  public Course(String deptNum, String name, String time, String[] days, double credit, String notes){
   this(deptNum, name, time, days, credit); 
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
   * @param note: the notes
   */ 
  public void setNotes(String note){
    notes = note;
  }
  
  /**
   * Sets the course visibility to true.
   */
  public void show(){
   visible = true; 
  }
  
  /**
   * Sets the course visibility to false.
   */ 
  public void hide(){
   visible = false; 
  }
  
  /**
   * Returns a boolean value with the visibility of the course.
   * @return if the course is visible
   */
  public boolean isVisible(){
   return visible; 
  }
  
  /**
   * Returns the number of credits for this course.
   * @return the number of credits
   */
  public double getCredits(){
   return credit; 
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
   * @param day: the day the meeting takes place
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
   * @param day: the day the meeting takes place
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
    String s = deptNum + ": " + name + "\n" + time + "\n";
    
    if (!meetings.isEmpty()){ // if at least one meeting has been added to the course
      s+= "Meets on: ";
      
      for(int i=0; i < meetings.size(); i++){
        s+= meetings.get(i).getDay() + " "; 
      }
    }    
    
    else{ // if no meetings have been added
      s+="No meeting days specified."; 
    }
    s+= "\nCredits: " + credit;
    
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
    Course test1 = new Course("CS230", "Data Structures in Java", "9:50-11:10", tf, 1);
    System.out.println("Creating a new object:\n" + test1);
    
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
    Course test2 = new Course("MATH215", "Mathematics for the Sciences I", "8:30-9:40", mwth, 1, "Prerequisites: MATH 116");
    System.out.println(test2);
    
    System.out.println("\nMeetings of MATH215:");
    System.out.println(test2.getMeetings());
    
    System.out.println("\nTesting visibility methods:");
    System.out.println("MATH215 is visible: " + test2.isVisible());
    
    test2.hide();
    System.out.println("Hiding MATH215\nMATH215 is visible: " + test2.isVisible());
    
    test2.show();
    System.out.println("Showing MATH215\nMATH215 is visible: " + test2.isVisible());
  }
}