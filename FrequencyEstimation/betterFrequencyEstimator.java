import java.util.*;
import java.util.Random;
import static java.lang.Boolean.TRUE;


public class betterFrequencyEstimator {
    /**
    m_bloom : The size of the bloom filter array
    k_bloom : The number of hash functions over the bloom filter
    m_countmin : The number of columns for the countmin sketch
    k_countmin : The number of hash functions over the countmin sketch (number of lines)
    float pr1 : probability of false positive for the bloom filter
    float 1-pr2 : probability of false positive for the countmin sketch
    countmin : The table for the countmin sketch
    bloom : The bloom filter array
     */
    int availableSpace;
    float pr1;
    float epsilon;
    float pr2;
    int m_bloom;
    int k_bloom;
    int m_countmin;
    int k_countmin;
    int[][] countmin;
    Boolean[] bloom;
    int countr;


    public betterFrequencyEstimator(int availableSpace, float pr1, float epsilon, float pr2)
    {
        countr = 0;
        //Checking if we have available space
        if(m_bloom/4+m_countmin*k_countmin <= availableSpace)
        {
            this.epsilon = epsilon;
            this.pr1 = pr1;
            this.pr2 = pr2;
            this.availableSpace = availableSpace;
            int n=400000;
            this.m_bloom = (int) ((-n*Math.log(pr1))/(Math.pow(Math.log(2.0),2)));
            this.m_countmin=(int) Math.ceil(Math.exp(1.0)/epsilon);
            this.k_bloom = (int) (Math.log(2.0)*(m_bloom/n));
            this.k_countmin = (int) Math.ceil(Math.log(1/(1-pr2)));
            this.bloom = new Boolean[m_bloom];
            Arrays.fill(bloom, false);
            this.countmin = new int[k_countmin][m_countmin];

        }
        else
        {
            throw new OutOfMemoryError();
        }
    }


    void addArrival(long key) {

        //if we had to create hashs function  :
        //int[] hashs_countmin = new int[k_countmin];
        //hashs_countmin[i] = r.nextInt(m_countmin);
        //but we do it directly here


        //Setting the seed
        Random r = new Random(key);
        //Filling the bloom filter
        for (int i = 0; i < k_bloom; ++i) {
            bloom[r.nextInt(m_bloom)]= true;
        }
        //Setting the seed for the countmin sketch and filling it
        Random r1 = new Random(key);
        for (int i = 0; i < k_countmin; ++i) {
            countmin[i][r1.nextInt(m_countmin)]+=1;
        }

    }

    int getFreqEstimation(long key){
        /**
         *We first check if the IP is not contained in our set and return 0 if it is not
         *If it is, we go have a look to the countmin sketch
         */
        System.out.println(m_countmin);

        Random r = new Random(key);
        for (int i = 0; i < k_bloom; ++i) {
            if (!bloom[r.nextInt(m_bloom)])
            {
                return 0;
            }
        }
            Random r1 = new Random(key);
            int minimum=0;
            for (int i = 0; i < k_countmin; ++i) {
                int value=countmin[i][r1.nextInt(m_countmin)];
                if(i==0){
                    minimum=value;
                }
                if(value<minimum){
                    minimum=value;
                }
            }
            return minimum;
    }
}