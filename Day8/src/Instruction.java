import java.util.Map;

public class Instruction {
    private final String register;
    private final MainPartOneTwo.Operation operation;
    private final int value;
    private final String conditionVariable;
    private final String conditionComparison;
    private final int conditionValue;

    private Instruction(String instruction) {
        String[] components = instruction.split(" ");
        register = components[0];
        operation = MainPartOneTwo.Operation.valueOf(components[1]);
        value = Integer.parseInt(components[2]);
        conditionVariable = components[4];
        conditionComparison = components[5];
        conditionValue = Integer.parseInt(components[6]);
    }

    public static Instruction create(String line) {
        return new Instruction(line);
    }

    public int execute(Map<String, Integer> registers) {
        if (conditionVerified(registers)) {
            registers.put(register, executeOperation(registers));
            return registers.get(register);
        }
        return -1;
    }

    private boolean conditionVerified(Map<String, Integer> registers) {
        int conditionVariableValue = registers.get(conditionVariable) != null ? registers.get(conditionVariable) : 0;
        switch (conditionComparison) {
            case "<" : return conditionVariableValue < conditionValue;
            case "<=" : return conditionVariableValue <= conditionValue;
            case ">" : return conditionVariableValue > conditionValue;
            case ">=" : return conditionVariableValue >= conditionValue;
            case "==" : return conditionVariableValue == conditionValue;
            case "!=" : return conditionVariableValue != conditionValue;
            default: return false;
        }
    }

    private int executeOperation(Map<String, Integer> registers) {
        int registerValue = registers.get(register) != null ? registers.get(register) : 0;
        switch (operation) {
            case dec: {
                registerValue -= value;
                break;
            }
            case inc: {
                registerValue += value;
                break;
            }
        }
        return registerValue;
    }
}
