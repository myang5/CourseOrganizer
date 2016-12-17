//********************************************************************
//  CircularArrayQueue.java       Java Foundations
//
//  Represents an array implementation of a queue in which neither
//  end of the queue is fixed in the array. The variables storing the
//  indexes of the front and rear of the queue continually increment
//  as elements are removed and added, cycling back to 0 when they
//  reach the end of the array.
//********************************************************************

package javafoundations;

import javafoundations.exceptions.*;

public class CircularArrayQueue<T> implements Queue<T>
{
  private final int DEFAULT_CAPACITY = 3;
  private int front, rear, count;
  private T[] queue;
  
  //-----------------------------------------------------------------
  //  Creates an empty queue using the default capacity.
  //-----------------------------------------------------------------
  public CircularArrayQueue()
  {
    front = rear = count = 0;
    queue = (T[]) (new Object[DEFAULT_CAPACITY]);
  }
  
  //-----------------------------------------------------------------
  //  Adds the specified element to the rear of this queue, expanding
  //  the capacity of the queue array if necessary.
  //-----------------------------------------------------------------
  public void enqueue (T element)
  {
    if (count == queue.length)
      expandCapacity();
    
    queue[rear] = element;
    rear = (rear+1) % queue.length;
    count++;
  }
  
  //-----------------------------------------------------------------
  //  Creates a new array to store the contents of this queue with
  //  twice the capacity of the old one.
  //-----------------------------------------------------------------
  public void expandCapacity()
  {
    T[] larger = (T[])(new Object[queue.length*2]);
    
    for (int index=0; index < count; index++)
      larger[index] = queue[(front+index) % queue.length];
    
    front = 0;
    rear = count;
    queue = larger;
  }
  
  //-----------------------------------------------------------------
  //  The following methods are left as Programming Projects.
  //-----------------------------------------------------------------
  // public T dequeue () throws EmptyCollectionException { }
  // public T first () throws EmptyCollectionException { }
  // public int size() { }
  // public boolean isEmpty() { }
  // public String toString() { }
  
  //----------------------------------------------------------------
  //dequeue method
  //Removes first element in Array
  //Returns removed element
  //----------------------------------------------------------------
  public T dequeue() throws EmptyCollectionException{
    T temp=queue[front];//temp variable representing 1st element in array
    queue[front]=null;//removes 1st element
    front=(front+1)%queue.length;
    count--;
    return temp;//returns value of 1st element
  }
  
  //----------------------------------------------------------------
  //first method
  //returns 1st element in Array
  //----------------------------------------------------------------
  public T first() throws EmptyCollectionException {
    return queue[front];
  }
  
  //----------------------------------------------------------------
  //Returns size of Array
  //----------------------------------------------------------------
  public int size() {
    return count;
  }
  
  //----------------------------------------------------------------
  //isEmpty method
  //Returns boolean(true if Array is empty, false if not)
  //----------------------------------------------------------------
  public boolean isEmpty() {
    return count==0;
  }
  
  //----------------------------------------------------------------
  //toString method
  //Returns a String representation of the Array
  //----------------------------------------------------------------
  public String toString() {
    String result="";
    int temp=count;//temp variable to represent number of elements in Array
    
    //starts at front
    //uses temp to determine when to stop
    for(int i=front;temp>0;i=(i+1)%queue.length) {
      result+=(queue[i]+"\n");
      temp--;//decrements temp
    }
    return result;
  }
  
  //----------------------------------------------------------------
  //toString method
  //Takes a String parameter that is used to seperate different parts
  //of the Queue
  //Returns a String representation of the Array
  //----------------------------------------------------------------
  public String toString(String s) {
    String result="";
    int temp=count;//temp variable to represent number of elements in Array
    
    //starts at front
    //uses temp to determine when to stop
    for(int i=front;temp>1;i=(i+1)%queue.length) {
      result+=(queue[i]+s);
      temp--;//decrements temp
    }
    result+=(queue[(front+count-1)%queue.length]);
    return result;
  }
  
}