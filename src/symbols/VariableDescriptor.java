package symbols;

public class VariableDescriptor extends TypeDescriptor{
    boolean isInitialized;
    String name;

    public VariableDescriptor(Type type) {
        super(type);
    }

    public VariableDescriptor() {
        super(Type.VOID);
    }

    public void setInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }

    public boolean isInitialized(){
        return isInitialized;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    
}