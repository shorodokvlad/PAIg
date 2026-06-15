package grafuri2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FacebookLikes {
    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/facebook/in.txt";
        String caleFisierOut = "data/facebook/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            printWriter = writer;

            int nrTeste = sc.nextInt();
            for (int t = 0; t < nrTeste; t++) {
                proceseazaTest(sc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void proceseazaTest(Scanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        long L = sc.nextLong();

        int[][] a = new int[n][n];
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            a[x][y] = 1;
            a[y][x] = 1;
        }

        long[] totalLikesAcordate = new long[n];
        for (int sursa = 0; sursa < n; sursa++) {
            int[] dist = BFS(a, n, sursa);
            totalLikesAcordate[sursa] = calculeazaLikes(dist, n, sursa, L);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(" ");
            sb.append(totalLikesAcordate[i]);
        }
        printWriter.println(sb.toString());
    }

    // BFS din sursa — returnează distanțele minime la toate nodurile
    public static int[] BFS(int[][] a, int n, int sursa) {
        int[] dist = new int[n];
        Arrays.fill(dist, -1);

        Queue<Integer> coada = new LinkedList<>();
        coada.add(sursa);
        dist[sursa] = 0;

        while (!coada.isEmpty()) {
            int p = coada.poll();
            for (int j = 0; j < n; j++) {
                if (a[p][j] == 1 && dist[j] == -1) {
                    dist[j] = dist[p] + 1;
                    coada.add(j);
                }
            }
        }

        return dist;
    }

    // Suma like-urilor acordate de sursa: L / 2^(d-1) pentru fiecare nod la distanța d
    public static long calculeazaLikes(int[] dist, int n, int sursa, long L) {
        long total = 0;
        for (int j = 0; j < n; j++) {
            if (j == sursa || dist[j] == -1) continue;

            int d = dist[j];
            long likes = L >> (d - 1);
            if (likes == 0) continue;

            total += likes;
        }
        return total;
    }
}