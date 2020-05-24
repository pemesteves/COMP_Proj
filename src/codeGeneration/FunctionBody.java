package codeGeneration;

import java.util.*;

import codeGeneration.CodeWriter.*;
import llir.*;
import symbols.*;


public class FunctionBody {
    private FunctionDescriptor functionDescriptor;
    public static LinkedHashMap<String, Integer> variableToIndex;
    public static LinkedHashMap<String, Integer> fieldsToIndex;
    public static int currentVariableIndex;
    public static int maxStack = 0;
    public static int totalStack = 0;
    private String STACK_LIMIT = "\t.limit stack ";
    private final String LOCALS_LIMIT;
    private static SymbolsTable fieldsTable;
    private static ClassDescriptor classDescriptor;

    public FunctionBody(FunctionDescriptor functionDescriptor, LinkedHashMap<String, Integer> variableToIndex, ClassDescriptor classDescriptor) {
        this.functionDescriptor = functionDescriptor;
        this.variableToIndex = variableToIndex;
        this.currentVariableIndex = variableToIndex.size();

        if(functionDescriptor.getName().equals("main")){
            this.currentVariableIndex--;
        }

        this.LOCALS_LIMIT = "\t.limit locals " + ((functionDescriptor.isStatic()?0:1) + functionDescriptor.getParametersTable().getSize() + functionDescriptor.getBodyTable().getSize());
        totalStack = 0;
        maxStack = 0;
        this.fieldsTable = classDescriptor.getVariablesTable();
        this.classDescriptor = classDescriptor;
    }

    public static void incStack(){
        totalStack++;
        if(totalStack > maxStack){
            maxStack = totalStack;
        }
    }
    
    public static void decStack(int value){
        totalStack -= value;
    }

    private void pushVariables(){

        //Push function parameters
        LinkedHashMap<String, List<Descriptor>> parametersTable = functionDescriptor.getParametersTable().getTable();
        for (Map.Entry<String, List<Descriptor>> entry : parametersTable.entrySet()) {
            String variableName = entry.getKey();
            int variableIndex = getVariableIndex(variableName);
            variableToIndex.put(variableName,variableIndex);
        }

        //Push body variables
        LinkedHashMap<String, List<Descriptor>> bodyTable = functionDescriptor.getBodyTable().getTable();

        for (Map.Entry<String, List<Descriptor>> entry : bodyTable.entrySet()) {
            String variableName = entry.getKey();
            int variableIndex = getVariableIndex(variableName);
            variableToIndex.put(variableName,variableIndex);
        }

    }

    public String generate(){

        boolean foundReturn = false;

        String generatedCode = new String();
        pushVariables();

        for(LLIRNode node : this.functionDescriptor.getFunctionBody()) {
            if (node instanceof LLIRAssignment) {
                AssignmentWriter assignmentWriter = new AssignmentWriter((LLIRAssignment) node);
                generatedCode += assignmentWriter.getCode();
            }
            else if (node instanceof LLIRMethodCall) {
                MethodCallWriter methodCallWriter = new MethodCallWriter((LLIRMethodCall) node);
                generatedCode += methodCallWriter.getCode();
            }else if (node instanceof LLIRImport) {
                ImportWriter importWriter = new ImportWriter((LLIRImport) node);
                generatedCode += importWriter.getCode();
            }
            else if (node instanceof LLIRIfElseBlock){
                IfElseWriter ifElseWriter = new IfElseWriter((LLIRIfElseBlock) node, "");
                generatedCode += ifElseWriter.getCode();
            }
            else if (node instanceof LLIRWhileBlock){
                WhileWriter whileWriter = new WhileWriter((LLIRWhileBlock) node, "");
                generatedCode += whileWriter.getCode();
            }
            else if (node instanceof LLIRReturn) {
                ReturnWriter returnWriter = new ReturnWriter((LLIRReturn) node);
                generatedCode += returnWriter.getCode();
                foundReturn = true;
            }
        }
        if(!foundReturn) generatedCode += "\treturn\n";
        
        return STACK_LIMIT + maxStack + "\n" + LOCALS_LIMIT + "\n" + generatedCode;
    }




    public static int getVariableIndex(String name){
        int variableIndex = variableToIndex.computeIfAbsent(
                name,
                val -> {
                    currentVariableIndex++;
                    return currentVariableIndex;
                }
        );
        return variableIndex;


    }
    public static String getVariableIndexExists(String name){
        Integer variableIndex = variableToIndex.get(name);
        if(variableIndex == null) {
            return "";
        }else if(variableIndex <= 3) return "_" + variableIndex;
        else return "\t" + variableIndex;

    }

    public static String getField(String name,Type type){
        return FunctionBody.classDescriptor.getName() + "/" + name + " " + CGConst.types.get(type) + "\n";
    }

    public static String getVariableIndexString(String name){
        int number = getVariableIndex(name);
        if(number <= 3) return "_" + number;
        else return "\t" + number;
    }

    public LinkedHashMap<String, Integer> getTable() {
        int currentIndex = 1;

        for (HashMap.Entry<String, List<Descriptor>> tableEntry : fieldsTable.getTable().entrySet()) {
            if(tableEntry.getValue().size() > 0) {
                VariableDescriptor field = (VariableDescriptor) tableEntry.getValue().get(0);
                fieldsToIndex.put(field.getName(),currentIndex);
                currentIndex++;
            }
        }


        return fieldsToIndex;
    }

    



}
