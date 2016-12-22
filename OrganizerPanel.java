/** 
 * FILE NAME: OrganizerPanel.java
 * WHO: Midori Yang
 * WHAT: Sets up the OrganizerPanel as well as the createCourse window for entering
 * new courses
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import structures.*;

public class OrganizerPanel extends JPanel{
  
  //global vars so that everything is inter-accessible
  private JLabel creditLabel;
  private JFrame createCourse;
  private JButton courseButton, exportButton, saveButton, cancelButton;
  private JTable table;
  private JTextField code, name, notes;
  private JComboBox timeOpts, creditOpts;
  private JPanel list, schedule, buttons, courseEnter, days;
  private String[] courses, columnNames, rowNames;
  private int[][][] times;
  private GridBagConstraints c, mainC, radioC;
  private Schedule mainSchedule;
  
  // Constructor
  public OrganizerPanel() {
    
    mainSchedule = new Schedule();
    
    String[] c1Days = {"Mon","Tues","Thurs","Fri"};
    Course c1 = new Course("JPN 201","Intermediate Japanese","11:10 - 12:20", c1Days, 1.0);
    String[] c2Days = {"Tues","Fri"};
    Course c2 = new Course("CS 230","Data Structures","9:50 - 11:00",c2Days, 1.0);
    String[] c3Days = {"Tues","Fri"};
    Course c3 = new Course("CS 240","Introduction to Machine Organization","1:30 - 2:40",c3Days, 1.0);
    String[] c4Days = {"Mon","Thurs"};
    Course c4 = new Course("CS 320","Tangible User Interfaces","1:30 - 2:40", c4Days, 1.0);
    Course c5 = new Course("CS Test","Test","1:30 - 2:40", c4Days, 1.0);
    Course c6 = new Course("CS Test2","Test2","1:30 - 2:40", c4Days, 1.0);
    
    mainSchedule.addCourse(c1);
    mainSchedule.addCourse(c2);
    mainSchedule.addCourse(c3);
    mainSchedule.addCourse(c4);
    mainSchedule.addCourse(c5);
    mainSchedule.addCourse(c6);
    
    columnNames = new String[] {"","Mon", "Tues", "Wed", "Thurs", "Fri"};
    rowNames = new String[] {"8:30 - 9:40","9:50 - 11:00","11:10 - 12:20",
      "12:20 - 1:20", "1:30 - 2:40","2:50 - 4:00"};
    
    c = new GridBagConstraints();
    mainC = new GridBagConstraints();
    
    setLayout(new GridBagLayout());
    setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    
    creditLabel= new JLabel("Total credits: " + mainSchedule.sumVisible());
    
    mainC.gridx = 1;
    mainC.gridy = 0;
    mainC.insets = new Insets(0,0,0,0);
    mainC.fill = GridBagConstraints.HORIZONTAL;
    mainC.weightx = 0.6;
    add(makeSchedule(), mainC);
    mainC.gridx = 0;
    mainC.gridy = 0;
    mainC.fill = GridBagConstraints.HORIZONTAL;
    mainC.insets = new Insets(0,0,0,15);
    mainC.weightx = 0.4;
    add(makeList(), mainC);
    mainC.gridx = 0;
    mainC.gridy = 1;
    mainC.fill = GridBagConstraints.NONE;
    mainC.weightx = 0.0;
    mainC.insets = new Insets(20,0,0,0);
    add(makeButtons(), mainC);
    mainC.gridx = 1;
    mainC.gridy = 1;
    mainC.weightx = 0.0;
    mainC.insets = new Insets(0,0,0,0);
    add(creditLabel, mainC);
    openCourseEnter();
  }
  
  /*--------------------------------------------------------------------
   * Course list related methods
   *--------------------------------------------------------------------*/
  //initialize list and panel
  private JPanel makeList(){
    list = new JPanel(new GridBagLayout());
    //list.setBorder(BorderFactory.createLineBorder(Color.black));
    list.setMinimumSize(new Dimension(500,200));
    radioC = new GridBagConstraints();
    String[] keys = mainSchedule.getAll();
    for(String key: keys){
      Course co = mainSchedule.getCourse(key);
      JCheckBox checkbox = new JCheckBox(co.getDeptNum() + ": " + co.getName());
      checkbox.addItemListener(new CheckBoxListener(co.getDeptNum()));
      checkbox.setSelected(true);
      radioC.gridx = 1;
      radioC.anchor = GridBagConstraints.FIRST_LINE_START;
      list.add(checkbox,radioC);
    }
    return list;
  }
  
  //private CheckBoxListener class that indicates to the table which
  //classes to show
  private class CheckBoxListener implements ItemListener{
    
    String label;
    
    //save the label of the checkbox
    public CheckBoxListener(String label){
      this.label = label;
    }
    
    public void itemStateChanged(ItemEvent e){
      
      //System.out.println("Checkbox: " + label);
      
      Course co = mainSchedule.getCourse(label);
      LinkedList<Meeting> meets = co.getMeetings();
      
      if (e.getStateChange() == ItemEvent.SELECTED){
        //System.out.println("selected");
        for(Meeting meet: meets){
          int row = java.util.Arrays.asList(rowNames).indexOf(meet.getTime());
          int col = java.util.Arrays.asList(columnNames).indexOf(meet.getDay());
          String currentVal = (String) table.getValueAt(row,col);
          
          //if the cell value is null there are no classes there yet
          //which means no conflicts
          if(currentVal==null){
            table.setValueAt("<html>" + label + "</html>", row, col);
            //System.out.println("Current val: " + table.getValueAt(row,col));
          }
          
          //if there is already a value in the cell, handle class conflicts
          else{
            String temp = currentVal.split("<html>")[1];
            temp = temp.split("</html>")[0];
            String[] codes = temp.split("<br>");
            String newVal = "<html>";
            for (int i=0; i<codes.length; i++){
              newVal += codes[i] + "<br>";
            }
            newVal += label + "</html>";
            
            //System.out.println("Setting value to " + newVal);
            table.setValueAt(newVal, row, col);
            updateRowHeights();
            
          }
        } 
        co.show();
        creditLabel.setText("Total credits: " + mainSchedule.sumVisible());
      }
      
      
      else if (e.getStateChange() == ItemEvent.DESELECTED){
        //System.out.println("deselected");
        for(Meeting meet: meets){
          int row = java.util.Arrays.asList(rowNames).indexOf(meet.getTime());
          int col = java.util.Arrays.asList(columnNames).indexOf(meet.getDay());
          String currentVal = (String) table.getValueAt(row,col);
          //System.out.println("Val in cell: " + currentVal);
          String temp = currentVal.split("<html>")[1];
          temp = temp.split("</html>")[0];
          //System.out.println(temp);
          String[] codes = temp.split("<br>");
          
          
          //if there is only one code in the cell, there are no conflicts to solve
          //can set cell to blank safely
          if(codes.length==1){
            //needs to be set to null to correspond with code in SELECTED
            table.setValueAt(null, row, col);
            //System.out.println("Current val: " + table.getValueAt(row,col));
          }
          
          //else, remove the class that was unchecked and update the cell
          else{
            ArrayList<String> codesList = new ArrayList<String>(Arrays.asList(codes));
            codesList.remove(label);
            String newVal = "<html>";
            for (int i=0; i<codesList.size(); i++){
              System.out.println(codesList.get(i));
              //if code is last only add code
              if(i==codesList.size()-1) {
                //System.out.println("doing 2");
                newVal += codesList.get(i);
                //for all other codes also add line break
              } else {
                //System.out.println("doing 3");
                newVal += codesList.get(i) + "<br>";
              }
            }
            newVal += "</html>";
            
            //System.out.println("Setting value to " + newVal);
            table.setValueAt(newVal, row, col);
            updateRowHeights();
          }
        } 
        co.hide();
        creditLabel.setText("Total credits: " + mainSchedule.sumVisible());
      }
    }
  }
  
  /*--------------------------------------------------------------------
   * Schedule related methods
   *--------------------------------------------------------------------*/
  //initialize table and panel
  private JPanel makeSchedule(){
    table = new JTable(new ScheduleTableModel());
    table.setDefaultRenderer(String.class, new ColorRenderer());
    table.getColumnModel().getColumn(0).setMinWidth(90);
    table.setGridColor(Color.black);
    
    schedule = new JPanel(new BorderLayout());
    schedule.add(table.getTableHeader(), BorderLayout.PAGE_START);
    schedule.add(table, BorderLayout.CENTER);
    schedule.setMinimumSize(new Dimension(table.getWidth(), table.getHeight()));
    return schedule;
  }
  
  
  private class ScheduleTableModel extends AbstractTableModel{
    
    private Object[][] data = new Object[rowNames.length][columnNames.length];
    
    //must be implemented
    public int getColumnCount() {
      return columnNames.length;
    }
    
    //must be implemented
    public int getRowCount() {
      return data.length;
    }
    
    public String getColumnName(int col) {
      return columnNames[col];
    }
    
    public Class<?> getColumnClass(int columnIndex) {
      return String.class;
    }
    
    //must be implemented
    public Object getValueAt(int row, int col) {
      if(col==0) return rowNames[row];
      else{return data[row][col];}
    }
    
    public void setValueAt(Object value, int row, int col) {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
    
    public boolean isCellEditable(int row, int col) {
      return false;
    }
    
    
  }
  
  
  
  static class ColorRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
      Component c = super.getTableCellRendererComponent(table, value, isSelected,
                                                        hasFocus, row, column);
      String val = (String) table.getValueAt(row,column);
      if(val != null && val.contains("<br>")){
        c.setForeground(Color.RED);
      }
      else{
        c.setForeground(Color.BLACK);
      }
      return c;
    }
  }
  
  
  private void updateRowHeights(){
    for (int row = 0; row < table.getRowCount(); row++){
      int rowHeight = table.getRowHeight();
      for (int column = 0; column < table.getColumnCount(); column++){
        Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
        rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
      }
      table.setRowHeight(row, rowHeight);
    }
  }
  
  
  
  /*--------------------------------------------------------------------
   * Course entry related methods
   *--------------------------------------------------------------------*/
  
  //makes the "Enter a course" and "Export to file" buttons
  private JPanel makeButtons(){
    buttons = new JPanel();
    courseButton = new JButton("Enter a course");
    exportButton = new JButton("Export to file");
    courseButton.addActionListener(new ButtonListener());
    exportButton.addActionListener(new ButtonListener());
    buttons.add(courseButton);
    buttons.add(exportButton);
    return buttons;
  }
  
  //makes the panel containing all the course info entry fields
  private JPanel makeCourseEnter(){
    JPanel courseEnter = new JPanel(new GridBagLayout());
    
    JLabel label1= new JLabel("Course department and number");
    label1.setMaximumSize(new Dimension(70,20));
    label1.setHorizontalAlignment(JLabel.RIGHT);
    c.gridx = 0;
    c.gridy = 0;
    c.anchor = GridBagConstraints.LINE_END;
    c.insets = new Insets(10,10,0,10);//top, left, bottom, right
    courseEnter.add(label1, c);
    
    JLabel label2= new JLabel("Course name");
    label2.setMaximumSize(new Dimension(70,20));
    label2.setHorizontalAlignment(JLabel.RIGHT);
    c.gridx = 0;
    c.gridy = 1;
    courseEnter.add(label2, c);
    
    JLabel label3= new JLabel("Days of the week");
    label3.setMaximumSize(new Dimension(70,20));
    label3.setHorizontalAlignment(JLabel.RIGHT);
    c.gridx = 0;
    c.gridy = 2;
    courseEnter.add(label3, c);
    
    JLabel label4= new JLabel("Time");
    label4.setMaximumSize(new Dimension(70,20));
    label4.setHorizontalAlignment(JLabel.RIGHT);
    c.gridx = 0;
    c.gridy = 3;
    courseEnter.add(label4, c);
    
    JLabel label5= new JLabel("Credits");
    label4.setMaximumSize(new Dimension(70,20));
    label4.setHorizontalAlignment(JLabel.RIGHT);
    c.gridx = 0;
    c.gridy = 4;
    courseEnter.add(label5, c);
    
    JLabel label6= new JLabel("Notes (optional)");
    label5.setMaximumSize(new Dimension(70,20));
    label5.setHorizontalAlignment(JLabel.RIGHT);
    c.gridx = 0;
    c.gridy = 5;
    c.insets = new Insets(10,10,10,10);//top, left, bottom, right
    courseEnter.add(label6, c);
    
    code = new JTextField("ex. CS 230", 20);
    c.gridx = 1;
    c.gridy = 0;
    c.insets = new Insets(10,5,0,10);//top, left, bottom, right
    c.anchor = GridBagConstraints.LINE_START;
    courseEnter.add(code, c);
    
    name = new JTextField("ex. Data Structures", 20);
    c.gridx = 1;
    c.gridy = 1;
    courseEnter.add(name, c);
    
    days = new JPanel(new FlowLayout(FlowLayout.CENTER));
    for(int i=1; i<columnNames.length; i++){
      days.add(new JCheckBox(columnNames[i]));
    }
    c.gridx = 1;
    c.gridy = 2;
    courseEnter.add(days, c);
    
    
    String[] timeStrings = {"","8:30 - 9:40","9:50 - 11:00","11:10 - 12:20",
      "12:20 - 1:20", "1:30 - 2:40","2:50 - 4:00"};
    timeOpts = new JComboBox(timeStrings);
    c.gridx = 1;
    c.gridy = 3;
    courseEnter.add(timeOpts, c);
    
    String[] creditStrings = {"", "1.0", "1.25"};
    creditOpts = new JComboBox(creditStrings);
    c.gridx = 1;
    c.gridy = 4;
    courseEnter.add(creditOpts, c);
    
    notes = new JTextField(20);
    c.gridx = 1;
    c.gridy = 5;
    courseEnter.add(notes, c);
    
    JPanel buttons = new JPanel(new GridBagLayout());
    GridBagConstraints c2 = new GridBagConstraints();
    saveButton = new JButton("Save");
    cancelButton = new JButton("Cancel");
    saveButton.addActionListener(new ButtonListener());
    cancelButton.addActionListener(new ButtonListener());
    c2.gridx = 0;
    c2.gridy = 0;
    c2.insets = new Insets(10,0,10,5);//top, left, bottom, right
    c2.anchor = GridBagConstraints.LINE_END;
    buttons.add(saveButton, c2);
    c2.gridx = 1;
    c2.gridy = 0;
    c2.insets = new Insets(10,5,10,0);//top, left, bottom, right
    c2.anchor = GridBagConstraints.LINE_START;
    buttons.add(cancelButton, c2);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 2;
    courseEnter.add(buttons, c);
    
    return courseEnter;
  }
  
  //makes the course entry JFrame upon init and hides it
  public void openCourseEnter(){ 
    createCourse = new JFrame("Enter a Course");
    createCourse.getContentPane().add(makeCourseEnter());
    createCourse.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    createCourse.setResizable(false);
    createCourse.pack();
    createCourse.setVisible(false);
    
  }
 
  
  //resets the info entry fields in the course entry JFrame
  private void resetCourseEnter(){
    code.setText("ex. CS 230");
    name.setText("ex. Data Structures");
    Component[] comps = days.getComponents();
    for(int i=0; i<comps.length; i++){
      JCheckBox comp = (JCheckBox) comps[i];
      comp.setSelected(false);
    }
    timeOpts.setSelectedIndex(0);
    notes.setText("");
  }
  
  private class ButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      if(e.getSource()==courseButton){
        resetCourseEnter();
        createCourse.setVisible(true);
      }
      if(e.getSource()==exportButton){
        String filename = JOptionPane.showInputDialog(null,"Please enter a file name:");
        mainSchedule.saveVisible(filename + ".txt");
      }
      if(e.getSource()==saveButton){
        String codeStr = code.getText();
        String nameStr = name.getText();
        
        //get an exactly-sized list of which days were selected (dayStrs)
        LinkedList<String> dayStrsTemp = new LinkedList<String>();
        Component[] comps = days.getComponents();
        for(int i=0; i<comps.length; i++){
          JCheckBox comp = (JCheckBox) comps[i];
          if(comp.isSelected())
            dayStrsTemp.add(comp.getLabel());
        }
        String[] dayStrs = new String[dayStrsTemp.size()];
        for(int j=0; j<dayStrsTemp.size(); j++){
          dayStrs[j] = dayStrsTemp.get(j);
        }
        
        String timeStr = String.valueOf(timeOpts.getSelectedItem());
        String notesStr = notes.getText();
        double credit = 0;
        try{
          credit = Double.parseDouble(String.valueOf(creditOpts.getSelectedItem()));
        }
        catch (NumberFormatException f){}
        
        //don't let person make course while necessary information is not inputted
        //notes are optional
        if (codeStr.equals("") || codeStr.equals("ex. CS 230") ||
            nameStr.equals("") || nameStr.equals("ex. Data Structures") ||
            timeStr.equals("") ||
            dayStrs.length==0 || credit==0) {
          JOptionPane.showMessageDialog(null,"You did not enter one or more of the fields.");
        }
        
        else{
          Course course = new Course(codeStr, nameStr, timeStr, dayStrs, credit, notesStr);
          System.out.println(course);
          mainSchedule.addCourse(course);
          
          JCheckBox checkbox = new JCheckBox(course.getDeptNum() + ": " + course.getName());
          checkbox.addItemListener(new CheckBoxListener(course.getDeptNum()));
          checkbox.setSelected(true);
          radioC.gridx = 1;
          radioC.anchor = GridBagConstraints.FIRST_LINE_START;
          list.add(checkbox,radioC);
          
          createCourse.setVisible(false);
        }
        
      }
      if(e.getSource()==cancelButton){
        createCourse.setVisible(false);
      }
    }
  }
  
  
}
