package grafuri_comentarii;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Algoritmul lui Dijkstra pentru determinarea drumurilor minime dintr-un nod sursă
 * într-un graf ponderat. La fiecare pas, se extrage nodul nevizitat cu distanța minimă
 * și se relaxează muchiile sale, actualizând distanțele vecinilor dacă s-a găsit un drum mai scurt.
 */
public class Dijkstra {
    // valoare convenție pentru distanță infinită (muchie inexistentă)
    public static final int INF = 1000000;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File("data/dijkstra/in.txt"));
             PrintWriter pw = new PrintWriter(new File("data/dijkstra/out.txt"))) {

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

                int[] distMin = new int[n]; // distMin[i] = distanța minimă de la nodStart la i
                int[] tata = new int[n];    // tata[i] = predecesorul lui i pe drumul minim

                int nodStart = 2; // nodul sursă fix (hardcodat)

                DijkstraAlg(c, nodStart, distMin, tata);

                // scriem distanțele minime separate prin spații
                for (int i = 0; i < n; i++) {
                    pw.print(distMin[i] + (i == n - 1 ? "" : " "));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculează distanțele minime de la nodul sursă la toate celelalte noduri.
     *
     * @param c       matricea costurilor
     * @param nod     nodul sursă
     * @param distMin vectorul distanțelor minime (completat de algoritm)
     * @param tata    vectorul predecesorilor pe drumurile minime (completat de algoritm)
     */
    public static void DijkstraAlg(int[][] c, int nod, int[] distMin, int[] tata) {
        int n = c.length;
        int[] vizitat = new int[n]; // vizitat[i] = 1 dacă distanța minimă la i este finalizată

        // inițializăm distanțele cu costurile directe din nodul sursă
        for (int i = 0; i < n; i++) {
            tata[i] = (c[nod][i] != INF) ? nod : -1; // tată = sursă dacă există muchie directă
            distMin[i] = c[nod][i];                   // distanța inițială = costul muchiei directe
            vizitat[i] = 0;                            // niciun nod nu este finalizat
        }

        vizitat[nod] = 1; // nodul sursă este finalizat primul (distanța sa este 0)
        tata[nod] = -1;   // nodul sursă nu are predecesor

        // repetăm de n-1 ori: la fiecare pas finalizăm un nod
        for (int i = 0; i < n - 1; i++) {
            int min = Integer.MAX_VALUE;
            int indexMin = -1;

            // găsim nodul nevizitat cu distanța minimă curentă
            for (int j = 0; j < n; j++) {
                if (vizitat[j] == 0 && distMin[j] < min) {
                    min = distMin[j];
                    indexMin = j;
                }
            }

            if (indexMin != -1) {
                vizitat[indexMin] = 1; // finalizăm nodul cu distanța minimă

                // relaxăm muchiile din indexMin: actualizăm vecinii nevizitați
                for (int j = 0; j < n; j++) {
                    if (vizitat[j] == 0) {
                        if (distMin[j] > distMin[indexMin] + c[indexMin][j]) {
                            distMin[j] = distMin[indexMin] + c[indexMin][j]; // am găsit un drum mai scurt
                            tata[j] = indexMin; // actualizăm predecesorul lui j
                        }
                    }
                }
            }
        }
    }

    /**
     * Reconstituie drumul minim de la nodStart la nodFinal folosind vectorul predecesorilor.
     *
     * @param nodStart nodul sursă
     * @param nodFinal nodul destinație
     * @param tata     vectorul predecesorilor completat de DijkstraAlg
     * @return lista nodurilor de pe drumul minim, în ordine de la sursă la destinație
     */
    public static List<Integer> AfiseazaDrum(int nodStart, int nodFinal, int[] tata) {
        List<Integer> drum = new ArrayList<>();
        int x = nodFinal;

        // mergem înapoi prin vectorul tata de la destinație spre sursă
        while (x != -1) {
            drum.add(x);    // adăugăm nodul curent la drum
            x = tata[x];    // urcăm la predecesorul lui x
        }

        Collections.reverse(drum); // inversăm lista pentru ordinea sursă → destinație
        return drum;
    }
}