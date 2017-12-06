import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainPartOne {

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("Day5/resources/input"));
            // approach #1: go through all the data (memory consumption)
            int size = lines.size();
            int[] jumps = new int[size];
            for (int i = 0; i < size; i++) {
                jumps[i] = Integer.parseInt(lines.get(i));
            }
            // execute
            int steps = 0;
            for (int next = 0; next < size; steps++) {
                next += jumps[next]++;
            }
            System.out.println("Steps = " + steps);
            // approach #2: "in place" step through the data used in the jumps from the List<String>
            steps = 0;
            for (int next = 0; next < size; steps++) {
                int jump = Integer.parseInt(lines.get(next));
                lines.set(next, Integer.toString(jump + 1));
                next += jump;
            }
            System.out.println("Steps = " + steps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
