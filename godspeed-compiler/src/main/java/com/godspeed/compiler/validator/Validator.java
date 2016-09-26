package com.godspeed.compiler.validator;


import com.godspeed.compiler.model.ProcessorManager;

/**
 * Description: the base interface for validating annotations.
 */
public interface Validator<ValidatorDefinition> {

    /**
     * @param processorManager    The manager
     * @param validatorDefinition The validator to use
     * @return true if validation passed, false if there was an error.
     */
    public boolean validate(ProcessorManager processorManager, ValidatorDefinition validatorDefinition);
}
