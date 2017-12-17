import java.util.ArrayList;
import java.util.List;

public class MainPartOne {

    public static void main(String[] args) {
        MainPartOne mpo = new MainPartOne();
        mpo.processCircularBuffer(3);
        mpo.processCircularBuffer(371);
    }

    private void processCircularBuffer(int stepForward) {
        int currentPosition = 0;
        List<Integer> circularBuffer = new ArrayList<>();
        for (int i = 0; i < 2018; i++) {
            circularBuffer.add(currentPosition, i);
            currentPosition = ((currentPosition + stepForward) % circularBuffer.size()) + 1;
        }
        System.out.println("Steps forward : " + stepForward + " ==> Value after '2017' = " + circularBuffer.get(circularBuffer.indexOf(2017) + 1));
    }

}
