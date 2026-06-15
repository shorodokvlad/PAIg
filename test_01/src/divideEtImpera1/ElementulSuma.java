package divideEtImpera1;

import divideEtImpera2.QuickSort;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;


public class ElementulSuma {
    public static void main(String[] args) {
        String caleFisierIn = "data/element_suma/in.txt";
        String caleFisierOut = "data/element_suma2/out.txt";

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

                System.out.println();
                //Sum(arr, n, x);
                Sum2(arr, n, x);
                System.out.println();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void Suma(int[] a, int n, int x) {
        for (int i = 0; i <= n - 2; i++) {
            for (int j = i + 1; j <= n - 1; j++) {
                if (a[i] + a[j] == x) {
                    System.out.println(a[i] + " " + a[j]);
                    return;
                }
            }
        }
        System.out.println(-1);
    }

    public static void Sum_2(int[] a, int n, int x) {

        // Parcurgem fiecare element din sir ca potential "prim element" al perechii
        // Ne oprim la n-1 pentru ca ultimul element nu mai are pereche de cautat
        for (int i = 0; i < n - 1; i++) {

            // DIVIDE: calculam complementul - ce valoare ar trebui sa gasim
            // pentru ca a[i] + complement = x  =>  complement = x - a[i]
            // Exemplu: x=10, a[i]=3  =>  cautam valoarea 7 in sir
            int j = CautareBinara.CautaBinar(a, 0, n - 1, x - a[i]);

            // Verificam doua conditii:
            // 1) j != -1  =>  complementul chiar exista in sir (nu -1 = negasit)
            // 2) j != i   =>  nu folosim acelasi element de doua ori
            //                 (ex: x=10, a[i]=5 => cauta 5, gaseste tot a[i])
            if (j != -1 && j != i) {

                // Am gasit o pereche valida: a[i] + a[j] = x
                // Afisam cele doua valoare care formeaza suma
                System.out.println(a[i] + " " + a[j]);
            }
        }
    }


    public static void Sum(int[] a, int n, int x) {
        for (int i = 0; i <= n - 2; i++) {
            for (int j = i + 1; j <= n - 1; j++) {
                if (a[i] + a[j] == x) {
                    System.out.println(a[i] + " + " + a[j]);
                    return;
                }
            }
        }
        System.out.println(-1);
    }

    public static void Sum2(int[] a, int n, int x) {
        for (int i = 0; i < n - 1; i++) {
            int j = CautareBinara.CautaBinar(a, 0, n - 1, x - a[i]);
            if (j != - 1 && j != i) {
                System.out.println(a[i] + " + " + a[j] + " = " + x);
            }
        }
    }










}