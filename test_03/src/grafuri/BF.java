package grafuri;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BF {
    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/graf/in.txt";
        String caleFisierOut = "data/graf/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            printWriter = writer;

            int n = sc.nextInt();
            int m = sc.nextInt();

            int[][] a = new int[n][n];
            for (int i = 0; i < m; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                a[u][v] = 1;
                a[v][u] = 1;
            }

            int start = sc.nextInt();
            BF(a, n, start);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void BF(int[][] a, int n, int v) {
        int[] vizitat = new int[n];
        for (int i = 0; i < n; i++) {
            vizitat[i] = 0;
        }

        Queue<Integer> coada = new LinkedList<>();
        coada.add(v);
        vizitat[v] = 1;

        while (!coada.isEmpty()) {
            int p = coada.poll();
            printWriter.println(p);

            for (int j = 0; j < n; j++) {
                if (a[p][j] == 1 && vizitat[j] == 0) {
                    coada.add(j);
                    vizitat[j] = 1;
                }
            }
        }
    }
}