import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class MainPartOne {

    public static void main(String[] args) {
        MainPartOne mpo = new MainPartOne();
        mpo.knotHash("Day10/resources/input");
    }
    
    private void knotHash(String inputFilePath) {
        try {
            int listLength = 256;
            int[] list = Stream.iterate(0, n -> n + 1).limit(listLength).mapToInt(Integer::intValue).toArray();
            int[] lengths = Arrays.stream(Files.readAllLines(Paths.get(inputFilePath)).get(0).split(",")).mapToInt(Integer::parseInt).toArray();
            int position = 0, skip = 0;
            for (int length:lengths) {
                for (int offset = 0; offset < length / 2; offset++) {
                    int tmp = list[(position + offset) % listLength];
                    list[(position + offset) % listLength] = list[(position + length - 1 - offset) % listLength];
                    list[(position + length - 1 - offset) % listLength] = tmp;
                }
                position = (position + length + skip++) % listLength;
            }
            System.out.println("Result of multiplying the first two numbers in the list = " + (list[0] * list[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
