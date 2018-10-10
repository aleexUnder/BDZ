import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class FrontEnd {


    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("1. Тест\n2. Программа\n");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        if (input == 1){
            test();
        }
        else{
        new Paint();}


}

    public static void test()throws IOException, InterruptedException{
        int t=10;
        System.out.println("Тест "+t+" раз");
        ArrayList<Integer> times = new ArrayList<Integer>();
        for (int i=0;i<t;i++){
            long startTime = System.currentTimeMillis();
            new Paint();
            times.add((int)(System.currentTimeMillis()-startTime));}

        int sum = 0;
        for (int i=0;i<times.size();i++) {
            sum=sum+times.get(i);
        }
        double avg = sum/times.size();
        System.out.println("Avg time: "+(int)avg+" millis");
    }
}
