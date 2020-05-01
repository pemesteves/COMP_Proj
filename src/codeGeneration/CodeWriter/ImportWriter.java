package codeGeneration.CodeWriter;

import codeGeneration.CGConst;
import codeGeneration.FunctionParameters;
import llir.LLIRExpression;
import llir.LLIRImport;
import llir.LLIRMethodCall;

import java.util.List;

public class ImportWriter {
    private static String INSTRUCTION = "\tinvokestatic ";
    private String code;

    public ImportWriter(LLIRImport importLLIR){
        this.code ="";
        this.code += getParameters(importLLIR);
        this.code += INSTRUCTION;

        String arguments = FunctionParameters.getParametersTypes(importLLIR.getImportDescriptor().getParameters());

        this.code += getIdentifiers(importLLIR) + "(" + arguments + ")"+ CGConst.types.get(importLLIR.getImportDescriptor().getReturn()) + "\n";

    }

    private String getIdentifiers(LLIRImport importLLIR){
        String genarated ="";
        List<String> identifiers =  importLLIR.getImportDescriptor().getIdentifiers();
        for (String identifier : identifiers){
            genarated += identifier + "/";
        }
        genarated = genarated.substring(0, genarated.length() - 1);
        return genarated;

    }

    public String getCode(){
        return this.code;
    }

    private String getParameters(LLIRImport importLLIR){
        String result = "";

        List<LLIRExpression> parameters = importLLIR.getParametersExpressions();
        for (LLIRExpression expression : parameters){
            ExpressionWriter expressionWriter = new ExpressionWriter(expression,"NAME");
            result += expressionWriter.getCode();

        }

        return result;
    }
}