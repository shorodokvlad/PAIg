package divideEtImpera2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class QuickSort {
    public static void main(String[] args) {
        String caleFisierIn = "data/sortare/in.txt";
        String caleFisierOut = "data/sortare/out.txt";




        int[] arr = citireFisier(caleFisierIn);

        System.out.println(Arrays.toString(arr));



        /*
                System.out.print("Array: ");
                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                }

                System.out.println();
                QuickkSort(arr, 0, n - 1);

                System.out.print("Sortat: ");
                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                }

                System.out.println("\n");

                 */
    }

    public static int getNrTest(String numeFisier) {
        try (Scanner sc = new Scanner(new File(numeFisier))) {

            int n = sc.nextInt();

            return n;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] citireFisier(String numeFisier) {
        try (Scanner sc = new Scanner(new File(numeFisier))) {


            int nrTest = sc.nextInt();

            int n = sc.nextInt();

            int[] arr = new int[n];

            for (int i = 0; i < nrTest; i++)
            {
                for (int j = 0; j < n; j++) {
                    arr[j] = sc.nextInt();

                }

            }
            return arr;



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void QSort(int[] a, int p, int q) {

        // Initializam doi pointeri la capetele intervalului [p, q]
        // i porneste din STANGA si merge spre dreapta
        // j porneste din DREAPTA si merge spre stanga
        int i = p;
        int j = q;

        // DIVIDE: alegem pivotul ca elementul din MIJLOCUL intervalului
        // (alegerea din mijloc e mai buna decat primul/ultimul element
        //  pentru a evita cazul cel mai rau pe siruri aproape sortate)
        int x = a[(p + q) / 2];

        do {
            // Mutam i spre dreapta cat timp elementele sunt mai mici decat pivotul
            // => cautam primul element din stanga care "nu e la locul lui"
            //    (ar trebui sa fie in jumatatea dreapta)
            while (a[i] < x) i++;

            // Mutam j spre stanga cat timp elementele sunt mai mari decat pivotul
            // => cautam primul element din dreapta care "nu e la locul lui"
            //    (ar trebui sa fie in jumatatea stanga)
            while (a[j] > x) j--;

            // Daca i si j nu s-au incrucisat inca, am gasit doi "intruși":
            // a[i] e prea mare (e in stanga dar > pivot)
            // a[j] e prea mic  (e in dreapta dar < pivot)
            // => ii schimbam intre ei
            if (i <= j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;

                // Dupa schimb, avansam ambii pointeri
                // a[i] si a[j] sunt acum la locul lor, trecem mai departe
                i++;
                j--;
            }

            // Continuam pana cand pointerii se incruciSeaza (i > j)
            // La final: toate elementele din [p,j] <= pivot
            //           toate elementele din [i,q] >= pivot
        } while (i <= j);

        // IMPERA: sortam recursiv cele doua subjumătati

        // Jumatatea STANGA [p, j] - elementele mai mici sau egale cu pivotul
        if (p < j) QSort(a, p, j);

        // Jumatatea DREAPTA [i, q] - elementele mai mari sau egale cu pivotul
        if (i < q) QSort(a, i, q);

        // Cazul de baza (implicit): cand p >= q, intervalul are 0 sau 1 element
        // => deja sortat, recursivitatea se opreste
    }


    public static void QSortPrimul(int a[], int p, int q) {
        int i = p;
        int j = q;
        int x = a[p];

        do {
            while (a[i] < x) i++;
            while (a[j] > x) j--;

            if (i <= j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;

                i++;
                j--;
            }
        } while (i <= j);

        if (p < j) QSort(a, p, j);
        if (i < q) QSort(a, i, q);
    }

    public static void QSortAleatoriu(int a[], int p, int q) {
        int i = p;
        int j = q;

        Random random = new Random();

        // random.nextInt(q - p + 1) genereaza un numar aleatoriu din intervalul [0, q-p]
        // adaugam + p pentru a "muta" intervalul din [0, q-p] in [p, q]
        //
        // Exemplu: p=2, q=5
        //   q - p + 1  = 5 - 2 + 1 = 4
        //   nextInt(4) => genereaza 0, 1, 2 sau 3  (intervalul [0, 3])
        //   + p        => genereaza 2, 3, 4 sau 5  (intervalul [p, q]) ✓
        int indexAleatoriu = random.nextInt(q - p + 1) + p;
        int x = a[indexAleatoriu];

        do {
            while (a[i] < x) i++;
            while (a[j] > x) j--;

            if (i <= j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;

                i++;
                j--;
            }
        } while (i <= j);

        if (p < j) QSort(a, p, j);
        if (i < q) QSort(a, i, q);
    }

    public static void QuickkSort(int[] a, int p, int q) {
        int i = p;
        int j = q;

        Random random = new Random();
        int indx = random.nextInt(q - p + 1) + p;
        int x = a[indx];

        do {
            while (a[i] < x) i++;
            while (a[j] > x) j--;

            if (i <= j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;

                i++;
                j--;
            }

        } while (i <= j);

        if (p < j) QuickkSort(a, p, j);
        if (i < q) QuickkSort(a, i, q);
    }


}
