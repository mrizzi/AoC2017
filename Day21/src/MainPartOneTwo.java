import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MainPartOneTwo {
    
    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
        mpo.processInstructions("Day21/resources/example" , 2);
        mpo.processInstructions("Day21/resources/input", 18);
    }

    private void processInstructions(String inputFilePath, int iterations) {
        try {
            List<String> rows = Files.readAllLines(Paths.get(inputFilePath));
            Map<String, String[]> rules = new HashMap<>();
            for (String rule : rows) {
                String[] tokens = rule.split(" => ");
                String[] input = tokens[0].split("/");
                String[] output = tokens[1].split("/");
                if (input.length > 2) {
                    rules.putAll(getRotatedPatterns(input[0], input[1], input[2], output));
                    // flip horizontally
                    rules.putAll(getRotatedPatterns(input[2], input[1], input[0], output));
                } else {
                    rules.putAll(getRotatedPatterns(input[0], input[1], output));
                    // flip horizontally
                    rules.putAll(getRotatedPatterns(input[1], input[0], output));
                }
            }
            String pattern = ".#...####";
            for (int i = 0; i < iterations; i++) {
                int size = (int) Math.sqrt(pattern.length());
                if (size % 2 == 0) {
                    StringBuilder[] outputPatterns = new StringBuilder[3];
                    String outputPattern = "";
                    for (int j = 0; j < size/2; j++) {
                        outputPatterns[0] = new StringBuilder();
                        outputPatterns[1] = new StringBuilder();
                        outputPatterns[2] = new StringBuilder();
                        for (int k = 0; k < size; k += 2) {
                            StringBuilder subPattern = new StringBuilder(pattern.substring(k + (j * size * 2), k + 2 + (j * size * 2)))
                                    .append(pattern.substring(k + size + (j * size * 2), k + size + 2 + (j * size * 2)));
                            outputPatterns[0].append(rules.get(subPattern.toString())[0]);
                            outputPatterns[1].append(rules.get(subPattern.toString())[1]);
                            outputPatterns[2].append(rules.get(subPattern.toString())[2]);
                        }
                        outputPattern += outputPatterns[0].toString() + outputPatterns[1].toString() + outputPatterns[2].toString();
                    }
                    pattern = outputPattern;
                } else {
                    StringBuilder[] outputPatterns = new StringBuilder[4];
                    String outputPattern = "";
                    for (int j = 0; j < size/3; j++) {
                        outputPatterns[0] = new StringBuilder();
                        outputPatterns[1] = new StringBuilder();
                        outputPatterns[2] = new StringBuilder();
                        outputPatterns[3] = new StringBuilder();
                        for (int k = 0; k < size; k += 3) {
                            StringBuilder subPattern = new StringBuilder(pattern.substring(k + (j * size * 3), k + 3 + (j * size * 3)))
                                    .append(pattern.substring(k + size + (j * size * 3), k + size + 3 + (j * size * 3)))
                                    .append(pattern.substring(k + 2 * size + (j * size * 3), k + 2 * size + 3 + (j * size * 3)));
                            outputPatterns[0].append(rules.get(subPattern.toString())[0]);
                            outputPatterns[1].append(rules.get(subPattern.toString())[1]);
                            outputPatterns[2].append(rules.get(subPattern.toString())[2]);
                            outputPatterns[3].append(rules.get(subPattern.toString())[3]);
                        }
                        outputPattern += outputPatterns[0].toString() + outputPatterns[1].toString() + outputPatterns[2].toString() + outputPatterns[3].toString();
                    }
                    pattern = outputPattern;
                }
                if (i == 4) System.out.println(inputFilePath + " :: Pixels on after 5 iterations = " + pattern.chars().filter(ch -> ch == '#').count());
            }
            System.out.println(inputFilePath + " :: Pixels on after " + iterations + " iterations= " + pattern.chars().filter(ch -> ch == '#').count());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Map<String, String[]> getRotatedPatterns(String row0, String row1, String[] output) {
        Map<String, String[]> patterns = new HashMap<>();
        StringBuilder basePattern = new StringBuilder(row0).append(row1);
        patterns.put(basePattern.toString(), output);
        // rotate 180° degrees
        patterns.putIfAbsent(basePattern.reverse().toString(), output);
        // rotate 90° degrees clockwise
        StringBuilder rotatePattern = new StringBuilder("" + row1.charAt(0))
                .append(row0.charAt(0))
                .append(row1.charAt(1))
                .append(row0.charAt(1));
        patterns.putIfAbsent(rotatePattern.toString(), output);
        // rotate -90° degrees clockwise
        patterns.putIfAbsent(rotatePattern.reverse().toString(), output);
        return patterns;
    }

    public Map<String, String[]> getRotatedPatterns(String row0, String row1, String row2, String[] output) {
        Map<String, String[]> patterns = new HashMap<>();
        StringBuilder basePattern = new StringBuilder(row0).append(row1).append(row2);
        patterns.put(basePattern.toString(), output);
        // rotate 180° degrees
        patterns.putIfAbsent(basePattern.reverse().toString(), output);
        // rotate 90° degrees clockwise
        StringBuilder rotatePattern = new StringBuilder("" + row2.charAt(0))
                .append(row1.charAt(0))
                .append(row0.charAt(0))
                .append(row2.charAt(1))
                .append(row1.charAt(1))
                .append(row0.charAt(1))
                .append(row2.charAt(2))
                .append(row1.charAt(2))
                .append(row0.charAt(2));
        patterns.putIfAbsent(rotatePattern.toString(), output);
        // rotate -90° degrees clockwise
        patterns.putIfAbsent(rotatePattern.reverse().toString(), output);
        return patterns;
    }

}
