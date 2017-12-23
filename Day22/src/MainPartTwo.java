import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPartTwo {
    
    public static void main(String[] args) {
        MainPartTwo mpo = new MainPartTwo();
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
            for (int burst = 0; burst < 10000000; burst++) {
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
                        nodes.put(position, 'F');
                        break;
                    case '.':
                        turn = Turn.LEFT;
                        nodes.put(position, 'W');
                        break;
                    case 'W':
                        turn = Turn.NOT_TURN;
                        nodes.put(position, '#');
                        nodeInfected++;
                        break;
                    case 'F':
                        turn = Turn.GO_BACK;
                        nodes.put(position, '.');
                        break;
                }
                switch (facing) {
                    case UP:
                        switch (turn) {
                            case LEFT:
                                y--;
                                facing = Facing.LEFT;
                                break;
                            case RIGHT:
                                y++;
                                facing = Facing.RIGHT;
                                break;
                            case NOT_TURN:
                                x--;
                                break;
                            case GO_BACK:
                                x++;
                                facing = Facing.DOWN;
                                break;
                        }
                        break;
                    case DOWN:
                        switch (turn) {
                            case LEFT:
                                y++;
                                facing = Facing.RIGHT;
                                break;
                            case RIGHT:
                                y--;
                                facing = Facing.LEFT;
                                break;
                            case NOT_TURN:
                                x++;
                                break;
                            case GO_BACK:
                                x--;
                                facing = Facing.UP;
                                break;
                        }
                        break;
                    case LEFT:
                        switch (turn) {
                            case LEFT:
                                x++;
                                facing = Facing.DOWN;
                                break;
                            case RIGHT:
                                x--;
                                facing = Facing.UP;
                                break;
                            case NOT_TURN:
                                y--;
                                break;
                            case GO_BACK:
                                y++;
                                facing = Facing.RIGHT;
                                break;
                        }
                        break;
                    case RIGHT:
                        switch (turn) {
                            case LEFT:
                                x--;
                                facing = Facing.UP;
                                break;
                            case RIGHT:
                                x++;
                                facing = Facing.DOWN;
                                break;
                            case NOT_TURN:
                                y++;
                                break;
                            case GO_BACK:
                                y--;
                                facing = Facing.LEFT;
                                break;
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
        LEFT, RIGHT, NOT_TURN, GO_BACK
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
