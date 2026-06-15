package divideEtImpera1;

import divideEtImpera2.QuickSort;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class CautareBinara {
    public static void main(String[] args) {
        String caleFisierIn = "data/cautare_binara/in.txt";
        String caleFisierOut = "data/cautare_binara/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            while (sc.hasNext()) {

                int n = sc.nextInt();
                int[] arr = new int[n];

                for (int i = 0; i < n; i++) {
                    arr[i] = sc.nextInt();
                }
                int x = sc.nextInt();

                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                }

                QuickSort.QSort(arr, 0, arr.length - 1);
                System.out.println("\nArray sortat: ");
                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                    writer.print(arr[i] + " ");
                }

                /*
                int cauta = BinarySearch(arr, 0, arr.length - 1, x);
                System.out.println("\n[Cautarea Binara] Valoarea " + x + " a fost gasita la indexul: " + cauta);
                System.out.println("");

                 */



                int cauta = IterativeBinarySearch(arr, n, x);
                System.out.println("\n[Cautarea Binara] Valoarea " + x + " a fost gasita la indexul: " + cauta);
                System.out.println("");





                writer.println("\n" + x);
                writer.println(cauta);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int Cauta(int[] a, int n, int x) {
        for (int i = 0; i < n; i++) {
            if (a[i] == x)
                return i;
        }
        return -1;
    }

    public static int CautaBinar(int[] a, int p, int q, int x) {
        // Cazul de baza: daca p > q inseamna ca subsirul este gol
        // elementul x nu a fost gasit, returnam -1
        if (p > q) return -1;

        // DIVIDE: calculam mijlocul intervalului [p, q]
        int m = (p + q) / 2;

        // IMPERA: verificam daca elementul din mijloc este x
        if (a[m] == x) {
            // am gasit elementul x la pozitia m, returnam pozitia
            return m;
        } else {
            // elementul nu este la mijloc, continuam cautarea
            if (a[m] < x) {
                // x este mai mare decat mijlocul
                // x se afla in jumatatea DREAPTA [m+1, q]
                // ignoram complet jumatatea stanga
                return CautaBinar(a, m + 1, q, x);
            } else {
                // x este mai mic decat mijlocul
                // x se afla in jumatatea STANGA [p, m-1]
                // ignoram complet jumatatea dreapta
                return CautaBinar(a, p, m - 1, x);
            }
        }
    }

    public static int CautaBinar_Iterativ(int[] a, int n, int x) {

        // Initializam capetele intervalului de cautare [p, q]
        // p = primul index (stanga), q = ultimul index (dreapta)
        int p = 0;
        int q = n - 1;

        // Continuam cautarea cat timp intervalul [p, q] este valid (nenul)
        // Cand p > q, inseamna ca am epuizat toate elementele -> x nu exista
        while (p <= q) {

            // DIVIDE: calculam mijlocul intervalului curent [p, q]
            int mid = (p + q) / 2;

            // IMPERA: verificam daca elementul din mijloc este x
            if (a[mid] == x) return mid;  // am gasit! returnam pozitia

            // elementul nu este la mijloc, injumatatim intervalul:
            if (a[mid] < x) {
                // x este mai MARE decat mijlocul
                // x se afla in jumatatea DREAPTA -> mutam p dupa mijloc
                // intervalul devine [mid+1, q], ignoram complet jumatatea stanga
                p = mid + 1;
            } else {
                // x este mai MIC decat mijlocul
                // x se afla in jumatatea STANGA -> mutam q inaintea mijlocului
                // intervalul devine [p, mid-1], ignoram complet jumatatea dreapta
                q = mid - 1;
            }

            // La fiecare iteratie, intervalul se injumatateste
            // dupa log2(n) pasi, fie gasim x, fie p > q si ne oprim
        }

        // Am iesit din bucla fara sa gasim x -> x nu se afla in sir
        return -1;
    }


    public static int BinarySearch(int[] a, int p, int q, int x) {
        if (p > q) return -1;

        int m = (p + q) / 2;

        if (a[m] == x) {
            return m;
        } else if (a[m] > x) {
            return BinarySearch(a, p, m - 1, x);
        } else {
            return BinarySearch(a, m + 1, q, x);
        }
    }

    public static int IterativeBinarySearch(int[] a, int n, int x) {
        int p = 0;
        int q = n - 1;

        while (p <= q) {
            int m = (p + q) / 2;

            if (a[m] == x) {
                return m;
            } else if (a[m] < x) {
                p = m + 1;
            } else {
                q = m - 1;
            }
        }
        return -1;
    }


}
