import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MainPartOne {
    
    public static void main(String[] args) {
        MainPartOne mpo = new MainPartOne();
        mpo.processMap("Day22/resources/example" );
        mpo.processMap("Day22/resources/input");
    }

    private void processMap(String inputFilePath) {
        try {
            List<String> map = Files.readAllLines(Paths.get(inputFilePath));
            int nodeInfected = 0;
            int size = map.get(0).length();
            int x = (size - 1) / 2;
            int y = x;
            Facing facing = Facing.UP;
            Map<Point, Character> nodes = new HashMap<>();
            for (int burst = 0; burst < 10000; burst++) {
                Point position = new Point(x, y);
                char node = '.';
                if (x >= 0 && x < size && y >= 0 && y < size) {
                    if (!nodes.containsKey(position)) {
                        node = map.get(x).charAt(y);
                        nodes.put(position, node);
                    } else {
                        node = nodes.get(position);
                    }
                } else {
                    if (nodes.containsKey(position)) {
                        node = nodes.get(position);
                    }
                }
                Turn turn = null;
                switch (node) {
                    case '#':
                        turn = Turn.RIGHT;
                        nodes.put(position, '.');
                        break;
                    case '.':
                        turn = Turn.LEFT;
                        nodes.put(position, '#');
                        nodeInfected++;
                        break;
                }
                switch (facing) {
                    case UP:
                        if (turn == Turn.LEFT) {
                            y--;
                            facing = Facing.LEFT;
                        }
                        else {
                            y++;
                            facing = Facing.RIGHT;
                        }
                        break;
                    case DOWN:
                        if (turn == Turn.LEFT) {
                            y++;
                            facing = Facing.RIGHT;
                        }
                        else {
                            y--;
                            facing = Facing.LEFT;
                        }
                        break;
                    case LEFT:
                        if (turn == Turn.LEFT) {
                            x++;
                            facing = Facing.DOWN;
                        }
                        else {
                            x--;
                            facing = Facing.UP;
                        }
                        break;
                    case RIGHT:
                        if (turn == Turn.LEFT) {
                            x--;
                            facing = Facing.UP;
                        }
                        else {
                            x++;
                            facing = Facing.DOWN;
                        }
                        break;
                }
            }
            System.out.println(inputFilePath + " :: # burst caused an infection = " + nodeInfected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private enum Facing {
        UP, DOWN, LEFT, RIGHT

    }

    private enum Turn {
        LEFT, RIGHT
    }

    private class Point {
        int x = 0;
        int y = 0;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

}
