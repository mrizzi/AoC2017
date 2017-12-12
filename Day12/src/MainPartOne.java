import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainPartOne {
    
    private List<String> lines;
    private final Set<Integer> group = new HashSet<>();
    
    public MainPartOne(String inputFilePath) {
        try {
            this.lines = Files.readAllLines(Paths.get(inputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainPartOne example = new MainPartOne("Day12/resources/example");
        example.processStream();
        MainPartOne input = new MainPartOne("Day12/resources/input");
        input.processStream();
    }
    
    private void processStream() {
        traverseRecord(lines.get(0));
        System.out.println("Programs in the group that contains program ID 0 = " + group.size());
    }
    
    private void traverseRecord(String line) {
        String[] components = line.split(" <-> ");
        group.add(Integer.parseInt(components[0]));
        for (String connectedTo : components[1].split(", ")) {
            if (!group.contains(Integer.parseInt(connectedTo))) traverseRecord(lines.get(Integer.parseInt(connectedTo)));
        }
    }

}
