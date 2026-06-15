package divideEtImpera2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class ElementulSuma2 {

    public static void main(String[] args) {
        String caleFisierIn = "data/element_suma2/in.txt";
        String caleFisierOut = "data/element_suma2/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {

            while (sc.hasNext()) {
                int n = sc.nextInt();

                int[] a = new int[n];
                int[] b = new int[n];

                for (int i = 0; i < n; i++) a[i] = sc.nextInt();
                for (int i = 0; i < n; i++) b[i] = sc.nextInt();

                int x = sc.nextInt();

                QuickSort.QSort(a, 0, n - 1);
                QuickSort.QSort(b, 0, n - 1);


                String rezultat = findSum(a, b, n, x);

                System.out.println(rezultat + " = " + x);
                writer.println(rezultat);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String cautaSuma(int[] a, int[] b, int n, int x) {

        // Folosim tehnica "two pointers" (doi pointeri):
        // i porneste de la STANGA lui a[] (valori mici)
        // j porneste de la DREAPTA lui b[] (valori mari)
        int i = 0;
        int j = n - 1;

        // Continuam cat timp ambii pointeri sunt in limitele sirurilor
        // i merge doar spre dreapta (creste), j merge doar spre stanga (scade)
        // cand i ajunge la n sau j ajunge la -1, am epuizat toate combinatiile
        while (i < n && j >= 0) {

            // Calculam suma curenta: un element din a[] + un element din b[]
            // a[i] = element mic (din stanga), b[j] = element mare (din dreapta)
            int suma = a[i] + b[j];

            if (suma == x) {
                // Suma exacta gasita! Returnam perechea ca String
                return a[i] + " + " + b[j];

            } else if (suma < x) {
                // Suma e PREA MICA => avem nevoie de un numar mai mare
                // Mutam i spre dreapta => a[i] creste => suma va creste
                i++;

            } else {
                // Suma e PREA MARE => avem nevoie de un numar mai mic
                // Mutam j spre stanga => b[j] scade => suma va scadea
                j--;
            }

            // La fiecare pas, fie i creste fie j scade
            // => algoritmul converge garantat, fara bucle infinite
        }

        // Am parcurs toate combinatiile posibile fara sa gasim suma x
        return "-1";
    }

    public static String findSum(int[] a, int[] b, int n, int x) {
        int i = 0;
        int j = n - 1;

        while (i < n && j > 0) {
            int suma = a[i] + b[j];

            if (suma == x) {
                return a[i] + " + " + b[j];
            } else if (suma < x) {
                i++;
            } else {
                j--;
            }
        }
        return "-1";
    }

}