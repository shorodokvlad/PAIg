package grafuri_comentarii;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Algoritmul Roy-Floyd-Warshall pentru determinarea drumurilor minime între
 * oricare două noduri ale unui graf ponderat. Folosește programare dinamică:
 * pentru fiecare nod intermediar k, îmbunătățește distanțele între toate perechile
 * (i, j) verificând dacă trecerea prin k produce un drum mai scurt.
 */
public class RoyFloydWarshall {
    // valoare convenție pentru distanță infinită (muchie inexistentă)
    public static final int INF = 1000000;
    // drum[i][j] = predecesorul lui j pe drumul minim de la i la j
    private static int[][] drum;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File("data/roy_floyd/in.txt"));
             PrintWriter pw = new PrintWriter(new File("data/roy_floyd/out.txt"))) {

            if (sc.hasNextInt()) {
                int n = sc.nextInt(); // numărul de noduri
                int m = sc.nextInt(); // numărul de muchii ponderate

                int[][] c = new int[n][n]; // matricea costurilor: c[i][j] = costul muchiei i→j

                // inițializăm matricea: 0 pe diagonală, INF pentru muchii inexistente
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        c[i][j] = (i == j) ? 0 : INF;
                    }
                }

                for (int i = 0; i < m; i++) {
                    int u = sc.nextInt(); // nodul sursă al muchiei
                    int v = sc.nextInt(); // nodul destinație al muchiei
                    int w = sc.nextInt(); // costul (greutatea) muchiei
                    c[u][v] = c[v][u] = w; // graf neorientat: muchia există în ambele sensuri
                }

                int[][] d = RoyFloyd(c, n); // calculăm toate drumurile minime

                // scriem matricea distanțelor minime, câte un rând pe linie
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        pw.print(d[i][j] + (j == n - 1 ? "" : " "));
                    }
                    pw.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculează matricea distanțelor minime între toate perechile de noduri.
     *
     * @param c matricea costurilor inițiale
     * @param n numărul de noduri
     * @return matricea d unde d[i][j] = distanța minimă de la i la j
     */
    public static int[][] RoyFloyd(int[][] c, int n) {
        int[][] d = new int[n][n]; // matricea distanțelor minime (rezultatul final)
        drum = new int[n][n];      // drum[i][j] = predecesorul lui j pe drumul minim i→j

        // inițializăm d cu costurile directe și drum cu predecesorii direcți
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                d[i][j] = c[i][j]; // distanța inițială = costul muchiei directe (sau INF)
                if (i != j && c[i][j] != INF) {
                    drum[i][j] = i;  // există muchie directă: predecesorul lui j este i
                } else {
                    drum[i][j] = -1; // nu există muchie directă sau i==j
                }
            }
        }

        // k = nodul intermediar prin care testăm dacă drumul i→k→j este mai scurt
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && i != k && k != j) {        // excludem cazurile triviale
                        if (d[i][j] > d[i][k] + d[k][j]) {  // drumul prin k este mai scurt
                            d[i][j] = d[i][k] + d[k][j];     // actualizăm distanța minimă i→j
                            drum[i][j] = drum[k][j];          // actualizăm predecesorul: k era pe drumul k→j
                        }
                    }
                }
            }
        }

        return d; // returnăm matricea distanțelor minime
    }

    /**
     * Tipărește recursiv drumul minim de la nodul i la nodul j folosind vectorul drum.
     *
     * @param i nodul sursă
     * @param j nodul destinație
     */
    public static void TiparesteDrum(int i, int j) {
        if (drum[i][j] == i) {
            // caz de bază: predecesorul lui j este chiar i → muchie directă
            System.out.print(i + " " + j + " ");
        } else if (drum[i][j] != -1) {
            // caz recursiv: drum[i][j] este un nod intermediar pe drumul i→j
            TiparesteDrum(i, drum[i][j]);   // tipărim drumul de la i la nodul intermediar
            TiparesteDrum(drum[i][j], j);   // tipărim drumul de la nodul intermediar la j
        } else {
            // drum[i][j] == -1: nu există niciun drum între i și j
            System.out.print("Nu exista drum.");
        }
    }
}