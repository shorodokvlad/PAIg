package grafuri2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Vrajitor {
    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/vrajitor/in.txt";
        String caleFisierOut = "data/vrajitor/out.txt";

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

        // a[x][y] = 1 înseamnă "x o consideră populară pe y"
        int[][] a = new int[n][n];
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            a[x][y] = 1;
        }

        int popularitateInitiala = popularitate(a, n, 0);

        int v = sc.nextInt();
        for (int i = 0; i < v; i++) {
            String tip = sc.next();
            int persoana = sc.nextInt();

            switch (tip) {
                case "Inverseaza":
                    inverseaza(a, n, persoana);
                    break;
                case "Nepopular":
                    nepopular(a, n, persoana);
                    break;
                case "Popular":
                    popular(a, n, persoana);
                    break;
            }
        }

        int popularitateFinala = popularitate(a, n, 0);
        printWriter.println(popularitateFinala - popularitateInitiala);
    }

    // popularitatea lui x = numărul de persoane care îl preferă pe x (in-degree)
    public static int popularitate(int[][] a, int n, int x) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (i != x) {
                count += a[i][x];
            }
        }
        return count;
    }

    // Vraja 1: inversează toate preferințele persoanei x (muchiile de ieșire)
    public static void inverseaza(int[][] a, int n, int x) {
        for (int j = 0; j < n; j++) {
            if (j != x) {
                a[x][j] = 1 - a[x][j];
            }
        }
    }

    // Vraja 2: nimeni nu îl mai preferă pe x (elimină toate muchiile de intrare)
    public static void nepopular(int[][] a, int n, int x) {
        for (int i = 0; i < n; i++) {
            a[i][x] = 0;
        }
    }

    // Vraja 3: toți îl preferă pe x (adaugă toate muchiile de intrare)
    public static void popular(int[][] a, int n, int x) {
        for (int i = 0; i < n; i++) {
            if (i != x) {
                a[i][x] = 1;
            }
        }
    }
}