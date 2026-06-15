package grafuri;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Dijkstra {
    public static final int INF = 1000000;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File("data/dijkstra/in.txt"));
             PrintWriter pw = new PrintWriter(new File("data/dijkstra/out.txt"))) {

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

                int[] distMin = new int[n];
                int[] tata = new int[n];

                int nodStart = 2;

                DijkstraAlg(c, nodStart, distMin, tata);

                for (int i = 0; i < n; i++) {
                    pw.print(distMin[i] + (i == n - 1 ? "" : " "));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DijkstraAlg(int[][] c, int nod, int[] distMin, int[] tata) {
        int n = c.length;
        int[] vizitat = new int[n];

        for (int i = 0; i < n; i++) {
            tata[i] = (c[nod][i] != INF) ? nod : -1;
            distMin[i] = c[nod][i];
            vizitat[i] = 0;
        }

        vizitat[nod] = 1;
        tata[nod] = -1;

        for (int i = 0; i < n - 1; i++) {
            int min = Integer.MAX_VALUE;
            int indexMin = -1;

            for (int j = 0; j < n; j++) {
                if (vizitat[j] == 0 && distMin[j] < min) {
                    min = distMin[j];
                    indexMin = j;
                }
            }

            if (indexMin != -1) {
                vizitat[indexMin] = 1;

                for (int j = 0; j < n; j++) {
                    if (vizitat[j] == 0) {
                        if (distMin[j] > distMin[indexMin] + c[indexMin][j]) {
                            distMin[j] = distMin[indexMin] + c[indexMin][j];
                            tata[j] = indexMin;
                        }
                    }
                }
            }
        }
    }

    public static List<Integer> AfiseazaDrum(int nodStart, int nodFinal, int[] tata) {
        List<Integer> drum = new ArrayList<>();
        int x = nodFinal;
        while (x != -1) {
            drum.add(x);
            x = tata[x];
        }
        Collections.reverse(drum);
        return drum;
    }
}
