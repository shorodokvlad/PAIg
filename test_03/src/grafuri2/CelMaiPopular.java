package grafuri2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class CelMaiPopular {
    public static void main(String[] args) {
        String caleFisierIn  = "data/cel_mai_popular/in.txt";
        String caleFisierOut = "data/cel_mai_popular/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            int n = sc.nextInt();
            sc.nextLine();

            int[] popularitate = new int[n];
            for (int i = 0; i < n; i++) {
                String linie = sc.nextLine().trim();
                for (String val : linie.split("\\s+"))
                    popularitate[Integer.parseInt(val)]++;
            }

            int maxPop = 0;
            for (int i = 0; i < n; i++)
                if (popularitate[i] > maxPop)
                    maxPop = popularitate[i];

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (popularitate[i] == maxPop) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(i);
                }
            }
            writer.println(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}