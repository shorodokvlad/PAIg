package divideEtImpera2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class MergeSort2 {
    public static void main(String[] args) {
        String caleFisierIn = "data/sortare/in.txt";
        String caleFisierOut = "data/sortare/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            int nrTest = sc.nextInt();

            for (int j = 0; j < nrTest; j++) {
                int n = sc.nextInt();
                int[] arr = new int[n];


                for (int i = 0; i < arr.length; i++) {
                    arr[i] = sc.nextInt();
                }

                System.out.print("Array: ");
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();

                SortareInterclasare(arr, 0, arr.length - 1);

                System.out.print("Sortat: ");
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i] + " ");
                    writer.print(arr[i] + " ");
                }
                writer.print("\n");
                System.out.println("\n");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
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

    public static void SortareInterclasare(int[] a, int p, int q) {
        if (p < q) {
            int m = (p + q) / 2;

            SortareInterclasare(a, p, m);
            SortareInterclasare(a, m + 1, q);
            Interclaseaza(a, p, m, q);
        }
    }
}
