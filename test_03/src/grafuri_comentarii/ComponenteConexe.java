package grafuri_comentarii;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Determinarea componentelor conexe ale unui graf neorientat prin parcurgere DF.
 * Algoritmul caută repetat noduri nevizitate și lansează din fiecare o parcurgere DF,
 * etichetând toate nodurile atinse cu numărul componentei conexe curente.
 */
public class ComponenteConexe {
    // writer global accesibil din metodele statice
    static PrintWriter printWriter;
    // vizitat[i] = 0 dacă nodul i nu a fost vizitat, sau numărul componentei conexe din care face parte
    static int[] vizitat;
    // numărul de noduri, global pentru a fi accesibil din DF()
    static int n;

    public static void main(String[] args) {
        String caleFisierIn = "data/componente_conexe/in.txt";
        String caleFisierOut = "data/componente_conexe/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            printWriter = writer;

            n = sc.nextInt(); // numărul de noduri
            int m = sc.nextInt(); // numărul de muchii

            int[][] a = new int[n][n]; // matricea de adiacență, inițializată cu 0

            for (int i = 0; i < m; i++) {
                int u = sc.nextInt(); // primul capăt al muchiei
                int v = sc.nextInt(); // al doilea capăt al muchiei
                a[u][v] = 1;         // muchia u→v
                a[v][u] = 1;         // muchia v→u (graf neorientat)
            }

            vizitat = new int[n];
            for (int i = 0; i < n; i++) {
                vizitat[i] = 0; // inițial toate nodurile sunt nevizitate
            }

            int compConexa = ComponenteConexe(a); // determinăm componentele conexe

            printWriter.println(compConexa); // scriem numărul total de componente conexe

            for (int c = 1; c <= compConexa; c++) {
                List<Integer> noduri = new ArrayList<>(); // nodurile din componenta c

                for (int i = 0; i < n; i++) {
                    if (vizitat[i] == c) { // nodul i aparține componentei c
                        noduri.add(i);
                    }
                }

                // construim linia de ieșire pentru componenta c
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < noduri.size(); i++) {
                    if (i > 0) sb.append(" ");
                    sb.append(noduri.get(i));
                }
                printWriter.println(sb.toString()); // scriem nodurile componentei c
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Identifică toate componentele conexe ale grafului prin lansări repetate de DF.
     *
     * @param a matricea de adiacență
     * @return numărul total de componente conexe găsite
     */
    public static int ComponenteConexe(int[][] a) {
        boolean existaNodNevizitat = true; // flag care controlează bucla principală
        int compConexa = 0;               // contorul componentelor conexe găsite

        while (existaNodNevizitat) {
            existaNodNevizitat = false; // presupunem că nu mai există noduri nevizitate

            for (int i = 0; i < n; i++) {
                if (vizitat[i] == 0) {              // am găsit un nod nevizitat → nouă componentă
                    existaNodNevizitat = true;       // continuăm bucla după acest DF
                    compConexa = compConexa + 1;     // incrementăm numărul de componente
                    DF(a, i, compConexa);            // etichetăm toată componenta cu compConexa
                }
            }
        }

        return compConexa; // returnăm numărul total de componente conexe
    }

    /**
     * Parcurgere DF recursivă care etichetează nodul v și toți vecinii săi nevizitați
     * cu numărul componentei conexe c.
     *
     * @param a matricea de adiacență
     * @param v nodul curent vizitat
     * @param c numărul componentei conexe curente
     */
    public static void DF(int[][] a, int v, int c) {
        vizitat[v] = c; // marcăm nodul v ca aparținând componentei c

        for (int j = 0; j < n; j++) {
            if (a[v][j] == 1 && vizitat[j] == 0) { // j este vecin nevizitat al lui v
                DF(a, j, c);                         // continuăm DF recursiv din j, în aceeași componentă
            }
        }
    }
}