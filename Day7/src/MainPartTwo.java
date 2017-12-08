import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MainPartTwo {

    public static void main(String[] args) {
        MainPartTwo mpo = new MainPartTwo();
        mpo.bottomProgram();
    }

    private void bottomProgram() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("Day7/resources/input"));
            List<Tower> towers = new ArrayList<>(lines.size());
            for (String line:lines) {
                String[] tokens = line.split(" ");
                Tower tower = new Tower(tokens[0], Integer.parseInt(tokens[1].substring(1, tokens[1].length() - 1)));
                int length = tokens.length;
                if (length > 2) {
                    for (int i = 3; i < (length - 1); i++) {
                        tower.addSubTowerName(tokens[i].substring(0, tokens[i].length() - 1));
                    }
                    tower.addSubTowerName(tokens[length - 1]);
                }
                towers.add(tower);
            }
            Tower bottom = findBottomProgram(towers);
            // calculate weight sums for every level
            getSubLevelsWeights(towers, bottom.name);
            System.out.println("Bottom program = " + bottom.name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Tower findBottomProgram(List<Tower> towers) {
        Tower bottom = null;
        int maxSubLevels = 0;
        for (Tower tower:towers) {
            int subLevels = getSubLevelsNumber(towers, tower.name);
            if (subLevels > maxSubLevels) {
                bottom = tower;
                maxSubLevels = subLevels;
            }
        }
        return bottom;
    }

    private int getSubLevelsNumber(List<Tower> towers, String towerName) {
        Tower tower = towers.get(towers.indexOf(new Tower(towerName, 0)));
        if (tower.hasSubTower()) {
            int maxSubLevels = 0;
            for (String subTower : tower.getSubTowerName()) {
                maxSubLevels = Math.max(maxSubLevels, getSubLevelsNumber(towers, subTower));
            }
            return 1 + maxSubLevels;
        }
        return 0;
    }

    private int getSubLevelsWeights(List<Tower> towers, String towerName) {
        Tower tower = towers.get(towers.indexOf(new Tower(towerName, 0)));
        if (tower.hasSubTower()) {
            Map<String, Integer> sumWeights = new HashMap<>();
            int subTowerSumWeights = 0;
            int totalSubTowerSumWeights = 0;
            int prevSubTowerSumWeights = 0;
            boolean differentFromPrevious = false;
            int differenceFromPrevious = 0;
            for (String subTower : tower.getSubTowerName()) {
                int subTowerSumWeight = getSubLevelsWeights(towers, subTower);
                totalSubTowerSumWeights += subTowerSumWeight;
                sumWeights.put(subTower, subTowerSumWeight);
                if (prevSubTowerSumWeights == 0) prevSubTowerSumWeights = subTowerSumWeight;
                if (!differentFromPrevious) differentFromPrevious = prevSubTowerSumWeights != subTowerSumWeight;
                if (differenceFromPrevious == 0) differenceFromPrevious = Math.abs(prevSubTowerSumWeights - subTowerSumWeight);
            }
            if (differentFromPrevious) {
                sumWeights.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v + " Weight : " + towers.get(towers.indexOf(new Tower(k, 0))).weight));
                List<Integer> values = new ArrayList<>(sumWeights.values());
                Collections.sort(values);
                if (!values.get(0).equals(values.get(1))) System.out.println("Change weight from " + values.get(0) + " to " + (values.get(0) + differenceFromPrevious));
                else System.out.println("Change weight from " + values.get(values.size() - 1) + " to " + (values.get(values.size() - 1) - differenceFromPrevious));
                System.exit(0);
            }
            return tower.weight + totalSubTowerSumWeights;
        }
        return tower.weight;
    }

    private class Tower {
        final String name;
        final int weight;
        final private List<String> subTowers;

        public Tower(String name, int weight) {
            this.name = name;
            this.weight = weight;
            this.subTowers = new ArrayList<>();
        }

        private void addSubTowerName(String name) {
            this.subTowers.add(name);
        }

        private List<String> getSubTowerName() {
            return this.subTowers;
        }

        private boolean hasSubTower() {
            return !subTowers.isEmpty();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Tower tower = (Tower) o;

            return name != null ? name.equals(tower.name) : tower.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }

}
