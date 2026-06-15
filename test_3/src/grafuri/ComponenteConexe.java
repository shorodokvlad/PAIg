package grafuri;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComponenteConexe {
    static PrintWriter printWriter;
    static int[] vizitat;
    static int n;

    public static void main(String[] args) {
        String caleFisierIn = "data/componente_conexe/in.txt";
        String caleFisierOut = "data/componente_conexe/out.txt";

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

            vizitat = new int[n];
            for (int i = 0; i < n; i++) {
                vizitat[i] = 0;
            }

            int compConexa = ComponenteConexe(a);

            // scriem rezultatul
            printWriter.println(compConexa);
            for (int c = 1; c <= compConexa; c++) {
                List<Integer> noduri = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if (vizitat[i] == c) {
                        noduri.add(i);
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < noduri.size(); i++) {
                    if (i > 0) sb.append(" ");
                    sb.append(noduri.get(i));
                }
                printWriter.println(sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int ComponenteConexe(int[][] a) {
        boolean existaNodNevizitat = true;
        int compConexa = 0;

        while (existaNodNevizitat) {
            existaNodNevizitat = false;
            for (int i = 0; i < n; i++) {
                if (vizitat[i] == 0) {
                    existaNodNevizitat = true;
                    compConexa = compConexa + 1;
                    DF(a, i, compConexa);
                }
            }
        }

        return compConexa;
    }

    public static void DF(int[][] a, int v, int c) {
        vizitat[v] = c;

        for (int j = 0; j < n; j++) {
            if (a[v][j] == 1 && vizitat[j] == 0) {
                DF(a, j, c);
            }
        }
    }
}