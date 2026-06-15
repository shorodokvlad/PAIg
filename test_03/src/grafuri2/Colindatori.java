package grafuri2;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Colindatori {
    static PrintWriter printWriter;
    static int[] vizitat;
    static int n;

    public static void main(String[] args) {
        String caleFisierIn = "data/colindatori/in.txt";
        String caleFisierOut = "data/colindatori/out.txt";

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
        n = sc.nextInt();

        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = sc.nextInt();

        // b[i][j] = 1 dacă i și j sunt prieteni SAU au prieteni comuni
        int[][] b = construiesteGrafExtins(a);

        // componente conexe în graful extins
        vizitat = new int[n];
        int compConexa = 0;
        boolean existaNevizitat = true;

        while (existaNevizitat) {
            existaNevizitat = false;
            for (int i = 0; i < n; i++) {
                if (vizitat[i] == 0) {
                    existaNevizitat = true;
                    compConexa++;
                    DF(b, i, compConexa);
                }
            }
        }

        // numărăm mărimea fiecărei componente și sortăm crescător
        List<Integer> marimi = new ArrayList<>();
        for (int c = 1; c <= compConexa; c++) {
            int marime = 0;
            for (int i = 0; i < n; i++)
                if (vizitat[i] == c) marime++;
            marimi.add(marime);
        }
        Collections.sort(marimi);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < marimi.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(marimi.get(i));
        }
        printWriter.println(sb.toString());
    }

    // b[i][j] = 1 dacă a[i][j]==1 SAU există k cu a[i][k]==1 și a[k][j]==1
    public static int[][] construiesteGrafExtins(int[][] a) {
        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if (a[i][j] == 1) {
                    b[i][j] = 1;
                    continue;
                }
                for (int k = 0; k < n; k++) {
                    if (a[i][k] == 1 && a[k][j] == 1) {
                        b[i][j] = 1;
                        break;
                    }
                }
            }
        }
        return b;
    }

    public static void DF(int[][] b, int v, int c) {
        vizitat[v] = c;
        for (int j = 0; j < n; j++)
            if (b[v][j] == 1 && vizitat[j] == 0)
                DF(b, j, c);
    }
}