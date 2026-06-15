package divideEtImpera2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MergeSort {
    public static void main(String[] args) {
        String caleFisierIn = "data/sortare/in.txt";
        String caleFisierOut = "data/sortare/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            while (sc.hasNext()) {
                int n = sc.nextInt();
                int[] arr = new int[n];

                for (int i = 0; i < n; i++) {
                    arr[i] = sc.nextInt();
                }

                System.out.print("Array: ");
                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                SortInterclasare(arr, 0, arr.length - 1);

                System.out.print("Sortat: ");
                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                    writer.print(arr[i] + " ");
                }
                writer.println();

                System.out.println("\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void SortareInterclasare(int a[], int p, int q) {
        if (p < q) {
            int m = (p + q) / 2;

            // Sortează subșirul din STÂNGA (de la p la m)
            SortareInterclasare(a, p, m);

            // Sortează subșirul din DREAPTA (de la m + 1 la q)
            SortareInterclasare(a, m + 1, q);

            // Combină cele două jumătăți sortate
            Interclaseaza(a, p, m, q);
        }
    }

    public static void Interclasare(int[] a, int p, int m, int q) {

        int k = 0;       // index pentru array-ul temporar c (unde scriem următorul element)
        int i = p;       // index curent în jumătatea STÂNGĂ  → parcurge [p ... m]
        int j = m + 1;   // index curent în jumătatea DREAPTĂ → parcurge [m+1 ... q]

        // Alocăm un array temporar de exact câte elemente are intervalul [p ... q]
        // Exemplu: p=0, q=5 → c are 6 elemente
        int[] c = new int[q - p + 1];

        // ---------------------------------------------------------------
        // FAZA 1: Comparăm câte un element din fiecare jumătate
        //         și îl adăugăm pe cel mai mic în c
        //         Ne oprim când una din jumătăți se epuizează
        // ---------------------------------------------------------------
        while (i <= m && j <= q) {

            if (a[i] == a[j]) {
                /* Dacă elementele sunt egale, le adăugăm pe amândouă */
                c[k++] = a[i++]; // adaugă a[i] în c, apoi avansează i și k
                c[k++] = a[j++]; // adaugă a[j] în c, apoi avansează j și k

            } else if (a[i] < a[j]) {
                // Elementul din STÂNGA e mai mic → îl adăugăm primul
                c[k++] = a[i++]; // adaugă a[i] în c, apoi avansează i și k

            } else {
                // Elementul din DREAPTA e mai mic → îl adăugăm primul
                c[k++] = a[j++]; // adaugă a[j] în c, apoi avansează j și k
            }
        }

        // ---------------------------------------------------------------
        // FAZA 2: Copiem elementele RĂMASE din jumătatea stângă
        //         (se execută doar dacă dreapta s-a terminat primul)
        // ---------------------------------------------------------------
        while (i <= m) {
            c[k++] = a[i++]; // adaugă elementele rămase din stânga
        }

        // ---------------------------------------------------------------
        // FAZA 3: Copiem elementele RĂMASE din jumătatea dreaptă
        //         (se execută doar dacă stânga s-a terminat primul)
        // ---------------------------------------------------------------
        while (j <= q) {
            c[k++] = a[j++]; // adaugă elementele rămase din dreapta
        }

        // ---------------------------------------------------------------
        // FAZA 4: Copiem rezultatul sortat din c înapoi în array-ul original a
        //         pe pozițiile [p ... q] (exact de unde am luat elementele)
        // ---------------------------------------------------------------
        for (int x = 0; x < c.length; x++) {
            a[p + x] = c[x]; // p+x asigură că scriem pe pozițiile corecte din a
        }
    }

    public static void Interclaseaza(int[] a, int p, int m, int q) {
        int k = 0;
        int i = p;
        int j = m + 1;

        int[] c = new int[q - p + 1];

        while (i <= m && j <= q) {
            if (a[i] == a[j]) {
                c[k++] = a[i++];
                c[k++] = a[j++];
            } else if (a[i] < a[j]) {
                c[k++] = a[i++];
            } else {
                c[k++] = a[j++];
            }
        }

        while (i <= m) {
            c[k++] = a[i++];
        }

        while (j <= q) {
            c[k++] = a[j++];
        }

        for (int x = 0; x < c.length; x++) {
            a[p + x] = c[x];
        }
    }

    public static void SortInterclasare(int[] a, int p, int q) {
        if (p < q) {
            int m = (p + q) / 2;

            SortInterclasare(a, p, m);
            SortInterclasare(a, m + 1, q);
            Interclaseaza(a, p, m, q);
        }
    }
}

