import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MainPartTwo {
    
    public static void main(String[] args) {
        int b = 109900;
        int c = b + 17000;
        AtomicInteger notPrimeNumbers = new AtomicInteger();
        IntStream.iterate(b, i -> i + 17).limit((c - b) / 17 + 1).forEach(number -> {if (!isPrime(number)) notPrimeNumbers.getAndIncrement();});
        System.out.println("Value in `h` register = " + notPrimeNumbers.get());
    }

    public static boolean isPrime(int number) {
        return IntStream.rangeClosed(2, number/2).noneMatch(i -> number%i == 0);
    }

}
