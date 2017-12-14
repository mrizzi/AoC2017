import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MainPartOne {

    public static void main(String[] args) {
        MainPartOne mpo = new MainPartOne();
        mpo.processStream("Day14/resources/example");
        mpo.processStream("Day14/resources/input");
    }

    private void processStream(String inputFilePath) {
        try {
            String key = Files.readAllLines(Paths.get(inputFilePath)).get(0);
            int squaresUsed = 0;
            for (int i = 0; i < 128; i++) {
                char[] hash = KnotHash.get(key + "-" + i).toCharArray();
                for (char c : hash) {
                    String binary = Integer.toBinaryString(Integer.parseInt("" + c, 16));
                    long count = binary.chars().filter(ch -> ch == '1').count();
                    squaresUsed += count;
                }
            }
            System.out.println(inputFilePath + " :: Squares used = " + squaresUsed);
            final AtomicInteger counter = new AtomicInteger(0);
            IntStream.range(0, 128).forEach(i -> 
                    KnotHash.get(key + "-" + i)
                    .chars()
                    .mapToObj(c ->
                            String.format("%4s", Integer.toBinaryString(Integer.parseInt("" + (char) c, 16))).replace(' ', '0'))
                    .forEach(str -> str.chars().filter(bit -> bit == '1').forEach(one -> counter.getAndIncrement())));
            System.out.println(inputFilePath + " :: Squares used (Stream approach) = " + counter.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
