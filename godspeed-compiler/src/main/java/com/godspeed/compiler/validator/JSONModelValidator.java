package com.godspeed.compiler.validator;


import com.godspeed.compiler.definition.JSONModelDefinition;
import com.godspeed.compiler.model.ProcessorManager;

public class JSONModelValidator implements Validator<JSONModelDefinition> {

    @Override
    public boolean validate(ProcessorManager processorManager, JSONModelDefinition tableDefinition) {
        boolean success = true;


//        if (!ProcessorUtils.implementsClass(processorManager.getProcessingEnvironment(), ClassNames.MODEL.toString(),  tableDefinition.element)) {
////            processorManager.logError(TableValidator.class, "The @Table annotation can only apply to a class that implements Model. Found: " + tableDefinition.element);
////            success = false;
//        }

        return success;
    }
}
