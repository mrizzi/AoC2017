import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MainPartTwo {

    public static void main(String[] args) {
        MainPartTwo mpo = new MainPartTwo();
        mpo.processCircularBuffer(371);
    }

    private void processCircularBuffer(int stepForward) {
        int currentPosition = 0;
        int numElements = 50000000;
        int valueAfterZero = 0;
        for (int i = 0; i < (numElements + 1);) {
            if (currentPosition == 1) valueAfterZero = i;
            currentPosition = ((currentPosition + stepForward) % ++i) + 1;
        }
        System.out.println("Value after '0' = " + valueAfterZero);

        // 2nd approach
        AtomicInteger intAfterZero = new AtomicInteger(0);
        AtomicInteger intCurrentPosition = new AtomicInteger(0);
        IntStream.range(0, numElements + 1).forEach(i -> {
            if (intCurrentPosition.get() == 1) intAfterZero.set(i);
            intCurrentPosition.set(((intCurrentPosition.get() + stepForward) % ++i) + 1);
        });
        System.out.println("Value after '0' (2nd approach) = " + intAfterZero.get());
    }

}
