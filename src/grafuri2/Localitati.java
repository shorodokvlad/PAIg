package grafuri2;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Localitati {
    public static void main(String[] args) {
        String caleFisierIn  = "data/localitati/in.txt";
        String caleFisierOut = "data/localitati/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            List<Integer> tokens = new ArrayList<>();
            while (sc.hasNextInt())
                tokens.add(sc.nextInt());

            int pos = 0;
            while (pos < tokens.size()) {
                int n = detecteazaN(tokens, pos);
                if (n == -1) break;

                int[][] dist = new int[n][n];
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        dist[i][j] = tokens.get(pos + i * n + j);

                int A    = tokens.get(pos + n * n);
                int B    = tokens.get(pos + n * n + 1);
                int Dmax = tokens.get(pos + n * n + 2);
                pos += n * n + 3;

                proceseazaTest(writer, dist, n, A, B, Dmax);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int detecteazaN(List<Integer> tokens, int pos) {
        for (int n = 2; pos + n * n + 3 <= tokens.size(); n++) {
            boolean diagonalaZero = true;
            for (int i = 0; i < n; i++) {
                if (tokens.get(pos + i * n + i) != 0) {
                    diagonalaZero = false;
                    break;
                }
            }
            if (!diagonalaZero) continue;

            int A = tokens.get(pos + n * n);
            int B = tokens.get(pos + n * n + 1);
            if (A >= 0 && A < n && B >= 0 && B < n)
                return n;
        }
        return -1;
    }

    public static void proceseazaTest(PrintWriter writer, int[][] dist,
                                      int n, int A, int B, int Dmax) {
        int[] parinte = new int[n];
        Arrays.fill(parinte, -1);
        boolean[] vizitat = new boolean[n];

        Queue<Integer> coada = new LinkedList<>();
        coada.add(A);
        vizitat[A] = true;

        while (!coada.isEmpty()) {
            int p = coada.poll();
            if (p == B) break;
            for (int j = 0; j < n; j++) {
                if (!vizitat[j] && dist[p][j] > 0 && dist[p][j] <= Dmax) {
                    vizitat[j] = true;
                    parinte[j] = p;
                    coada.add(j);
                }
            }
        }

        if (!vizitat[B]) {
            writer.println("Nu există soluție");
        } else {
            List<Integer> drum = new ArrayList<>();
            for (int nod = B; nod != -1; nod = parinte[nod])
                drum.add(nod);
            Collections.reverse(drum);

            StringBuilder sb = new StringBuilder("Drum " + A + " -> " + B + ": ");
            for (int i = 0; i < drum.size(); i++) {
                if (i > 0) sb.append(" ");
                sb.append(drum.get(i));
            }
            writer.println(sb.toString());
        }
    }
}