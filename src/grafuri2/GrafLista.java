package grafuri2;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class GrafLista {
    static PrintWriter printWriter;
    static int[] vizitat;
    static int n;

    public static void main(String[] args) {
        String caleFisierIn  = "data/graf_lista/in.txt";
        String caleFisierOut = "data/graf_lista/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            printWriter = writer;

            // --- citire graf ---
            int nDinFisier = sc.nextInt();
            int m = sc.nextInt();

            int[][] muchii = new int[m][2];
            int maxNod = nDinFisier - 1;
            for (int i = 0; i < m; i++) {
                muchii[i][0] = sc.nextInt();
                muchii[i][1] = sc.nextInt();
                maxNod = Math.max(maxNod, Math.max(muchii[i][0], muchii[i][1]));
            }
            n = maxNod + 1;

            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int[] muchie : muchii) {
                adj.get(muchie[0]).add(muchie[1]);
                adj.get(muchie[1]).add(muchie[0]);
            }

            int start = sc.nextInt();

            // --- DF Recursiv ---
            vizitat = new int[n];
            long t1 = System.nanoTime();
            printWriter.print("DF recursiv: ");
            DFRecursiv(adj, start);
            long t2 = System.nanoTime();
            printWriter.println();
            printWriter.println("Timp DF recursiv: " + (t2 - t1) + " ns");

            // --- DF Iterativ ---
            vizitat = new int[n];
            long t3 = System.nanoTime();
            printWriter.print("DF iterativ: ");
            DFIterativ(adj, start);
            long t4 = System.nanoTime();
            printWriter.println();
            printWriter.println("Timp DF iterativ: " + (t4 - t3) + " ns");

            // --- BF ---
            vizitat = new int[n];
            long t5 = System.nanoTime();
            printWriter.print("BF: ");
            BF(adj, start);
            long t6 = System.nanoTime();
            printWriter.println();
            printWriter.println("Timp BF: " + (t6 - t5) + " ns");

            // --- Componente Conexe ---
            vizitat = new int[n];
            printWriter.println();
            int nrComp = ComponenteConexe(adj);
            printWriter.println("Nr. componente conexe: " + nrComp);
            for (int c = 1; c <= nrComp; c++) {
                printWriter.print("Componenta " + c + ": ");
                for (int i = 0; i < n; i++)
                    if (vizitat[i] == c) printWriter.print(i + " ");
                printWriter.println();
            }

            // --- Benchmark comparativ ---
            printWriter.println();
            printWriter.println("=== Benchmark: Lista vs Matrice ===");
            benchmark(adj, n, m);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------
    // DF Recursiv — O(n + m) cu listă
    // -------------------------------------------------------
    public static void DFRecursiv(List<List<Integer>> adj, int v) {
        vizitat[v] = 1;
        printWriter.print(v + " ");
        for (int j : adj.get(v))
            if (vizitat[j] == 0)
                DFRecursiv(adj, j);
    }

    // -------------------------------------------------------
    // DF Iterativ — O(n + m) cu listă
    // -------------------------------------------------------
    public static void DFIterativ(List<List<Integer>> adj, int start) {
        Stack<Integer> stiva = new Stack<>();
        stiva.push(start);
        vizitat[start] = 1;

        while (!stiva.isEmpty()) {
            int v = stiva.pop();
            printWriter.print(v + " ");
            List<Integer> vecini = new ArrayList<>(adj.get(v));
            Collections.reverse(vecini);
            for (int j : vecini)
                if (vizitat[j] == 0) {
                    stiva.push(j);
                    vizitat[j] = 1;
                }
        }
    }

    // -------------------------------------------------------
    // BF — O(n + m) cu listă
    // -------------------------------------------------------
    public static void BF(List<List<Integer>> adj, int start) {
        Queue<Integer> coada = new LinkedList<>();
        coada.add(start);
        vizitat[start] = 1;

        while (!coada.isEmpty()) {
            int p = coada.poll();
            printWriter.print(p + " ");
            for (int j : adj.get(p))
                if (vizitat[j] == 0) {
                    coada.add(j);
                    vizitat[j] = 1;
                }
        }
    }

    // -------------------------------------------------------
    // Componente Conexe
    // -------------------------------------------------------
    public static int ComponenteConexe(List<List<Integer>> adj) {
        boolean existaNevizitat = true;
        int compConexa = 0;
        while (existaNevizitat) {
            existaNevizitat = false;
            for (int i = 0; i < n; i++) {
                if (vizitat[i] == 0) {
                    existaNevizitat = true;
                    compConexa++;
                    DFComponente(adj, i, compConexa);
                }
            }
        }
        return compConexa;
    }

    public static void DFComponente(List<List<Integer>> adj, int v, int c) {
        vizitat[v] = c;
        for (int j : adj.get(v))
            if (vizitat[j] == 0)
                DFComponente(adj, j, c);
    }

    // -------------------------------------------------------
    // Benchmark: graf rar vs dens, listă vs matrice
    // -------------------------------------------------------
    public static void benchmark(List<List<Integer>> adjOriginal, int nOriginal, int mOriginal) {
        int[] dimensiuni = {100, 500, 1000, 5000};
        printWriter.printf("%-8s %-10s %-18s %-18s%n",
                "n", "m", "Lista (ns)", "Matrice (ns)");
        printWriter.println("-".repeat(58));

        for (int dim : dimensiuni) {
            // construim un graf rar: lanț 0-1-2-...(dim-1)
            int nrMuchii = dim - 1;
            List<List<Integer>> adjTest = new ArrayList<>();
            for (int i = 0; i < dim; i++) adjTest.add(new ArrayList<>());
            int[][] matriceTest = new int[dim][dim];

            for (int i = 0; i < nrMuchii; i++) {
                adjTest.get(i).add(i + 1);
                adjTest.get(i + 1).add(i);
                matriceTest[i][i + 1] = 1;
                matriceTest[i + 1][i] = 1;
            }

            // timp BF cu listă
            int[] viz1 = new int[dim];
            long s1 = System.nanoTime();
            bfBenchmark(adjTest, 0, viz1, dim);
            long timpLista = System.nanoTime() - s1;

            // timp BF cu matrice
            int[] viz2 = new int[dim];
            long s2 = System.nanoTime();
            bfBenchmarkMatrice(matriceTest, 0, viz2, dim);
            long timpMatrice = System.nanoTime() - s2;

            printWriter.printf("%-8d %-10d %-18d %-18d%n",
                    dim, nrMuchii, timpLista, timpMatrice);
        }
    }

    public static void bfBenchmark(List<List<Integer>> adj, int start, int[] viz, int dim) {
        Queue<Integer> coada = new LinkedList<>();
        coada.add(start);
        viz[start] = 1;
        while (!coada.isEmpty()) {
            int p = coada.poll();
            for (int j : adj.get(p))
                if (viz[j] == 0) { coada.add(j); viz[j] = 1; }
        }
    }

    public static void bfBenchmarkMatrice(int[][] a, int start, int[] viz, int dim) {
        Queue<Integer> coada = new LinkedList<>();
        coada.add(start);
        viz[start] = 1;
        while (!coada.isEmpty()) {
            int p = coada.poll();
            for (int j = 0; j < dim; j++)
                if (a[p][j] == 1 && viz[j] == 0) { coada.add(j); viz[j] = 1; }
        }
    }
}