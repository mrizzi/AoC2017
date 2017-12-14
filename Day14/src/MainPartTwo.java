import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class MainPartTwo {

    public static void main(String[] args) {
        MainPartTwo mpo = new MainPartTwo();
        mpo.processStream("Day14/resources/example");
        mpo.processStream("Day14/resources/input");
    }

    private void processStream(String inputFilePath) {
        try {
            String key = Files.readAllLines(Paths.get(inputFilePath)).get(0);
            StringBuilder[] rows = new StringBuilder[128];
            for (int i = 0; i < 128; i++) {
                char[] hash = KnotHash.get(key + "-" + i).toCharArray();
                StringBuilder row = new StringBuilder();
                for (char c : hash) {
                    row.append(String.format("%4s", Integer.toBinaryString(Integer.parseInt("" + c, 16))).replace(' ','0'));
                }
                rows[i] = row;
            }
            int label = 0;
            for (int i = 0; i < 128; i++) {
                for (int j = 0; j < 128; j++) {
                    Queue<Point> queue = new LinkedList<>();
                    if (rows[i].charAt(j) == '1') {
                        label++;
                        rows[i].setCharAt(j, (char)label);
                        queue.add(new Point(i, j));
                        while (!queue.isEmpty()) {
                            Point point = queue.remove();
                            int x = point.x;
                            int y = point.y;
                            if (x > 0) {
                                if (rows[x - 1].charAt(y) == '1') {
                                    rows[x - 1].setCharAt(y, (char)label);
                                    queue.add(new Point(x - 1, y));
                                }
                            }
                            if (x < 127) {
                                if (rows[x + 1].charAt(y) == '1') {
                                    rows[x + 1].setCharAt(y, (char)label);
                                    queue.add(new Point(x + 1, y));
                                }
                            }
                            if (y > 0) {
                                if (rows[x].charAt(y - 1) == '1') {
                                    rows[x].setCharAt(y - 1, (char)label);
                                    queue.add(new Point(x, y - 1));
                                }
                            }
                            if (y < 127) {
                                if (rows[x].charAt(y + 1) == '1') {
                                    rows[x].setCharAt(y + 1, (char)label);
                                    queue.add(new Point(x, y + 1));
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(inputFilePath + " :: Regions = " + label);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private class Point {
        int x = 0;
        int y = 0;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

}
