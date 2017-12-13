import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainPartTwo {

    public static void main(String[] args) {
        MainPartTwo mpo = new MainPartTwo();
        mpo.processStream("Day13/resources/example");
        mpo.processStream("Day13/resources/input");
    }

    private void processStream(String inputFilePath) {
        try {
            List<String> records = Files.readAllLines(Paths.get(inputFilePath));
            int layers = Integer.parseInt(records.get(records.size() - 1).split(":")[0]) + 1;
            int recordPointer = 0;
            int delay = 0;
            boolean caught = false;
            while (true) {
                for (int i = 0; i < layers; i++) {
                    String[] tokens = records.get(recordPointer).split(": ");
                    int depth = Integer.parseInt(tokens[0]);
                    int range = Integer.parseInt(tokens[1]);
                    if (depth == i) {
                        recordPointer++;
                        if (((i + delay) / (range - 1)) % 2 == 0 && (i + delay) % (range - 1) == 0) {
                            caught = true;
                            break;
                        }
                    }
                }
                if (!caught) {
                    System.out.println(inputFilePath + " :: Delay = " + delay);
                    break;
                }
                recordPointer = 0;
                caught = false;
                delay++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
