import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainPartTwo implements Runnable {
    
    private final Long id;
    private final String inputFilePath;
    private final Map<String, Long> register = new HashMap<>();
    private final Queue<Long> queueIn;
    private final Queue<Long> queueOut;
    private boolean waiting = false;
    private boolean stop = false;
    private int valuesSent = 0;

    public MainPartTwo(Long id, String inputFilePath, Queue<Long> queueIn, Queue<Long> queueOut) {
        this.id = id;
        this.inputFilePath = inputFilePath;
        this.queueIn = queueIn;
        this.queueOut = queueOut;
    }
    
    public static void main(String[] args) {
        Queue<Long> queueA = new ConcurrentLinkedQueue<>();
        Queue<Long> queueB = new ConcurrentLinkedQueue<>();
        MainPartTwo mpoA = new MainPartTwo(0L, "Day18/resources/input", queueA, queueB);
        Thread threadA = new Thread(mpoA);
        threadA.start();
        MainPartTwo mpoB = new MainPartTwo(1L, "Day18/resources/input", queueB, queueA);
        Thread threadB = new Thread(mpoB);
        threadB.start();
        while (true) {
            if (mpoA.isWaiting() && mpoB.isWaiting()) {
                mpoA.stop();
                mpoB.stop();
                break;
            }
            else if (!threadA.isAlive() && !threadB.isAlive()) break;
        }
        System.out.println("Program 1 send a value " + mpoB.getValuesSent() + " times.");
    }
    
    public void run() {
        this.processInstructions();
    }

    private void processInstructions() {
        try {
            List<String> instructions = Files.readAllLines(Paths.get(this.inputFilePath));
            register.put("p", id);
            for (int  i = 0; i < instructions.size();) {
                String[] tokens = instructions.get(i).split(" ");
                String instruction = tokens[0];
                String firstArgument = tokens[1];
                String secondArgument = tokens.length > 2 ? tokens[2] : null;
                boolean jump = false;
                switch (instruction) {
                    case "snd":
                        valuesSent++;
                        queueOut.add(getValue(firstArgument));
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
                        if (queueIn.isEmpty()) {
                            waiting = true;
                            while (waiting && !stop) {
                                waiting = queueIn.isEmpty();
                            }
                        }
                        if (!stop) register.put(firstArgument, queueIn.remove());
                        break;
                    case "jgz":
                        if (getValue(firstArgument) > 0) {
                            jump = true;
                            i += getValue(secondArgument);
                        }
                        break;
                }
                if (this.stop) break;
                if (!jump) i++;
            }
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
    
    public boolean isWaiting() {
        return this.waiting;
    }
    
    public int getValuesSent() {
        return this.valuesSent;
    }
    
    public void stop() {
        this.stop = true;
    }
}
