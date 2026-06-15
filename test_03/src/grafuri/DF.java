package grafuri;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class DF {
    static PrintWriter printWriter;
    static int[] vizitat;
    static int n;

    public static void main(String[] args) {
        String caleFisierIn = "data/graf/in.txt";
        String caleFisierOut = "data/graf/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            printWriter = writer;

            n = sc.nextInt();
            int m = sc.nextInt();

            int[][] a = new int[n][n];
            for (int i = 0; i < m; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                a[u][v] = 1;
                a[v][u] = 1;
            }

            int start = sc.nextInt();

            vizitat = new int[n];
            for (int i = 0; i < n; i++) {
                vizitat[i] = 0;
            }

            DF(a, start);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DF(int[][] a, int v) {
        vizitat[v] = 1;
        printWriter.println(v);

        for (int j = 0; j < n; j++) {
            if (a[v][j] == 1 && vizitat[j] == 0) {
                DF(a, j);
            }
        }
    }
}