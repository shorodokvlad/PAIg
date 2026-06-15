package divideEtImpera1;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class CelMaiMareDivizorComun {
    public static void main(String[] args) {
        String caleFisierIn = "data/cel_mai_mare_divizor_comun/in.txt";
        String caleFisierOut = "data/cel_mai_mare_divizor_comun/out.txt";

        try (Scanner sc = new Scanner(new File(caleFisierIn));
             PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            while (sc.hasNext()) {
                int n = sc.nextInt();
                int[] arr = new int[n];

                for (int i = 0; i < n; i++) {
                    arr[i] = sc.nextInt();
                }

                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                    writer.print(arr[i] + " ");
                }
                writer.println();
                System.out.println();

                int cmmdc = DetCelMaiMareDivizorComun(arr, 0, n - 1);
                System.out.println("Cel mai mare divizor comun: "  + cmmdc);
                writer.print(cmmdc);
                writer.println();

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int DeterminaCmmdc(int[] a, int p, int q) {
        // Cazul de baza: un singur element
        // returnam direct elementul, cmmdc(a[p], a[p]) = a[p]
        if (p == q) {
            return a[p];
        } else {
            // DIVIDE: impartim sirul in doua subjiruri egale
            int m = (p + q) / 2;

            // rezolvam recursiv subsirul STANG [p, m]
            // cmmdc1 = cmmdc-ul din prima jumatate
            int cmmdc1 = DeterminaCmmdc(a, p, m);

            // rezolvam recursiv subsirul DREPT [m+1, q]
            // cmmdc2 = cmmdc-ul din a doua jumatate
            int cmmdc2 = DeterminaCmmdc(a, m + 1, q);

            // IMPERA: combinam cele doua rezultate
            // folosind algoritmul lui Euclid iterativ
            // cmmdc(cmmdc1, cmmdc2)
            while (cmmdc2 != 0) {
                // salvam cmmdc2 intr-o variabila temporara
                // pentru ca il vom suprascrie
                int temp = cmmdc2;
                // noul cmmdc2 = restul impartirii cmmdc1 la cmmdc2
                cmmdc2 = cmmdc1 % cmmdc2;
                // noul cmmdc1 = vechiul cmmdc2
                cmmdc1 = temp;
            }
            // cand cmmdc2 devine 0, cmmdc1 contine rezultatul final
            return cmmdc1;
        }
    }

    public static int DetCelMaiMareDivizorComun(int[] a, int p, int q) {
        if (p == q) {
            return a[p];
        } else {
            int m = (p + q) / 2;

            int cmmdc1 = DetCelMaiMareDivizorComun(a, p, m);
            int cmmdc2 = DetCelMaiMareDivizorComun(a, m + 1, q);


            while (cmmdc2 != 0) {
                int temp = cmmdc2;

                cmmdc2 = cmmdc1 % cmmdc2;

                cmmdc1 = temp;
            }

            return cmmdc1;
        }
    }
}
