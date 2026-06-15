package divideEtImpera1;

import divideEtImpera2.QuickSort;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class GhicesteNumarul {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String caleFisierIn = "data/ghiceste_numarul/in.txt";
        String caleFisierOut = "data/ghiceste_numarul/out.txt";

        /*
        System.out.println("Introdu valoarea maxima n:");
        int n = scanner.nextInt();
        scanner.nextLine();

        guessNumber(1, n);

         */

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            while (sc.hasNext()) {
                int n = sc.nextInt();

                if (n == -1) return;

                System.out.println("Am citit numarul secret din fisier. Incep sa ghicesc...\n");
                guessNumber(1, 100, n);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void gheseste(int p, int q) {
        int m = (p + q) / 2;

        System.out.println("Este numarul " + m + "?");
        System.out.println("Raspunde: PREA MARE, PREA MIC sau BRAVO");

        String raspuns = scanner.nextLine().toUpperCase();

        if (raspuns.equals("BRAVO")) {
            System.out.println("Am ghicit numarul " + m + "!");
        } else if (raspuns.equals("PREA MARE")) {
            if (p == q) {
                System.out.println("Nu exista solutie!");
            } else {
                gheseste(p, m - 1);
            }
        } else if (raspuns.equals("PREA MIC")) {
            if (p == q) {
                System.out.println("Nu exista solutie!");
            } else {
                gheseste(m + 1, q);
            }
        }
    }

    public static void ghiceste(int p, int q, int x) {
        int m = (p + q) / 2;

        System.out.println("Calculatorul incearca: " + m);

        if (m == x) {
            System.out.println("BRAVO! Am ghicit numarul " + m + "!");
        } else if (m > x) {
            System.out.println("Raspuns: PREA MARE");
            if (p == q) {
                System.out.println("Nu exista solutie!");
            } else {
                ghiceste(p, m - 1, x);
            }
        } else {
            System.out.println("Raspuns: PREA MIC");
            if (p == q) {
                System.out.println("Nu exista solutie!");
            } else {
                ghiceste(m + 1, q, x);
            }
        }
    }

    public static void guessNumber(int p, int q, int x) {
        int m = (p + q) / 2;

        System.out.println("Calculatorul ghiceste: " + m);

        if (m == x) {
            System.out.println("Am ghicit numarul " + m);
        } else if (m > x) {
            System.out.println("Prea mare");
            if (p == q) {
                System.out.println("Nu are solutie");
            } else {
                guessNumber(p, m - 1, x);
            }
        } else {
            System.out.println("Prea mic");
            if (p == q) {
                System.out.println("Nu are solutie");
            } else {
                guessNumber(m + 1, q, x);
            }
        }
    }

}
