import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MainPartOneTwo {
    
    private int highestValue = 0;

    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
        mpo.executedInstructions();
    }
    
    private void executedInstructions() {
        try {
            Map<String, Integer> registers = new HashMap<>();
            Files.readAllLines(Paths.get("Day8/resources/input"))
                    .stream()
                    .map(line -> Instruction.create(line).execute(registers))
                    .forEach(value -> highestValue = Math.max(highestValue, value));
            System.out.println("Largest value in any register : " + 
                    registers.entrySet()
                            .stream()
                            .max(Map.Entry.comparingByValue())
                            .get());
            System.out.println("Highest value in any register during execution: " + highestValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Operation {inc, dec}
}
