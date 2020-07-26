package LecturerVetting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Christiaan Bouwer VVF6HCS19
 */
public class TestVetting {

    //instance variables used within methods
    int[][] rates;
    int numbStudents = 0;
    Connection con;

    //creates connection to database using ucanaccess, locating database in my directory
    //Calls the insertToDatabase method
    private void dbConnection() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection("jdbc:ucanaccess://"
                    + "C://Users//Christiaan//Documents//NetBeansProjects//Question 2//Assessment.accdb");
            insertToDatabase();
            con.close();
        } catch (ClassNotFoundException | SQLException exp) {
            exp.printStackTrace();
        }
    }

    //creates statement to delete table contents and insert rates array data into table
    //First calls the enterRates method
    private void insertToDatabase() throws SQLException {

        enterRates();

        try (Statement state = con.createStatement()) {
            state.execute(("TRUNCATE lecturer"));
            
            for (int i = 0; i < numbStudents; i++) {
                state.executeUpdate("INSERT INTO lecturer"
                        + "VALUES (\'" + (i + 1) + "\',\'"
                        + rates[i][0] + "\',\'"
                        + rates[i][1] + "\',\'"
                        + rates[i][2] + "\',\'"
                        + rates[i][3] + "\',\'"
                        + rates[i][4] + "\')");
            }
        }
    }

    public int[][] enterRates() {

        //Ask user for number of students then sets rates array size accordingly
        while (numbStudents < 1) {
            numbStudents = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of students:"));
        }
        rates = new int[numbStudents][5];

        //User enters rating of students then stored in rates array
        for (int row = 0; row < numbStudents; row++) {
            for (int col = 0; col < 5; col++) {
                int rating = Integer.parseInt(JOptionPane.showInputDialog("Student " + (row + 1) + "- Assessment " + (col + 1)
                        + "\nEnter the rate between 0 & 10"));
                //Test if users input is between 0 nad 10
                while (rating > 10 || rating < 0) {
                    rating = Integer.parseInt(JOptionPane.showInputDialog("Student " + (row + 1) + "- Assessment " + (col + 1)
                            + "\nRating must be between 0 & 10!"));
                }
                rates[row][col] = rating;
            }
        }

        return rates;
    }

    //displays contents of database and rates array
    //creates an instance of class vetting to invoke the display method and processRates method
    private void show() throws SQLException {
        Vetting obj = new Vetting("Linda", rates);
        obj.outputRates();
        obj.processRates();
    }

}
