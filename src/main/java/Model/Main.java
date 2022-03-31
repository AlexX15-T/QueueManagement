package Model;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        LogWritter l1 = new LogWritter(new int[]{4, 2, 30, 2, 4}, 60, 2);
        //l1.writeInLogs("logFileCase1.txt");

        LogWritter l2 = new LogWritter(new int[]{50, 2, 40, 1, 7}, 60, 5);
        l2.writeInLogs("logFileCase2.txt");

        LogWritter l3 = new LogWritter(new int[]{1000, 10, 100, 3, 9}, 200, 20);
        //l3.writeInLogs("logFileCase3.txt");

    }


}
