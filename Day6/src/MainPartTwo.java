import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

public class MainPartTwo {

    public static void main(String[] args) {
        MainPartTwo mpo = new MainPartTwo();
        mpo.allocationRoutine();
    }

    private void allocationRoutine() {
        try {
            String[] blocks = Files.readAllLines(Paths.get("Day6/resources/input")).get(0).split("\t");
            int size = blocks.length;
            int[] banks = new int[size];
            for (int i = 0; i < size; i++) {
                banks[i] = Integer.parseInt(blocks[i]);
            }
            // execute
            int cycles = 0;
            Map<BanksAllocation, Integer> banksAllocations = new HashMap<>();
            banksAllocations.put(new BanksAllocation(banks), cycles);
            int result = 0;
            while (true) {
                int position = IntStream.range(0, size)
                        .filter(i -> banks[i] == Arrays.stream(banks).max().getAsInt())
                        .findFirst()
                        .getAsInt();
                int max = Arrays.stream(banks).max().getAsInt();
                banks[position] = 0;
                for (; max > 0; max--) {
                    banks[++position%size]++;
                }
                cycles++;
                if (banksAllocations.putIfAbsent(new BanksAllocation(banks), cycles) != null) {
                    result = cycles - banksAllocations.get(new BanksAllocation(banks)).intValue();
                    break;
                }
            }
            System.out.println("Cycles = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class BanksAllocation {

        private final int[] banksAllocation;

        public BanksAllocation(int[] banksAllocation) {
            this.banksAllocation = banksAllocation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BanksAllocation that = (BanksAllocation) o;

            return Arrays.equals(banksAllocation, that.banksAllocation);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(banksAllocation);
        }
    }
}
