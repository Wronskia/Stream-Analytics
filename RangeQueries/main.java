/**
 * Created by Yassine on 19.03.17.
 */

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

public class task3 {
    public static void main(String[] args) throws IOException {
        rangeBF bf = new rangeBF(0.1);

        //Reading both files
        BufferedReader TSVFile = new BufferedReader(new FileReader("file1.tsv"));
        BufferedReader TXTFile = new BufferedReader(new FileReader("task3_queries.txt"));
        BufferedWriter out3 = new BufferedWriter(new FileWriter("out3.txt"));

        String queries_row;
        String requests_row;

        while ((requests_row = TSVFile.readLine()) != null) {
            String[] parts = requests_row.split("\t");
            //tab2 will consist of an array of string for each part of the request IP
            String[] tab2 = parts[0].split("\\.");
            //For sake of unicity, we store the IP number in 256 base
            long ip_request= (long) (Long.parseLong(tab2[0])*Math.pow(256,3)+Long.parseLong(tab2[1])*Math.pow(256,2)+Long.parseLong(tab2[2])*256+ Long.parseLong(tab2[3]));
            bf.insertValue(ip_request);

        }
        while ((queries_row = TXTFile.readLine()) != null) {
            if(queries_row.length()>0) {
                String[] query = queries_row.split("\t");
                String[] tab = query[0].split("\\.");
                String[] tab1 = query[1].split("\\.");
                long l= (long) (Long.parseLong(tab[0])*Math.pow(256,3)+Long.parseLong(tab[1])*Math.pow(256,2)+Long.parseLong(tab[2])*256+ Long.parseLong(tab[3]));
                long r= (long) (Long.parseLong(tab1[0])*Math.pow(256,3)+Long.parseLong(tab1[1])*Math.pow(256,2)+Long.parseLong(tab1[2])*256+ Long.parseLong(tab1[3]));

                if(bf.existsInRange(l,r)) {
                    out3.write("1"+",");
                }
                else{
                    out3.write("0"+",");

                }

                System.out.println(bf.existsInRange(l,r));
            }
        }
        TSVFile.close();
        TXTFile.close();
        out3.close();

    }
}