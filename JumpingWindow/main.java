/**
 * Created by Yassine on 10.03.17.
 */

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

public class task1 {
    public static void main(String[] args) throws IOException {

        /**
         * Reading the files and instantiating the structure
         */
        JumpingWindow win = new JumpingWindow(10000 ,0.01);
        BufferedReader TSVFile = new BufferedReader(new FileReader("file1.tsv"));
        BufferedReader TXTFile = new BufferedReader(new FileReader("task1_queries.txt"));
        BufferedWriter out = new BufferedWriter(new FileWriter("out1.txt"));


        String queries_row;
        String requests_row;
        int time_counter=0;

        //Vector of errors for the sanity check
        Vector<Integer> err = new Vector();

        while ((queries_row = TXTFile.readLine()) != null) {
            if(queries_row.length()>0) {

                //split by tabulation
                String[] query = queries_row.split("\t");

                //Keeping the time, the ip and the size of the window
                int time_query = Integer.parseInt(query[0]);
                int ip_query = Integer.parseInt(query[1].split("\\.")[0]);
                int size_window = Integer.parseInt(query[2]);

                while ((requests_row = TSVFile.readLine()) != null) {

                    time_counter += 1;
                    String[] parts = requests_row.split("\t");

                    //Taking the x in x.**.**.** in the requests file and adding it the the window
                    int ip_request = Integer.parseInt(parts[0].split("\\.")[0]);
                    win.insertEvent(ip_request);

                    if (time_counter == time_query) {
                        int real = win.getRealValue(ip_query,size_window);
                        int estim;
                        if (size_window == 0) {
                            estim =win.getFreqEstimation(ip_query);
                            out.write(estim+",");
                        } else {
                            estim = win.getFreqEstimation(ip_query, size_window);
                            out.write( estim + ",");
                        }
                        err.add(Math.abs(real-estim));
                        break;
                    }
                }
            }
        }
        //Printing the maximal error
        System.out.println(Collections.max(err));
        //Closing files
        TSVFile.close();
        TXTFile.close();
        out.close();

    }
}