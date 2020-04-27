package symbols;

public class VariableDescriptor extends TypeDescriptor{
    boolean isInitialized;
    String name;

    public VariableDescriptor(Type type) {
        super(type);
        
        this.isInitialized = false;
    }

    public VariableDescriptor() {
        super(Type.VOID);
    }

    public void setInitialized() {
        this.isInitialized = true;
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

    public void print(String prefix) {
        String initializedString;
        if (this.isInitialized) initializedString = "init";
        else initializedString = "non-init";

        System.out.println(prefix + name + " --> " + this.type + " (" + initializedString + ")");
    }
    
}