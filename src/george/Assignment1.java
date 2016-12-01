package george;

/* author: George Young
 * Date Created: September 16th 2016
 * Last Modified: September 30th 2016
 *
 */
import java.io.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author gy674
 */
public class Assignment1 {

    public static void main(String[] args) throws Exception {
        
        //Decleared Variables here
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<studentresult> studentsresult = new ArrayList<>();
        ArrayList<Object> params = new ArrayList();
        String sql = "SELECT * FROM student";
        String sql1 = "Select * from coursestudent where courseId= ?";
        String course[] = {"PROG10000", "DBAS20000", "MATH30000"};

        JdbcHelper jdbc = new JdbcHelper();
        //Connecting to database with the arguments
        Boolean a = jdbc.connect("jdbc:mysql://localhost:3306/ejd", "root", "674600");

        if (a) {// Start if Database is connected

            ResultSet result = jdbc.query(sql, null);
            //Will start the loop if the result set returned anything but null
            if (result != null) {
                
                while (result.next()) {//loops until all students are in the arraylist

                    String id = result.getString("id");
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");

                    Student student = new Student(id, firstName, lastName);
                    students.add(student);
                }
                
                //Starting the loop to print out as many tables as it needs to
                //for this there are 3 courses so 3 times
                 
                for (String course1 : course) {
                    
                    studentsresult.clear();
                    params.clear();
                    params.add(course1);
                    result = jdbc.query(sql1, params);
                    if (result != null) {
                        //This part is to grab all of the Student Id which are enrolled in the course
                        while (result.next()) {
                            String sid = result.getString("studentId");
                            studentresult sres = new studentresult(sid);
                            studentsresult.add(sres);
                        }
                    }
                    
                    //Start of Table
                    System.out.println("***  Students in " + course1 + " Course ***");
                    System.out.println("+=========+============+============+");
                    System.out.println("|ID       | First Name |  Last Name |");
                    for (int count = 0; count < studentsresult.size(); count++) {
                        
                        //loops through the Student Id which are part of the course

                        for (int coun = 0; coun < students.size(); coun++) {
                            
                            //loops through all of the students until match with student in course
                            String b = studentsresult.get(count).getId();
                            String c = students.get(coun).getId();

                            if (c.equals(b)) {
                                //once matched it will get the name of the student with that matching id
                                String fn = students.get(coun).getFirstName();
                                String ln = students.get(coun).getLastName();
                                //Printf is used to create a table format
                                System.out.printf("|%-9s|%-12s|%-12s|\n", c, fn, ln);
                                break;
                                //breaks out of current for loop because it is already found
                            }

                        }

                    }
                    //END of table
                    System.out.println("+=========+============+============+");
                    System.out.println("");
                }
            }
        } else {
            System.out.println("FAILED TO CONNECT TO DATABASE");

        }

    }

}
