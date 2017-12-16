import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MainPartOneTwo {

    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
//        mpo.processStream("Day16/resources/example");
        mpo.processStream("Day16/resources/input");
    }

    private void processStream(String inputFilePath) {
        try {
            String[] moves = Files.readAllLines(Paths.get(inputFilePath)).get(0).split(",");
//            StringBuilder dancers = new StringBuilder("abcde");
            StringBuilder dancers = new StringBuilder("abcdefghijklmnop");
            int length = dancers.length();
            Map<String, String> cache = new HashMap<>();
            for (int j = 0; j < 1000000000; j++) {
                if (cache.containsKey(dancers.toString())) {
                    dancers = new StringBuilder(cache.get(dancers.toString()));
                } else {
                    String key = dancers.toString();
                    for (int i = 0; i < moves.length; i++) {
                        switch (moves[i].charAt(0)) {
                            case 's':
                                dancers = new StringBuilder(dancers.insert(0, dancers.substring(length - Integer.parseInt("" + moves[i].substring(1)))).substring(0, length));
                                break;
                            case 'x':
                                MainPartOneTwo.swap(dancers, Integer.parseInt("" + moves[i].substring(1, moves[i].indexOf('/'))), Integer.parseInt("" + moves[i].substring(moves[i].indexOf('/') + 1)));
                                break;
                            case 'p':
                                MainPartOneTwo.swap(dancers, dancers.indexOf(String.valueOf(moves[i].charAt(1))), dancers.indexOf(String.valueOf(moves[i].charAt(3))));
                                break;
                        }
                    }
                    cache.put(key, dancers.toString());
                }
                if (j == 0) System.out.println(inputFilePath + " :: Programs standing (Part One) = " + dancers);
            }
            System.out.println(inputFilePath + " :: Programs standing = " + dancers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void swap(StringBuilder sb, int first, int second) {
        char toSwap = sb.charAt(first);
        sb.setCharAt(first, sb.charAt(second));
        sb.setCharAt(second, toSwap);
    }

}
