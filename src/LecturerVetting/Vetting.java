package LecturerVetting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 *
 * @author Christiaan Bouwer VVF6HCS19
 */
public class Vetting {

    int[][] rates;
    String teacherName;

    //Constructor to set the default values of the rates array and teacher name
    public Vetting(String name, int[][] rates) {
        this.rates = rates;
        teacherName = name;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }

    //Method to invoke other methods
    public void processRates() {
        getTotal();
        getMax();
        getMin();
        rateFrequency();
    }

    //Displays the content of the lecturer table in the database
    //Calls and displays the array of totals, max and min
    public void outputRates() throws SQLException {

        System.out.println("The rates are:" + Arrays.deepToString(rates));

        TestVetting obj = new TestVetting();

        Statement state = obj.con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM lecturer");
        int numCols = res.getMetaData().getColumnCount();
        while (res.next()) {
            for (int i = 1; i <= numCols; i++) {
                System.out.println(res.getString(i));
            }
        }

        System.out.println(Arrays.toString(getTotal())
                + "\n\nThe last number in the total array is " + getTotal()[4]
                + "\nGood job on a high score of " + getMax()
                + "\nYou could improve on the score of " + getMin());
    }

    //Nested forloop to itterate rates array, sum each value per assessment
    //Store each assessment value in total array
    private int[] getTotal() {
        int[] total = new int[5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < rates.length; j++) {
                total[i] += rates[j][i];
            }
        }
        return total;
    }

    //Loop to itterate through total array comparing values to find max
    private int getMax() {
        int[] arr = getTotal();
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        return max;
    }

    //Loop to itterate through total array comparing values to find min
    private int getMin() {
        int[] arr = getTotal();
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    //Tally rates by itterating through nested forloop
    //Frequencies stored in freq array
    private void rateFrequency() {
        int[] freq = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < rates.length; i++) {
            for (int j = 0; j < 5; j++) {
                freq[rates[i][j]]++;
            }
        }

        System.out.println("Overall rate distribution:");
        //Nested forloop to itterate through freq array printing * per frequency
        for (int n = 0; n < freq.length; n++) {
            System.out.print(n + ": ");
            for (int x = 0; x < freq[n]; x++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
