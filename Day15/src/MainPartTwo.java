import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainPartTwo {

    public static void main(String[] args) {
        MainPartTwo mpo = new MainPartTwo();
        mpo.processStream("Day15/resources/example");
        mpo.processStream("Day15/resources/input");
    }

    private void processStream(String inputFilePath) {
        try {
            List<String> startingValues = Files.readAllLines(Paths.get(inputFilePath));
            long valueA = Long.parseLong(startingValues.get(0).split(" with ")[1]);
            long valueB = Long.parseLong(startingValues.get(1).split(" with ")[1]);
            int finalCount = 0;
            int pairsToGenerate = 5000000;
            for (int pairsGenerated = 0; pairsGenerated < pairsToGenerate;) {
                valueA = (valueA * 16807L) % 2147483647L;
                // make everything 32 bit long before taking the 16 lowest bits
                String binaryA = String.format("%32s", Long.toBinaryString(valueA)).replace(' ', '0').substring(16);
                if (binaryA.endsWith("00")) {
                    while (true){
                        valueB = (valueB * 48271L) % 2147483647L;
                        String binaryB = String.format("%32s", Long.toBinaryString(valueB)).replace(' ', '0').substring(16);
                        if (binaryB.endsWith("000")) {
                            pairsGenerated++;
                            if (binaryA.equals(binaryB)) finalCount++;
                            break;
                        }
                    }
                }
            }
            System.out.println(inputFilePath + " :: Judge's final count = " + finalCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
