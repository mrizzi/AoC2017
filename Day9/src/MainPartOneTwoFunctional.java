import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class MainPartOneTwoFunctional {

    public static void main(String[] args) {
        MainPartOneTwoFunctional mpo = new MainPartOneTwoFunctional();
        String exampleFilePath = "Day9/resources/example";
        for (int i = 0; i < 8; i++) {
            mpo.processStream(exampleFilePath + i);
        }
        mpo.processStream("Day9/resources/input");
    }
    
    private void processStream(String inputFilePath) {
        try {
            Function<String, Integer> processTotalScore = line -> totalScore(line);
            System.out.println("Total score = " + processTotalScore.apply(Files.readAllLines(Paths.get(inputFilePath)).get(0)));
            Function<String, Integer> processNonCanceledCharacters = line -> nonCanceledCharacters(line);
            System.out.println("Total non-canceled characters within garbage = " + processNonCanceledCharacters.apply(Files.readAllLines(Paths.get(inputFilePath)).get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Integer totalScore(String line) {
        boolean insideGarbage = false;
        int score = 0;
        int totalScore = 0;
        boolean ignoreNext = false;
        for (char character:line.toCharArray()) {
            if (!ignoreNext) {
                switch (character) {
                    case '!': {
                        ignoreNext = true;
                        break;
                    }
                    case '{': {
                        if (!insideGarbage) score++;
                        break;
                    }
                    case '}': {
                        if (!insideGarbage) totalScore += score--;
                        break;
                    }
                    case '<': {
                        if (!insideGarbage) insideGarbage = true;
                        break;
                    }
                    case '>': {
                        insideGarbage = false;
                        break;
                    }
                    default: {
                    }
                }
            } else ignoreNext = false;
        }
        return totalScore;
    }

    private Integer nonCanceledCharacters(String line) {
        boolean insideGarbage = false;
        boolean ignoreNext = false;
        int nonCanceledCharInGarbage = 0;
        for (char character:line.toCharArray()) {
            if (!ignoreNext) {
                switch (character) {
                    case '!': {
                        ignoreNext = true;
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
        return nonCanceledCharInGarbage;
    }

}
