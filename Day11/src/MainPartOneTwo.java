import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class MainPartOneTwo {

    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
        String exampleFilePath = "Day11/resources/example";
        for (int i = 0; i < 4; i++) {
            mpo.processStream(exampleFilePath + i);
        }
        mpo.processStream("Day11/resources/input");
    }
    
    private void processStream(String inputFilePath) {
        try {
            String[] directions = Files.readAllLines(Paths.get(inputFilePath)).get(0).split(",");
            int childX = 0;
            int childY = 0;
            int maxDistance = 0;
            for (String direction : directions) {
                switch (direction) {
                    case "n":
                        childY++;
                        break;
                    case "ne":
                        childX++;
                        childY++;
                        break;
                    case "se":
                        childX++;
                        break;
                    case "s":
                        childY--;
                        break;
                    case "sw":
                        childX--;
                        childY--;
                        break;
                    case "nw":
                        childX--;
                        break;
                }
                maxDistance = Math.max(maxDistance, Math.max(Math.abs(childX), Math.abs(childY)));
            }
            System.out.println(inputFilePath + " :: Number of steps required = " + Math.max(Math.abs(childX), Math.abs(childY)));
            System.out.println(inputFilePath + " :: Max number of steps required = " + maxDistance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
