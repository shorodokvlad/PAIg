package grafuri_comentarii;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Parcurgere în adâncime (DFS) a unui graf neorientat reprezentat prin matrice de adiacență.
 * Algoritmul vizitează recursiv fiecare nod nevizitat adiacent nodului curent,
 * mergând cât mai adânc posibil înainte de a se întoarce (backtracking).
 */
public class DF {
    // writer global accesibil din metoda statică DF()
    static PrintWriter printWriter;
    // vizitat[i] = 0 dacă nodul i nu a fost vizitat, 1 dacă a fost vizitat
    static int[] vizitat;
    // numărul de noduri, global pentru a fi accesibil din DF()
    static int n;

    public static void main(String[] args) {
        String caleFisierIn = "data/graf/in.txt";
        String caleFisierOut = "data/graf/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            printWriter = writer;

            n = sc.nextInt(); // numărul de noduri ale grafului
            int m = sc.nextInt(); // numărul de muchii ale grafului

            int[][] a = new int[n][n]; // matricea de adiacență, inițializată cu 0

            for (int i = 0; i < m; i++) {
                int u = sc.nextInt(); // primul capăt al muchiei curente
                int v = sc.nextInt(); // al doilea capăt al muchiei curente
                a[u][v] = 1;         // muchia u→v
                a[v][u] = 1;         // muchia v→u (graf neorientat)
            }

            int start = sc.nextInt(); // nodul de start al parcurgerii

            vizitat = new int[n];
            for (int i = 0; i < n; i++) {
                vizitat[i] = 0; // inițial toate nodurile sunt nevizitate
            }

            DF(a, start); // lansăm parcurgerea DF din nodul de start

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parcurge recursiv graful în adâncime pornind din nodul v
     * și scrie fiecare nod vizitat în fișierul de ieșire.
     *
     * @param a matricea de adiacență a grafului
     * @param v nodul curent vizitat
     */
    public static void DF(int[][] a, int v) {
        vizitat[v] = 1;       // marcăm nodul v ca vizitat
        printWriter.println(v); // procesăm nodul: îl scriem în fișierul de ieșire

        for (int j = 0; j < n; j++) {                   // parcurgem toți vecinii posibili ai lui v
            if (a[v][j] == 1 && vizitat[j] == 0) {      // dacă j este vecin nevizitat al lui v
                DF(a, j);                                 // continuăm parcurgerea recursiv din j
            }
        }
        // la întoarcerea din recursie (backtracking) se reia căutarea vecinilor lui v
    }
}