package semantic;

import java.util.HashMap;
import java.util.Map;

public class SemanticEnvironment {

    private final SemanticEnvironment parent;
    private final Map<String, Boolean> variables;

    public SemanticEnvironment() {
        this(null);
    }

    public SemanticEnvironment(SemanticEnvironment parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    public boolean defineVariable(String name) {
        if (variables.containsKey(name)) {
            return false;
        }

        variables.put(name, true);
        return true;
    }

    public boolean isVariableDefined(String name) {
        if (variables.containsKey(name)) {
            return true;
        }

        if (parent != null) {
            return parent.isVariableDefined(name);
        }

        return false;
    }
}
