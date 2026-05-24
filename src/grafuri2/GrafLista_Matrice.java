package grafuri2;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class GrafLista_Matrice {
    static PrintWriter printWriter;
    static int[] vizitat;
    static int n;

    public static void main(String[] args) {
        String caleFisierIn = "data/graf_lista/in.txt";
        String caleFisierOut = "data/graf_lista/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            printWriter = writer;

            n = sc.nextInt();
            int m = sc.nextInt();

// citim muchiile în avans pentru a determina n-ul real
            int[][] muchii = new int[m][2];
            int maxNod = n - 1;
            for (int i = 0; i < m; i++) {
                muchii[i][0] = sc.nextInt();
                muchii[i][1] = sc.nextInt();
                maxNod = Math.max(maxNod, Math.max(muchii[i][0], muchii[i][1]));
            }
            n = maxNod + 1; // n real bazat pe nodurile din fișier

            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

            for (int i = 0; i < m; i++) {
                int u = muchii[i][0];
                int v = muchii[i][1];
                adj.get(u).add(v);
                adj.get(v).add(u);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DFRecursiv(List<List<Integer>> adj, int v) {
        vizitat[v] = 1;
        printWriter.print(v + " ");
        for (int j : adj.get(v))
            if (vizitat[j] == 0)
                DFRecursiv(adj, j);
    }

    public static void DFIterativ(List<List<Integer>> adj, int start) {
        Stack<Integer> stiva = new Stack<>();
        stiva.push(start);
        vizitat[start] = 1;

        while (!stiva.isEmpty()) {
            int v = stiva.pop();
            printWriter.print(v + " ");
            // inversăm lista vecinilor pentru aceeași ordine ca varianta recursivă
            List<Integer> vecini = new ArrayList<>(adj.get(v));
            Collections.reverse(vecini);
            for (int j : vecini)
                if (vizitat[j] == 0) {
                    stiva.push(j);
                    vizitat[j] = 1;
                }
        }
    }

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
}
