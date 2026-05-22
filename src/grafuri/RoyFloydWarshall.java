package grafuri;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class RoyFloydWarshall {
    public static final int INF = 1000000;
    private static int[][] drum;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File("data/roy_floyd/in.txt"));
             PrintWriter pw = new PrintWriter(new File("data/roy_floyd/out.txt"))) {

            if (sc.hasNextInt()) {
                int n = sc.nextInt();
                int m = sc.nextInt();

                int[][] c = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        c[i][j] = (i == j) ? 0 : INF;
                    }
                }

                for (int i = 0; i < m; i++) {
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    int w = sc.nextInt();
                    c[u][v] = c[v][u] = w;
                }

                int[][] d = RoyFloyd(c, n);

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        pw.print(d[i][j] + (j == n - 1 ? "" : " "));
                    }
                    pw.println();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[][] RoyFloyd(int[][] c, int n) {
        int[][] d = new int[n][n];
        drum = new int[n][n];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                d[i][j] = c[i][j];
                if (i != j && c[i][j] != INF) {
                    drum[i][j] = i;
                } else {
                    drum[i][j] = -1;
                }
            }
        }


        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && i != k && k != j) {
                        if (d[i][j] > d[i][k] + d[k][j]) {
                            d[i][j] = d[i][k] + d[k][j];
                            drum[i][j] = drum[k][j];
                        }
                    }
                }
            }
        }
        return d;
    }
    public static void TiparesteDrum(int i, int j) {
        if (drum[i][j] == i) {
            System.out.print(i + " " + j + " ");
        } else if (drum[i][j] != -1) {
            TiparesteDrum(i, drum[i][j]);
            TiparesteDrum(drum[i][j], j);
        } else {
            System.out.print("Nu exista drum.");
        }
    }
}