import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainPartTwo {

    public static void main(String[] args) {
        try {
            long total1 = 0;
            long total2 = 0;
            // change the # of executions to do a performance test
            int executions = 1;
            for (int execution = 0; execution < executions; execution++) {
                List<String> lines = Files.readAllLines(Paths.get("Day5/resources/input"));
                int size = lines.size();
                // approach #1: go through all the data (memory consumption) but more well-performing
                long start = System.currentTimeMillis();
                int[] jumps = new int[size];
                for (int i = 0; i < size; i++) {
                    jumps[i] = Integer.parseInt(lines.get(i));
                }
                // execute
                int steps = 0;
                // you can choose the "if-else" approach or the "ternary" one
                for (int next = 0; next < size; steps++) {
/*                int jump = jumps[next];
                if (jump >= 3) next += jumps[next]--;
                else next += jumps[next]++;*/
                    next += jumps[next] >= 3 ? jumps[next]-- : jumps[next]++;
                }
                total1 += (System.currentTimeMillis() - start);
                System.out.println("Steps 1 = " + steps);
                // approach #2: "in place" step through the data used in the jumps from the List<String>
                steps = 0;
                start = System.currentTimeMillis();
                for (int next = 0; next < size; steps++) {
                    int jump = Integer.parseInt(lines.get(next));
                    lines.set(next, Integer.toString(jump >= 3 ? jump - 1 : jump + 1));
                    next += jump;
                }
                total2 += (System.currentTimeMillis() - start);
                System.out.println("Steps 2 = " + steps);
            }
            System.out.println("Average 1 = " + (total1/executions));
            System.out.println("Average 2 = " + (total2/executions));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
