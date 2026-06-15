package divideEtImpera1;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class SumaPatratelor {
    public static void main(String[] args) {
        String caleFisierIn = "data/suma_patratelor/in.txt";
        String caleFisierOut = "data/suma_patratelor/out.txt";

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

                int suma = PowerSum(arr, 0, n - 1);
                System.out.println(suma);
                writer.print(suma);
                writer.println();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int SumPatratelor(int[] a, int p, int q) {
        // Cazul de baza: un singur element
        // returnam direct patratul lui
        if (p == q) {
            return a[p] * a[p];
        } else {
            // DIVIDE: impartim sirul in doua subjiruri egale
            // m+1 pentru ca mijlocul apartine partii STANGI [p, m]
            // si nu trebuie omis din calcul
            int m = (p + q) / 2;

            // rezolvam recursiv subsirul STANG [p, m]
            // suma1 = suma patratelor din prima jumatate
            int suma1 = SumPatratelor(a, p, m);

            // rezolvam recursiv subsirul DREPT [m+1, q]
            // suma2 = suma patratelor din a doua jumatate
            // folosim m+1 si NU m pentru ca a[m] este
            // deja inclus in suma1, altfel l-am aduna de doua ori
            int suma2 = SumPatratelor(a, m + 1, q);

            // IMPERA: combinam cele doua sume
            return suma1 + suma2;
        }
    }
    public static int PowerSum(int[] a, int p, int q) {
        if (p == q) {
            return a[p] * a[p];
        } else {
            int m = (p + q) / 2;

            int sum1 = PowerSum(a, p, m);
            int sum2 = PowerSum(a, m + 1, q);

            return sum1 + sum2;
        }
    }
}
