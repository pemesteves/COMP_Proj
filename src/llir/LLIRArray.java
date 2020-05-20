package llir;

import symbols.NamedTypeDescriptor;

public class LLIRArray extends LLIRExpression {
    public NamedTypeDescriptor array;

    public LLIRArray(NamedTypeDescriptor array) {
        this.array = array;
    }

    /**
     * @param array the array to set
     */
    public void setArray(NamedTypeDescriptor array) {
        this.array = array;
    }

    /**
     * @return the array
     */
    public NamedTypeDescriptor getArray() {
        return array;
    }

}