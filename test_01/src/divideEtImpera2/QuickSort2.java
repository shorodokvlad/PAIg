package divideEtImpera2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class QuickSort2 {
    public static void main(String[] args) {
        String caleFisierIn = "data/sortare/in.txt";
        String caleFisierOut = "data/sortare/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            while (sc.hasNext()) {
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

                QSort(arr, 0, arr.length - 1);

                System.out.print("Sortat: ");
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println("\n");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void QSort(int[] a, int p, int q) {
        int i = p;
        int j = q;

        //int x = a[(p + q) / 2];

        Random random = new Random();
        int indx = random.nextInt(q - p + 1) + p;
        int x = a[indx];
        System.out.println(x);

        do {
            while (a[i] < x) i++;
            System.out.println("while " + a[i] + " < " + x + " " + i + "++");
            while (a[j] > x) j--;
            System.out.println("while " + a[j] + " > " + x  + " " + j + "++");

            if (i <= j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;

                i++;
                j--;
            }

        } while (i <= j);

        if (p < j) QSort(a, p, j);
        System.out.println("QSORT (" + p + " " + j + ")");
        if (i < q) QSort(a, i, q);
        System.out.println(i + q);
    }
}
