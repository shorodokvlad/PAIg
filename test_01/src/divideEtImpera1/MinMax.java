package divideEtImpera1;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MinMax {
    public static void main(String[] args) {
        String caleFisierIn = "data/min_max/in.txt";
        String caleFisierOut = "data/min_max/out.txt";

        try(Scanner sc = new Scanner(new File(caleFisierIn));
            PrintWriter writer = new PrintWriter(new File(caleFisierOut))) {
            while (sc.hasNext()) {
                int n = sc.nextInt();
                int[] arr = new int[n];

                for (int i = 0; i < n; i++) {
                    arr[i] = sc.nextInt();
                }

                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                }

                System.out.println();
                int min = DetMin(arr, 0, arr.length - 1);
                int max = DetMax(arr, 0, arr.length - 1);
                System.out.println("Min: " + min + ", Max: " + max);


                System.out.println();

                for (int i = 0; i < n; i++) {
                    writer.print(arr[i] + " ");
                }
                writer.println();
                writer.print(min + " ");
                writer.print(max);

                writer.println();

                int maxIndex = 0;

                for (int i = 0; i < n; i++) {
                    if (max == arr[i]) {
                        maxIndex = i;
                    }
                }

                arr = remove(arr, maxIndex);

                for (int i = 0; i < n; i++) {
                    System.out.print(arr[i] + " ");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int DeterminaMaxim(int[] a, int p, int q) {
        // Cazul de baza: daca subsirul are un singur element
        // nu mai putem divide, returnam direct acel element
        if (p == q) {
            return a[p];
        } else {
            // DIVIDE: calculam mijlocul intervalului [p, q]
            // pentru a imparti sirul in doua subjiruri egale
            int m = (p + q) / 2;

            // rezolvam recursiv subsirul STANG [p, m]
            // max1 = maximul din prima jumatate
            int max1 = DeterminaMaxim(a, p, m);

            // rezolvam recursiv subsirul DREPT [m+1, q]
            // max2 = maximul din a doua jumatate
            int max2 = DeterminaMaxim(a, m + 1, q);

            // IMPERA: combinam solutiile celor doua subjiruri
            // folosind operatorul ternar:
            // daca max1 < max2 returnam max2, altfel returnam max1
            return max1 < max2 ? max2 : max1;
        }
    }

    public static int DeterminaMinim(int[] a, int p, int q) {
        // Cazul de baza: daca subsirul are un singur element
        // nu mai putem divide, returnam direct acel element
        if (p == q) {
            return a[p];
        } else {
            // DIVIDE: calculam mijlocul intervalului [p, q]
            // pentru a imparti sirul in doua subjiruri egale
            int m = (p + q) / 2;

            // rezolvam recursiv subsirul STANG [p, m]
            // min1 = minimul din prima jumatate
            int min1 = DeterminaMinim(a, p, m);

            // rezolvam recursiv subsirul DREPT [m+1, q]
            // min2 = minimul din a doua jumatate
            int min2 = DeterminaMinim(a, m + 1, q);

            // IMPERA: combinam solutiile celor doua subjiruri
            // comparam minimele obtinute si returnam cel mai mic
            if (min1 > min2) {
                // min2 este mai mic, il returnam
                return min2;
            } else {
                // min1 este mai mic sau egal, il returnam
                return min1;
            }
        }
    }

    public static int DetMin(int[] a, int p, int q) {
        if (p == q) {
            return a[p];
        } else {
            int m = (p + q) / 2;

            int min1 = DetMin(a, p, m);
            int min2 = DetMin(a, m + 1, q);

            if (min1 > min2) {
                return min2;
            } else {
                return min1;
            }
        }
    }

    public static int DetMax(int[] a, int p, int q) {
        if (p == q) {
            return a[p];
        } else {
            int m = (p + q) / 2;

            int max1 = DetMax(a, p, m);
            int max2 = DetMax(a, m + 1, q);

            if (max1 < max2) {
                System.out.print(max1 + " ");
                return max2;

            } else {
                //System.out.print(max1 + " ");
                return max1;
            }
        }
    }

    public static int[] remove(int[] arr, int in) {

        if (arr == null || in < 0 || in >= arr.length) {
            return arr;
        }

        int[] arr2 = new int[arr.length - 1];

        // Copy the elements except the index
        // from original array to the other array
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == in)
                continue;

            arr2[k++] = arr[i];
        }

        return arr2;
    }


}