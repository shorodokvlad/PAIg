package grafuri_comentarii;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Parcurgere în lățime (BFS) a unui graf neorientat reprezentat prin matrice de adiacență.
 * Algoritmul vizitează nodurile nivel cu nivel, pornind dintr-un nod sursă,
 * folosind o coadă pentru a procesa nodurile în ordinea în care au fost descoperite.
 */
public class BF {
    // writer global pentru a putea fi accesat din metoda statică BF()
    static PrintWriter printWriter;

    public static void main(String[] args) {
        String caleFisierIn = "data/graf/in.txt";
        String caleFisierOut = "data/graf/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            printWriter = writer;

            int n = sc.nextInt(); // numărul de noduri ale grafului
            int m = sc.nextInt(); // numărul de muchii ale grafului

            int[][] a = new int[n][n]; // matricea de adiacență, inițializată cu 0

            for (int i = 0; i < m; i++) {
                int u = sc.nextInt(); // primul nod al muchiei curente
                int v = sc.nextInt(); // al doilea nod al muchiei curente
                a[u][v] = 1;         // marcăm muchia u→v
                a[v][u] = 1;         // marcăm muchia v→u (graf neorientat)
            }

            int start = sc.nextInt(); // nodul de start al parcurgerii
            BF(a, n, start);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parcurge graful în lățime pornind din nodul v și scrie nodurile vizitate în ordine.
     *
     * @param a matricea de adiacență a grafului
     * @param n numărul de noduri
     * @param v nodul de start
     */
    public static void BF(int[][] a, int n, int v) {
        int[] vizitat = new int[n]; // vizitat[i] = 1 dacă nodul i a fost deja adăugat în coadă

        for (int i = 0; i < n; i++) {
            vizitat[i] = 0; // inițial niciun nod nu este vizitat
        }

        Queue<Integer> coada = new LinkedList<>(); // coada nodurilor descoperite, neprocessate încă
        coada.add(v);    // adăugăm nodul de start în coadă
        vizitat[v] = 1;  // marcăm nodul de start ca vizitat pentru a nu-l adăuga din nou

        while (!coada.isEmpty()) {       // cât timp mai există noduri neprocessate
            int p = coada.poll();        // extragem primul nod din coadă (FIFO)
            printWriter.println(p);      // procesăm nodul: îl scriem în fișierul de ieșire

            for (int j = 0; j < n; j++) {                    // parcurgem toți vecinii posibili ai lui p
                if (a[p][j] == 1 && vizitat[j] == 0) {      // dacă j este vecin nevizitat al lui p
                    coada.add(j);                             // îl adăugăm în coadă pentru procesare ulterioară
                    vizitat[j] = 1;                           // îl marcăm vizitat imediat pentru a evita duplicatele
                }
            }
        }
    }
}