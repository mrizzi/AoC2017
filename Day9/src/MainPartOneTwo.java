import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class MainPartOneTwo {

    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
        String exampleFilePath = "Day9/resources/example";
        for (int i = 0; i < 8; i++) {
            mpo.processStream(exampleFilePath + i);
        }
        mpo.processStream("Day9/resources/input");
    }
    
    private void processStream(String inputFilePath) {
        try {
            boolean insideGarbage = false;
            int score = 0;
            int totalScore = 0;
            boolean ignoreNext = false;
            int nonCanceledCharInGarbage = 0;
            String line = Files.readAllLines(Paths.get(inputFilePath)).get(0);
            for (char character:line.toCharArray()) {
                if (!ignoreNext) {
                    switch (character) {
                        case '!': {
                            ignoreNext = true;
                            break;
                        }
                        case '{': {
                            if (!insideGarbage) score++;
                            else nonCanceledCharInGarbage++;
                            break;
                        }
                        case '}': {
                            if (!insideGarbage) totalScore += score--;
                            else nonCanceledCharInGarbage++;
                            break;
                        }
                        case '<': {
                            if (!insideGarbage) insideGarbage = true;
                            else nonCanceledCharInGarbage++;
                            break;
                        }
                        case '>': {
                            insideGarbage = false;
                            break;
                        }
                        default: {
                            if (insideGarbage) nonCanceledCharInGarbage++;
                        }
                    }
                } else ignoreNext = false;
            }
            System.out.println("Total score = " + totalScore);
            System.out.println("Total non-canceled characters within garbage = " + nonCanceledCharInGarbage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
