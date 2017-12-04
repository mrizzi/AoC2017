import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainPartTwo {

    public static void main(String[] args) {
        MainPartTwo mainPartTwo = new MainPartTwo();
        mainPartTwo.calculate();
    }
    
    private void calculate() {
//        int target = 3;
//        int target = 24;
//        int target = 805;
        int target = 312051;
        int x = 0, y = 0;
        int step = 0;
        int sum = step;
        Direction direction = Direction.RIGHT;
        Map<Coordinate, Integer> values = new HashMap<>();
        Coordinate lastCoordinate = new Coordinate(x, y);
        for (int square = 1; true; square++) {
            Integer calculated = 0;
            switch (direction) {
                case UP:
                    // left
                    calculated = values.get(new Coordinate(x - 1, y));
                    // NOT upper right corner and the prev one
                    if (y < (step - 1)) calculated += values.get(new Coordinate(x - 1, y + 1));
                    if (y > -(step - 1)) {
                        calculated += values.get(new Coordinate(x - 1, y - 1));
                        calculated += values.get(new Coordinate(x, y - 1));
                    }
                    lastCoordinate = new Coordinate(x, y);
                    if (Math.abs(++y) == step) direction = Direction.LEFT;
                    break;
                case LEFT:
                    // under
                    calculated = values.get(new Coordinate(x, y -1));
                    // NOT upper left corner and the prev one
                    if (x > - (step - 1)) calculated += values.get(new Coordinate(x - 1, y - 1));
                    if (x < step) {
                        calculated += values.get(new Coordinate(x + 1, y - 1));
                        calculated += values.get(new Coordinate(x + 1, y));
                    }
                    lastCoordinate = new Coordinate(x, y);
                    if (Math.abs(--x) == step) direction = Direction.DOWN;
                    break;
                case DOWN:
                    // right
                    calculated = values.get(new Coordinate(x + 1, y));
                    // NOT lower left corner and the prev one
                    if (y > - (step - 1)) calculated += values.get(new Coordinate(x + 1, y - 1));
                    if (y < step) {
                        calculated += values.get(new Coordinate(x, y + 1));
                        calculated += values.get(new Coordinate(x + 1, y + 1));
                    }
                    lastCoordinate = new Coordinate(x, y);
                    if (Math.abs(--y) == step) direction = Direction.RIGHT;
                    break;
                case RIGHT:
                    if (square == 1) {
                        calculated = 1;
                    } else {
                        // above
                        calculated = values.get(new Coordinate(x, y + 1));
                        // NOT lower right corner and the prev one
                        if (x < step) calculated += values.get(new Coordinate(x + 1, y + 1));
                        if (x > -step) {
                            calculated += values.get(new Coordinate(x - 1, y));
                            calculated += values.get(new Coordinate(x - 1, y + 1));
                        }
                    }
                    lastCoordinate = new Coordinate(x, y);
                    if (x++ == step) direction = Direction.UP;
                    break;
            }
            values.put(lastCoordinate, calculated);
            if (calculated > target) break;
            if ((square % (8 * sum + 1)) == 0) {
                step++;
                sum += step;
                direction = Direction.UP;
            }
        }
        System.out.println("Value = " + values.get(lastCoordinate) + ", coordinates = (" + lastCoordinate.x + ", " + lastCoordinate.y + ")");
    }
    
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    
    private class Coordinate {
        final int x, y;
        
        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Coordinate) {
                return (this.x == ((Coordinate) obj).x && this.y == ((Coordinate) obj).y); 
            } else return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }
    }
}
