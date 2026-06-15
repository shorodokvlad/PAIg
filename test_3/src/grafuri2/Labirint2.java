package grafuri2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Labirint2 {

    public static void main(String[] args) {
        String inputFile = "data/labirint2/in.txt";
        String outputFile = "data/labirint2/out.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             PrintWriter pw = new PrintWriter(new File(outputFile))) {

            List<List<Integer>> grid = new ArrayList<>();
            List<int[]> queries = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (!line.contains(" ")) {
                    List<Integer> row = new ArrayList<>();
                    for (int j = 0; j < line.length(); j++) {
                        char c = line.charAt(j);
                        if (c == '0') {
                            row.add(0);
                        } else {
                            row.add(-1);
                        }
                    }
                    grid.add(row);
                } else {
                    String[] tokens = line.split("\\s+");
                    if (tokens.length >= 4) {
                        queries.add(new int[]{
                                Integer.parseInt(tokens[0]),
                                Integer.parseInt(tokens[1]),
                                Integer.parseInt(tokens[2]),
                                Integer.parseInt(tokens[3])
                        });
                    }
                }
            }

            if (grid.isEmpty() || queries.isEmpty()) return;

            int m = grid.size();
            int n = grid.get(0).size();

            for (int[] q : queries) {
                // Trecem de la indexare 1-based la 0-based
                int startX = q[0] - 1;
                int startY = q[1] - 1;
                int endX = q[2] - 1;
                int endY = q[3] - 1;

                if (startX < 0 || startX >= m || startY < 0 || startY >= n ||
                        endX < 0 || endX >= m || endY < 0 || endY >= n ||
                        grid.get(startX).get(startY) != 0 || grid.get(endX).get(endY) != 0) {
                    pw.println("-1");
                    continue;
                }

                int dist = BFS(grid, startX, startY, endX, endY);
                pw.println(dist);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int BFS(List<List<Integer>> grid, int startX, int startY, int endX, int endY) {
        int m = grid.size();
        int n = grid.get(0).size();

        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();

        // Coada va stoca: {linie, coloană, distanță}
        // Distanța inițială este 1 (cerința spune inclusiv capetele)
        queue.add(new int[]{startX, startY, 1});
        visited[startX][startY] = true;

        // Cele 8 direcții (N, S, E, V, NE, NV, SE, SV)
        int[] dr = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dc = {-1,  0,  1, -1, 1, -1, 0, 1};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            int dist = current[2];

            if (r == endX && c == endY) {
                return dist;
            }

            for (int i = 0; i < 8; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < m && nc >= 0 && nc < n && !visited[nr][nc] && grid.get(nr).get(nc) == 0) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc, dist + 1});
                }
            }
        }

        return -1;
    }
}