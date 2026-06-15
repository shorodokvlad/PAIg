import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import static divideEtImpera2.QuickSort.QuickkSort;

public class NumberGenerator {
    public static void main(String[] args) {
        String caleFisierIn = "data/sortare/in.txt";
        String caleFisierOut = "data/sortare/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {


                int n = 1000;
                int[] arr = new int[n];

                for (int i = 0; i < n; i++) {
                    arr[i] = (int)(Math.random() * 5000);
                }

                System.out.print("Array: ");
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i] + " ");
                    writer.print(arr[i] + " ");
                }
                System.out.println();

                writer.print("\n");

                QuickkSort(arr, 0, n - 1);

            System.out.print("Sortat: ");
            for (int i = 0; i < n; i++) {
                System.out.print(arr[i] + " ");
            }
                System.out.println("\n");

            writer.print("\n");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
