package grafuri2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class NoduriIzolate {
    public static void main(String[] args) {
        String caleFisierIn  = "data/noduri/in.txt";
        String caleFisierOut = "data/noduri/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            int n = sc.nextInt();
            int m = sc.nextInt();

            int[] grad = new int[n];
            for (int i = 0; i < m; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                grad[u]++;
                grad[v]++;
            }

            for (int i = 0; i < n; i++)
                if (grad[i] == 0)
                    writer.println(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}