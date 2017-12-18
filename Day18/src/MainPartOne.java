import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPartOne {
    
    private Map<String, Long> register = new HashMap<>();
    
    public static void main(String[] args) {
        MainPartOne mpoExample = new MainPartOne();
        mpoExample.processInstructions("Day18/resources/example");
        MainPartOne mpo = new MainPartOne();
        mpo.processInstructions("Day18/resources/input");
    }

    private void processInstructions(String inputFilePath) {
        try {
            List<String> instructions = Files.readAllLines(Paths.get(inputFilePath));
            long lastSoundPlayed = 0;
            for (int  i = 0; i < instructions.size();) {
                String[] tokens = instructions.get(i).split(" ");
                String instruction = tokens[0];
                String firstArgument = tokens[1];
                String secondArgument = tokens.length > 2 ? tokens[2] : null;
                boolean recoverExecuted = false;
                boolean jump = false;
                switch (instruction) {
                    case "snd":
                        lastSoundPlayed = getValue(firstArgument);
                        break;
                    case "set":
                        register.put(firstArgument, getValue(secondArgument));
                        break;
                    case "add":
                        register.put(firstArgument, getValue(firstArgument) + getValue(secondArgument));
                        break;
                    case "mul":
                        register.put(firstArgument, getValue(firstArgument) * getValue(secondArgument));
                        break;
                    case "mod":
                        register.put(firstArgument, getValue(firstArgument) % getValue(secondArgument));
                        break;
                    case "rcv":
                        if (getValue(firstArgument) > 0) recoverExecuted = true;
                        break;
                    case "jgz":
                        if (getValue(firstArgument) > 0) {
                            jump = true;
                            i += getValue(secondArgument);
                        }
                        break;
                }
                if (recoverExecuted) break;
                if (!jump) i++;
            }
            System.out.println(inputFilePath + " :: Value of the recovered frequency = " + lastSoundPlayed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private long getValue(String argument) {
        long toReturn = 0;
        try {
            toReturn = Long.parseLong(argument, 10);
        } catch (NumberFormatException e) {
            if (register.containsKey(argument)) toReturn = register.get(argument);
            else register.put(argument, toReturn);
        }
        return toReturn;
    }

}
