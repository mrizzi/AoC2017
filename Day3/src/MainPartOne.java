public class MainPartOne {

    public static void main(String[] args) {
        int target = 3;
//        int target = 23;
//        int target = 312051;
//        for (int i = 1; i <= target; i++) {
            int x = 0, y = 0;
            int step = 0;
            int sum = step;
            int square = 1;
            Direction direction = Direction.LEFT;
            while (true) {
//                if (i == square++) break;
                if (target == square++) break;
                if ((square % (8 * sum + 1)) == 0) {
                    step++;
                    sum += step;
                    direction = Direction.RIGHT;
                }
                switch (direction) {
                    case UP:
                        if (Math.abs(++y) == step) direction = Direction.LEFT;
                        break;
                    case LEFT:
                        if (Math.abs(--x) == step) direction = Direction.DOWN;
                        break;
                    case DOWN:
                        if (Math.abs(--y) == step) direction = Direction.RIGHT;
                        break;
                    case RIGHT:
                        if (Math.abs(++x) == step) direction = Direction.UP;
                        break;
                }
            }
            System.out.println("target = " + target + ", distance = " + (Math.abs(x) + Math.abs(y)) + ", coordinates = (" + x + ", " + y + ")");
//        }

    }
    
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
