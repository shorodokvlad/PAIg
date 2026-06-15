package divideEtImpera2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class OperatiiMultimi {

    static PrintWriter writer;

    public static void main(String[] args) {
        String caleFisierIn = "data/operatii_multimi/in.txt";
        String caleFisierOut = "data/operatii_multimi/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter pw = new PrintWriter(new File(caleFisierOut))) {

            writer = pw; // atribuim câmpului static

            while (sc.hasNext()) {
                // Citire mulțimea A
                int n = sc.nextInt();
                int[] a = new int[n];
                for (int i = 0; i < n; i++) {
                    a[i] = sc.nextInt();
                }

                // Citire mulțimea B
                int m = sc.nextInt();
                int[] b = new int[m];
                for (int i = 0; i < m; i++) {
                    b[i] = sc.nextInt();
                }

                // Reuniune
                //writer.print("A U B: ");
                System.out.print("A U B: ");
                //String raspuns = reunion(a, n, b, m);
                reuniune(a, n, b, m);
                //System.out.println(raspuns);
                //writer.println();

                // Intersecție
                //writer.print("A ∩ B: ");
                System.out.print("\nA ∩ B: ");
                intersectie(a, n, b, m);
                //writer.println();

                // Diferență
                //writer.print("A – B: ");
                System.out.print("\nA - B: ");
                diferenta(a, n, b, m);
                //writer.println();
            }



        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    // Reuniune
    public static void reuniune(int[] a, int n, int[] b, int m) {
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (a[i] == b[j]) {
                System.out.print(a[i] + " ");
                writer.print(a[i] + " ");
                i++; j++;
            } else if (a[i] < b[j]) {
                System.out.print(a[i] + " ");
                writer.print(a[i] + " ");
                i++;
            } else {
                System.out.print(b[j] + " ");
                writer.print(b[j] + " ");
                j++;
            }
        }
        while (i < n) {
            System.out.print(a[i] + " ");
            writer.print(a[i++] + " ");
        }
        while (j < m) {
            System.out.print(b[j] + " ");
            writer.print(b[j++] + " ");
        }
    }

    // Intersecție
    public static void intersectie(int[] a, int n, int[] b, int m) {
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (a[i] == b[j]) {
                System.out.print(a[i] + " ");
                writer.print(a[i] + " ");
                i++; j++;
            } else if (a[i] < b[j]) {
                i++;
            } else {
                j++;
            }
        }
    }

    // Diferență A - B
    public static void diferenta(int[] a, int n, int[] b, int m) {
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (a[i] == b[j]) {
                i++; j++;
            } else if (a[i] < b[j]) {
                System.out.print(a[i] + " ");
                writer.print(a[i] + " ");
                i++;
            } else {
                j++;
            }
        }
        while (i < n) { System.out.print(a[i] + " "); writer.print(a[i++] + " "); }
    }

    /*
    public static String reunion(int[] a, int n, int[] b, int m) {
        StringBuilder sb = new StringBuilder();

        int i = 0, j = 0;
        while (i < n && j < m) {
            if (a[i] == b[j]) {
                sb.append(a[i] + " ");
                i++; j++;
            } else if (a[i] < b[j]) {
                sb.append(a[i] + " ");
                i++;
            } else {
                sb.append(b[j] + " ");
                j++;
            }
        }

        while (i < n) {
            sb.append(a[i] + " ");
            i++;
        }

        while (j < m) {
            sb.append(b[j] + " ");
            j++;
        }
        return sb.toString();
    }

     */
}