import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainPartOne {

    public static void main(String[] args) {
        MainPartOne mpo = new MainPartOne();
        mpo.processStream("Day15/resources/example");
        mpo.processStream("Day15/resources/input");
    }

    private void processStream(String inputFilePath) {
        try {
            List<String> startingValues = Files.readAllLines(Paths.get(inputFilePath));
            long valueA = Long.parseLong(startingValues.get(0).split(" with ")[1]);
            long valueB = Long.parseLong(startingValues.get(1).split(" with ")[1]);
            int finalCount = 0;
            for (int i = 0; i < 40000000; i++) {
                valueA = (valueA * 16807L) % 2147483647L;
                valueB = (valueB * 48271L) % 2147483647L;
                // make everything 32 bit long before taking the 16 lowest bits
                String binaryA = String.format("%32s", Long.toBinaryString(valueA)).replace(' ', '0').substring(16);
                String binaryB = String.format("%32s", Long.toBinaryString(valueB)).replace(' ', '0').substring(16);
                if (binaryA.equals(binaryB)) finalCount++;
            }
            System.out.println(inputFilePath + " :: Judge's final count = " + finalCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
