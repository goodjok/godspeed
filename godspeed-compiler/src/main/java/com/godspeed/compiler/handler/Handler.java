package com.godspeed.compiler.handler;


import com.godspeed.compiler.model.ProcessorManager;

import javax.annotation.processing.RoundEnvironment;

/**
 * Description: The main base-level handler for performing some action when the
 * {@link javax.annotation.processing.Processor#process(java.util.Set, RoundEnvironment)} is called.
 */
public interface Handler {

    /**
     * Called when the process of the {@link javax.annotation.processing.Processor} is called
     *
     * @param processorManager The manager that holds processing information
     * @param roundEnvironment The round environment
     */
    public void handle(ProcessorManager processorManager, RoundEnvironment roundEnvironment);
}
