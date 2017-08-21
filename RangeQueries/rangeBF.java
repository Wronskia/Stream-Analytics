import java.util.Random;

import static java.lang.Boolean.TRUE;


/**
 *
 */
public class rangeBF {

    double pr;
    boolean[][] bloom_filters = new boolean[32][];
    int m_bloom;

    public rangeBF(double pr)
    {

        this.pr = pr;
        int n=400000;
        this.m_bloom = (int) ((-n*Math.log(pr))/(Math.pow(Math.log(2.0),2)));
        for(int i=0;i<32;++i){
            if(i<=13) {
                bloom_filters[i] = new boolean[m_bloom];
            }
            else{
                n=(int)(400000.0/(Math.pow(2,i-13)));
                bloom_filters[i] = new boolean[(int) ((-n*Math.log(pr))/(Math.pow(Math.log(2.0),2)))];
            }
         }
    }

    void insertValue(long key){

        int n=400000;
        int  k_bloom;
        for(int i=0;i<32;++i){

            Random r = new Random(key);


            if(i<=13) {
                k_bloom = (int) (Math.log(2.0) * (m_bloom / n));
                for (int j = 0; j < k_bloom; ++j) {
                    bloom_filters[i][r.nextInt(m_bloom)]= true;

                }
            }
            else{
                n=(int)(400000.0/(Math.pow(2,i-13)));
                k_bloom = (int) (Math.log(2.0) * (bloom_filters[i].length / n));
                for (int j = 0; j < k_bloom; ++j) {
                    bloom_filters[i][r.nextInt(bloom_filters[i].length)]= true;
                }
            }
            key = key/2;
        }
    }

    boolean existsInRange(long l, long r){

        int n=400000;
        for(int i=0;i<32 && l<=r;++i)
        {
            if(r%2==0)
            {
                Random random = new Random(r);
                int  k_bloom;
                boolean b=true;
                if(i<=13) {
                    k_bloom = (int) (Math.log(2.0) * (m_bloom / n));
                    for (int j = 0; j < k_bloom; ++j)
                    {
                        if (!bloom_filters[i][random.nextInt(m_bloom)])
                        {
                            b=false;
                        }
                    }
                    if(b) {
                        return true;
                    }
                }
                else{
                    n=(int)(400000.0/(Math.pow(2,i-13)));
                    k_bloom = (int) (Math.log(2.0) * (bloom_filters[i].length / n));
                    for (int j = 0; j < k_bloom; ++j)
                    {
                        if (!bloom_filters[i][random.nextInt(bloom_filters[i].length)])
                        {
                            b=false;
                        }
                    }
                    if(b) {
                        return true;
                    }
                }
                    r=r-1;
            }


            if(l%2==1)
            {
                Random random = new Random(l);
                int  k_bloom;
                boolean b=true;
                if(i<=13) {
                    k_bloom = (int) (Math.log(2.0) * (m_bloom / n));
                    for (int j = 0; j < k_bloom; ++j)
                    {
                        if (!bloom_filters[i][random.nextInt(m_bloom)])
                        {
                            b=false;
                        }
                    }
                    if(b) {
                        return true;
                    }
                }
                else
                {
                    n=(int)(400000.0/(Math.pow(2,i-13)));
                    k_bloom = (int) (Math.log(2.0) * (bloom_filters[i].length) / n);
                    for (int j = 0; j < k_bloom; ++j)
                    {
                        if (!bloom_filters[i][random.nextInt(bloom_filters[i].length)])
                        {
                            b=false;
                        }
                    }
                    if(b)
                    {
                        return true;
                    }
                }
                l=l+1;
            }
            l=l/2;
            r=r/2;
        }
        return false;
    }
}