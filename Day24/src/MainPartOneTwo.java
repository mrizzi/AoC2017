import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MainPartOneTwo {
    
    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
        mpo.processMap("Day24/resources/example" );
        mpo.processMap("Day24/resources/input");
    }

    private void processMap(String inputFilePath) {
        try {
            List<String> inventory = Files.readAllLines(Paths.get(inputFilePath));
            Inventory<Component> components = new Inventory<>();
            inventory.stream().forEach(component -> components.addComponent(new Component(component)));
            System.out.println(inputFilePath + " :: Strength of the strongest bridge = " + components.findStrengthOfStrongestBridge());
            System.out.println(inputFilePath + " :: Strength of the longest bridge = " + components.findStrengthOfLongestBridge());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public  class Inventory<T extends Component> {
        Set<T> components = new HashSet<>();
        
        public boolean addComponent(T component) {
            return components.add(component);
        }

        private Set<T> getComponentsUnusedWithPort(int portType) {
            return components.stream().filter(component -> component.hasPort(portType) && !component.used).collect(Collectors.toSet());
        }
        
        public int findStrengthOfStrongestBridge() {
            return findStrengthOfStrongestBridge(0);
        }
        
        private int findStrengthOfStrongestBridge(int startingPortType) {
            int maxStrength = 0;
            for (T component : getComponentsUnusedWithPort(startingPortType)) {
                component.used = true;
                maxStrength = Math.max(maxStrength, component.getStrength() + findStrengthOfStrongestBridge(component.getOtherPort(startingPortType)));
                component.used = false;
            }
            return maxStrength;
        }
        
        public int findStrengthOfLongestBridge() {
            Set<T> bridge = findComponentOfLongestBridge(0);
            return bridge.stream().mapToInt(component -> component.getStrength()).sum();
        }

        private Set<T> findComponentOfLongestBridge(int startingPortType) {
            int maxLength = 0;
            int maxStrength = 0;
            Set<T> bridge = new HashSet<>();
            for (T component : getComponentsUnusedWithPort(startingPortType)) {
                component.used = true;
                Set<T> nextBridge = findComponentOfLongestBridge(component.getOtherPort(startingPortType));
                int nextBridgeStrength = nextBridge.stream().mapToInt(komponent -> komponent.getStrength()).sum();
                if (maxLength < 1 + nextBridge.size() ||
                        (maxLength == 1 + nextBridge.size() && 
                        maxStrength < (component.getStrength() + nextBridgeStrength))) {
                    bridge.clear();
                    bridge.add(component);
                    bridge.addAll(nextBridge);
                    maxLength = bridge.size();
                    maxStrength = component.getStrength() + nextBridgeStrength;
                }
                component.used = false;
            }
            return bridge;
        }
    }

    public class Component {
        final List<Integer> portsType;
        boolean used = false;
        
        public Component(String portsType) {
            this.portsType = Arrays.stream(portsType.split("/")).map(portType -> Integer.parseInt(portType)).collect(Collectors.toList());
        }
        
        public boolean hasPort(int portType) {
            return portsType.contains(portType);
        }
        
        public int getOtherPort(int portType) {
            return portsType.get((portsType.indexOf(portType) + 1) % 2);
        }
        
        public int getStrength() {
            return portsType.stream().mapToInt(Integer::intValue).sum();
        }

    }

}
