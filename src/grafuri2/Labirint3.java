package grafuri2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Labirint3 {
    public static final int INF = 1000000;

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/labirint2/in.txt"));
             PrintWriter pw = new PrintWriter(new File("data/labirint2/out.txt"))) {

            List<List<Integer>> grid = new ArrayList<>();
            List<int[]> coords = new ArrayList<>();
            String line;

            // Citirea datelor (rezilientă la format mixt)
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.contains("X") || (line.length() > 20 && line.contains("0"))) {
                    List<Integer> row = new ArrayList<>();
                    for (int j = 0; j < line.length(); j++) {
                        char c = line.charAt(j);
                        if (c == '0') {
                            row.add(0);
                        } else if (c == 'X') {
                            row.add(-1);
                        } else if (c == '-') {
                            row.add(-1);
                            if (j + 1 < line.length() && line.charAt(j + 1) == '1') j++;
                        } else if (c != ' ' && c != '\t') {
                            row.add(-1);
                        }
                    }
                    grid.add(row);
                } else {
                    String[] tokens = line.split("\\s+");
                    if (tokens.length >= 2) {
                        coords.add(new int[]{Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])});
                    }
                }
            }

            if (grid.isEmpty() || coords.isEmpty()) return;

            // Procesăm fiecare punct de start (interogare)
            for (int i = 0; i < coords.size(); i++) {
                // Scădem 1 pentru indexare de la 0
                int startX = coords.get(i)[0] - 1;
                int startY = coords.get(i)[1] - 1;

                int dist = DijkstraSpreMargine(grid, startX, startY);
                pw.println(dist);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int DijkstraSpreMargine(List<List<Integer>> grid, int startX, int startY) {
        int m = grid.size();
        int n = grid.get(0).size();

        // Verificăm dacă startul este pe hartă și e o locație liberă
        if (startX < 0 || startX >= m || startY < 0 || startY >= n || grid.get(startX).get(startY) != 0) {
            return -1;
        }

        int[][] distMin = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                distMin[i][j] = INF;
            }
        }

        // {linie, coloană, distanță}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        pq.add(new int[]{startX, startY, 1}); // Distanța începe de la 1 (include celula de start)
        distMin[startX][startY] = 1;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!pq.isEmpty()) {
            int[] curent = pq.poll();
            int r = curent[0];
            int c = curent[1];
            int d = curent[2];

            // Condiția de succes: am atins una din marginile matricii
            if (r == 0 || r == m - 1 || c == 0 || c == n - 1) {
                return d;
            }

            if (d > distMin[r][c]) continue;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid.get(nr).get(nc) == 0) {
                    if (d + 1 < distMin[nr][nc]) {
                        distMin[nr][nc] = d + 1;
                        pq.add(new int[]{nr, nc, d + 1});
                    }
                }
            }
        }

        return -1; // Nu a găsit ieșire
    }
}