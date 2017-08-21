/**
 * Created by Yassine on 18.03.17.
 */

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

/**
In this Main of task2, we are going to read the query and the request files, fill
the bloom filter and the count-min sketch and get the frequency of the IPs given by
the query.
 */
public class task2 {
        public static void main(String[] args) throws IOException {

            //Instantiate the structure
            betterFrequencyEstimator window = new betterFrequencyEstimator(100000000,0.1F,0.01F,0.9F);

            //Reading both files
            BufferedReader TSVFile = new BufferedReader(new FileReader("file1.tsv"));
            BufferedReader TXTFile = new BufferedReader(new FileReader("task2_queries.txt"));
            BufferedWriter out2 = new BufferedWriter(new FileWriter("out2.txt"));


            String queries_row;
            String requests_row;
            int time_counter=0;


            while ((queries_row = TXTFile.readLine()) != null) {

                if(queries_row.length()>0) {

                    // Storing the time and the ip of the query
                    String[] query = queries_row.split("\t");
                    int time_query = Integer.parseInt(query[0]);
                    //tab will consist of an array of string for each part of the query IP
                    String[] tab = query[1].split("\\.");
                    //For sake of unicity, we store the IP number in 256 base
                    long ip_query= (long) (Long.parseLong(tab[0])*Math.pow(256,3)+Long.parseLong(tab[1])*Math.pow(256,2)+Long.parseLong(tab[2])*256+ Long.parseLong(tab[3]));
                    // we then read line by line the requests IP file
                    while ((requests_row = TSVFile.readLine()) != null) {
                        time_counter += 1;
                        String[] parts = requests_row.split("\t");
                        //tab2 will consist of an array of string for each part of the request IP
                        String[] tab2 = parts[0].split("\\.");
                        //For sake of unicity, we store the IP number in 256 base
                        long ip_request= (long) (Long.parseLong(tab2[0])*Math.pow(256,3)+Long.parseLong(tab2[1])*Math.pow(256,2)+Long.parseLong(tab2[2])*256+ Long.parseLong(tab2[3]));
                        //we add the IP request to our bloom filter and count-min sketch
                        window.addArrival(ip_request);
                        //when the time counter reach the time of the query, we get the frequency of the query IP
                        if (time_counter == time_query) {
                            int real = window.getFreqEstimation(ip_query);



                            out2.write(real+",");

                            //System.out.println(real);
                            break;
                        }
                    }
                }
            }
            TSVFile.close();
            TXTFile.close();
            out2.close();
        }

}