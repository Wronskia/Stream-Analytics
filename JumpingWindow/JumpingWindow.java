import java.util.*;

public class JumpingWindow {

    int windowSizeW;
    double epsilon;
    ArrayList<Integer> window = new ArrayList<>();
    int subwindowsize;


    public JumpingWindow(int windowSizeW, double epsilon) {
        this.epsilon = epsilon;
        this.windowSizeW = windowSizeW;

        //computing W1 the size of the subwindow
        for (int i = (int) Math.floor(epsilon * windowSizeW * 2); i >= 0; i--)

            if (windowSizeW % i == 0) {
                subwindowsize = i;
                break;
            }
    }

    /**
     * This Methods adds the IPs until reaching the maximum size which is W+W1
     * And then liberate the oldest subwindow W1
     */
    void insertEvent(int srcIP) {
        window.add(srcIP);
        if (window.size() == windowSizeW + subwindowsize) {
            window.subList(0, subwindowsize).clear();
        }

    }


    /**
     * This Method returns the estimation of the frequency based on
     * what we discussed in question 1
     * based on the latest queryWindowSizeW1 querys
     */
    int getFreqEstimation(int srcIP, int queryWindowSizeW1) {
        int counter = 0;
        int maxi = window.size() / subwindowsize;
        if (window.size()%subwindowsize!=0)
        {
            maxi = maxi - 1;
        }
        for (int i = (int) Math.floor((window.size() - queryWindowSizeW1) / subwindowsize); i < maxi; ++i) {

            double subcounter = 0.;
            for (int j = 0; j < subwindowsize; ++j) {

                if (srcIP == window.get(i * subwindowsize + j)) {
                    if (i == (int) Math.floor((window.size() - queryWindowSizeW1) / subwindowsize)) {
                        if (window.size() % subwindowsize == 0) {
                            subcounter += 1.;
                        } else {
                            subcounter += 0.5;
                        }
                    } else {
                        subcounter += 1.;
                    }
                }
            }
            counter += (int) subcounter;

        }

        if (window.size()!=0)
        {
            for (int i = windowSizeW; i < window.size(); i++)
            {
                if (srcIP == window.get(i))
                {
                    counter += 1;
                }
            }
        }
        return counter;
    }

    /**
     * This Method returns the estimation of the frequency based on
     * what we discussed in question 1
     * based on the latest windowSizeW querys
     */

    int getFreqEstimation(int srcIP) {
        int counter = 0;
        for (int i = 0; i < window.size() / subwindowsize; ++i) {
            double subcounter = 0.;
            int a;
            if (i < window.size() / subwindowsize) {
                a = subwindowsize;
            } else {
                a = window.size()%subwindowsize;
            }
            for (int j = 0; j < a; j++) {
                //System.out.println(i * subwindowsize + j);
                if (srcIP == window.get(i * subwindowsize + j)) {
                    if (i == 0) {
                        //System.out.println(window.size() % subwindowsize);
                        if (window.size() % subwindowsize == 0) {
                            subcounter += 1.;
                        } else {
                            subcounter += 0.5;
                        }
                    } else {
                        subcounter += 1.;
                    }
                }
            }
            counter += (int) subcounter;
        }

        return counter;
    }

    /**
     * This Method consist of a sanity check
     * by getting the exact values
     */

    int getRealValue(int srcIP, int query_size) {

        int counter=0;
        int count = 0;
        int j = window.size()-1;
        int a = query_size;
        if(query_size==0) {
            a = windowSizeW;
        }
       while (count < a)
       {
            if(srcIP==window.get(j)){
                counter+=1;
            }
            j -= 1;
            count += 1;

        }
        return counter;
    }

}