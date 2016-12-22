/**
 * FILE NAME: Meeting.java
 * WHO: Christina Buffo
 * WHAT: Meeting objects are subclasses of the course that store
 * information about the course name, meeting time, and meeting day.
 */
public class Meeting {
  
  private String day;
  private String time;
  
  /**
   * Constructor for Meeting class: requires day and time.
   * @param day: the day the meeting occurs
   * @param time: the time the meeting occurs
   */ 
  public Meeting(String day, String time){
    this.day = day;
    this.time = time;
  }
  
  /**
   * Returns the day the meeting occurs.
   * @return the day
   */ 
  public String getDay(){
    return day; 
  }
  
  /**
   * Returns the time the meeting starts.
   * @return the start time
   */ 
  public String getTime(){
    return time; 
  }
  
  
  /**
   * Returns a string representation of the meeting.
   * @return the string representation of the meeting
   */ 
  public String toString(){
    return "Meeting occurs on: " + day + " at " + time; 
  }
  
  /**
   * Main driver method for testing purposes. 
   */ 
  public static void main(String[] args){
    Meeting test1 = new Meeting("Wednesday", "8:30-9:40");
    System.out.println(test1); 
    
    Meeting test2 = new Meeting("Thursday", "2:50-4:00");
    System.out.println(test2);
  }
}