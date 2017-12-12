import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainPartTwo {
    
    private List<String> lines;
    private Set<Integer> group = new HashSet<>();
    private Set<Integer> visitedPrograms = new HashSet<>();
    
    public MainPartTwo(String inputFilePath) {
        try {
            this.lines = Files.readAllLines(Paths.get(inputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainPartTwo example = new MainPartTwo("Day12/resources/example");
        example.processStream();
        MainPartTwo input = new MainPartTwo("Day12/resources/input");
        input.processStream();
    }
    
    private void processStream() {
        int groups = 0;
        for (String line : lines) {
            group.clear();
            int element = Integer.parseInt(line.split(" <-> ")[0]);
            if (!visitedPrograms.contains(element)) {
                groups++;
                traverseRecord(lines.get(element));
                visitedPrograms.addAll(group);
            }
        }
        System.out.println("Groups = " + groups);
    }
    
    private void traverseRecord(String line) {
        String[] components = line.split(" <-> ");
        group.add(Integer.parseInt(components[0]));
        for (String connectedTo : components[1].split(", ")) {
            if (!group.contains(Integer.parseInt(connectedTo))) traverseRecord(lines.get(Integer.parseInt(connectedTo)));
        }
    }

}
