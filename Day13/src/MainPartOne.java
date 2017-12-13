import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainPartOne {

    public static void main(String[] args) {
        MainPartOne mpo = new MainPartOne();
        mpo.processStream("Day13/resources/example");
        mpo.processStream("Day13/resources/input");
    }

    private void processStream(String inputFilePath) {
        try {
            List<String> records = Files.readAllLines(Paths.get(inputFilePath));
            int severity = 0;
            int layers = Integer.parseInt(records.get(records.size() - 1).split(":")[0]) + 1;
            int recordPointer = 0;
            for (int i = 0; i < layers; i++) {
                String[] tokens = records.get(recordPointer).split(": ");
                int depth = Integer.parseInt(tokens[0]);
                int range = Integer.parseInt(tokens[1]);
                if (depth == i) {
                    recordPointer++;
                    if ((i / (range - 1)) % 2 == 0 && i % (range - 1) == 0) {
                        severity += depth * range;
                    }
                }
            }
            System.out.println(inputFilePath + " :: Severity = " + severity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
