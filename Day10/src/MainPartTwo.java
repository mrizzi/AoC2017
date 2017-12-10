import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class MainPartTwo {

    public static void main(String[] args) {
        MainPartTwo mpo = new MainPartTwo();
        mpo.knotHash("Day10/resources/exampleAoC2017");
        mpo.knotHash("Day10/resources/input");
    }
    
    private void knotHash(String inputFilePath) {
        try {
            int listLength = 256;
            int[] list = Stream.iterate(0, n -> n + 1).limit(listLength).mapToInt(Integer::intValue).toArray();
            char[] inputLengths = Files.readAllLines(Paths.get(inputFilePath)).get(0).toCharArray();
            int[] lengths = new int[inputLengths.length + 5];
            for (int j = 0; j < inputLengths.length; j++) {
                lengths[j] = inputLengths[j];
            }
            lengths[inputLengths.length] = 17;
            lengths[inputLengths.length + 1] = 31;
            lengths[inputLengths.length + 2] = 73;
            lengths[inputLengths.length + 3] = 47;
            lengths[inputLengths.length + 4] = 23;
            int position = 0, skip = 0;
            for (int i = 0; i < 64; i++) {
                for (int length : lengths) {
                    for (int offset = 0; offset < length / 2; offset++) {
                        int tmp = list[(position + offset) % listLength];
                        list[(position + offset) % listLength] = list[(position + length - 1 - offset) % listLength];
                        list[(position + length - 1 - offset) % listLength] = tmp;
                    }
                    position = (position + length + skip++) % listLength;
                }
            }
            StringBuilder result = new StringBuilder(32);
            for (int k = 0; k < 16; k++) {
                String toAppend = Integer.toHexString(
                                    list[0 + (k * 16)] ^
                                    list[1 + (k * 16)] ^
                                    list[2 + (k * 16)] ^
                                    list[3 + (k * 16)] ^
                                    list[4 + (k * 16)] ^
                                    list[5 + (k * 16)] ^
                                    list[6 + (k * 16)] ^
                                    list[7 + (k * 16)] ^
                                    list[8 + (k * 16)] ^
                                    list[9 + (k * 16)] ^
                                    list[10 + (k * 16)] ^
                                    list[11 + (k * 16)] ^
                                    list[12 + (k * 16)] ^
                                    list[13 + (k * 16)] ^
                                    list[14 + (k * 16)] ^
                                    list[15 + (k * 16)]
                                    );
                if (toAppend.length() == 1) toAppend = "0" + toAppend;
                result.append(toAppend);
            }
            System.out.println("Knot Hash for " + inputFilePath + " = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
