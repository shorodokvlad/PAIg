package grafuri2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Labirint {
    public static final int INF = 1000000;

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/labirint/in.txt"));
             PrintWriter pw = new PrintWriter(new File("data/labirint/out.txt"))) {

            List<String> allLines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    allLines.add(line.trim());
                }
            }

            if (allLines.size() < 3) return;

            String[] endTokens = allLines.get(allLines.size() - 1).split("\\s+");
            int endX = Integer.parseInt(endTokens[0]);
            int endY = Integer.parseInt(endTokens[1]);

            String[] startTokens = allLines.get(allLines.size() - 2).split("\\s+");
            int startX = Integer.parseInt(startTokens[0]);
            int startY = Integer.parseInt(startTokens[1]);

            List<List<Integer>> grid = new ArrayList<>();
            for (int i = 0; i < allLines.size() - 2; i++) {
                String rowString = allLines.get(i);
                List<Integer> row = new ArrayList<>();
                // Logica nouă de parsare caracter cu caracter
                for (int j = 0; j < rowString.length(); j++) {
                    if (rowString.charAt(j) == '-') {
                        row.add(-1);
                        j++; // Sărim peste cifra '1'
                    } else if (rowString.charAt(j) == '0') {
                        row.add(0);
                    }
                }
                grid.add(row);
            }

            int m = grid.size();
            int n = grid.get(0).size();
            int V = m * n;

            int[][] c = new int[V][V];
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    c[i][j] = (i == j) ? 0 : INF;
                }
            }

            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid.get(i).get(j) == 0) {
                        int u = i * n + j;
                        for (int d = 0; d < 4; d++) {
                            int ni = i + dr[d];
                            int nj = j + dc[d];

                            if (ni >= 0 && ni < m && nj >= 0 && nj < n && grid.get(ni).get(nj) == 0) {
                                int v = ni * n + nj;
                                c[u][v] = 1;
                            }
                        }
                    }
                }
            }

            int[] distMin = new int[V];
            int[] tata = new int[V];

            int nodStart = startX * n + startY;
            int nodEnd = endX * n + endY;

            DijkstraAlg(c, nodStart, distMin, tata);
            List<Integer> drum = AfiseazaDrum(nodStart, nodEnd, tata);

            if (drum.isEmpty() || drum.get(0) != nodStart) {
                pw.println("Nu exista drum.");
            } else {
                pw.println(drum.size());
                for (int nod : drum) {
                    pw.println((nod / n) + " " + (nod % n));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DijkstraAlg(int[][] c, int nod, int[] distMin, int[] tata) {
        int n = c.length;
        int[] vizitat = new int[n];

        for (int i = 0; i < n; i++) {
            tata[i] = (c[nod][i] != INF) ? nod : -1;
            distMin[i] = c[nod][i];
            vizitat[i] = 0;
        }

        vizitat[nod] = 1;
        tata[nod] = -1;

        for (int i = 0; i < n - 1; i++) {
            int min = Integer.MAX_VALUE;
            int indexMin = -1;

            for (int j = 0; j < n; j++) {
                if (vizitat[j] == 0 && distMin[j] < min) {
                    min = distMin[j];
                    indexMin = j;
                }
            }

            if (indexMin != -1) {
                vizitat[indexMin] = 1;

                for (int j = 0; j < n; j++) {
                    if (vizitat[j] == 0) {
                        if (distMin[j] > distMin[indexMin] + c[indexMin][j]) {
                            distMin[j] = distMin[indexMin] + c[indexMin][j];
                            tata[j] = indexMin;
                        }
                    }
                }
            }
        }
    }

    public static List<Integer> AfiseazaDrum(int nodStart, int nodFinal, int[] tata) {
        List<Integer> drum = new ArrayList<>();
        int x = nodFinal;
        while (x != -1) {
            drum.add(x);
            if (x == nodStart) break;
            x = tata[x];
        }
        Collections.reverse(drum);
        return drum;
    }
}