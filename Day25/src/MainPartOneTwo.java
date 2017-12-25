import java.util.*;

public class MainPartOneTwo {

    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
        mpo.processBlueprint();
    }

    private void processBlueprint() {
        EndlessTape tape = new EndlessTape();
        int cursor = 0;
        State state = State.A;
        int steps = 12964419;
        for (int i = 0; i < steps; i++) {
            switch (state) {
                case A:
                    if (!tape.get(cursor)) {
                        tape.set(cursor, true);
                        cursor++;
                        state = State.B;
                    } else {
                        tape.set(cursor, false);
                        cursor++;
                        state = State.F;
                    }
                    break;
                case B:
                    if (!tape.get(cursor)) {
                        tape.set(cursor, false);
                        cursor--;
                        state = State.B;
                    } else {
                        tape.set(cursor, true);
                        cursor--;
                        state = State.C;
                    }
                    break;
                case C:
                    if (!tape.get(cursor)) {
                        tape.set(cursor, true);
                        cursor--;
                        state = State.D;
                    } else {
                        tape.set(cursor, false);
                        cursor++;
                        state = State.C;
                    }
                    break;
                case D:
                    if (!tape.get(cursor)) {
                        tape.set(cursor, true);
                        cursor--;
                        state = State.E;
                    } else {
                        tape.set(cursor, true);
                        cursor++;
                        state = State.A;
                    }
                    break;
                case E:
                    if (!tape.get(cursor)) {
                        tape.set(cursor, true);
                        cursor--;
                        state = State.F;
                    } else {
                        tape.set(cursor, false);
                        cursor--;
                        state = State.D;
                    }
                    break;
                case F:
                    if (!tape.get(cursor)) {
                        tape.set(cursor, true);
                        cursor++;
                        state = State.A;
                    } else {
                        tape.set(cursor, false);
                        cursor--;
                        state = State.E;
                    }
                    break;
            }
        }
        System.out.println("Diagnostic checksum = " + tape.getDiagnosticChecksum());
    }
    
    public class EndlessTape {
        final Map<Integer, Boolean> endlessTape = new HashMap<>();
        
        public boolean get(int cursor) {
            if (!endlessTape.containsKey(cursor)) endlessTape.put(cursor, false);
            return endlessTape.get(cursor);
        }
        
        public void set(int cursor, boolean value) {
            endlessTape.put(cursor, value);
        }
        
        public long getDiagnosticChecksum() {
            return this.endlessTape.values().stream().filter(value -> value).count();
        }
    }
    
    public enum State {
        A, B, C, D, E, F
    }

}
