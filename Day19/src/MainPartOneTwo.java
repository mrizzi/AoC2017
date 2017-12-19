import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainPartOneTwo {
    
    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
        mpo.processInstructions("Day19/resources/example");
        mpo.processInstructions("Day19/resources/input");
    }

    private void processInstructions(String inputFilePath) {
        try {
            List<String> rows = Files.readAllLines(Paths.get(inputFilePath));
            int row = 0;
            int column = rows.get(row++).indexOf("|");
            Orientation orientation = Orientation.VERTICAL;
            Sense sense = Sense.NEGATIVE;
            StringBuilder lettersSeen = new StringBuilder();
            boolean followThePath = true;
            int steps = 0;
            while (followThePath) {
                steps++;
                char step = rows.get(row).charAt(column);
                switch (step) {
                    case ' ':
                        followThePath = false;
                        break;
                    case '+':
                        if (orientation.equals(Orientation.VERTICAL)) {
                            orientation = Orientation.HORIZONTAL;
                            if (rows.get(row).charAt(column - 1) != ' ') {
                                sense = Sense.NEGATIVE;
                                column--;
                            } else {
                                sense = Sense.POSITIVE;
                                column++;
                            }
                        } else {
                            orientation = Orientation.VERTICAL;
                            if (rows.get(row - 1).charAt(column) != ' ') {
                                sense = Sense.POSITIVE;
                                row--;
                            } else {
                                sense = Sense.NEGATIVE;
                                row++;
                            }
                        }
                        break;
                    default:
                        if (!(step == '|' || step == '-')) lettersSeen.append(step);
                        if (orientation.equals(Orientation.VERTICAL)) {
                            if (sense.equals(Sense.NEGATIVE)) row++;
                            else row--;
                        } else {
                            if (sense.equals(Sense.NEGATIVE)) column--;
                            else column++;
                        }
                }
            }
            System.out.println(inputFilePath + " :: Letters seen in the path = " + lettersSeen);
            System.out.println(inputFilePath + " :: Steps # = " + steps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private enum Orientation {
        VERTICAL, HORIZONTAL
    }
    
    private enum Sense {
        POSITIVE, NEGATIVE
    }

}
