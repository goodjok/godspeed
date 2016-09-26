package com.godspeed.compiler;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * Created by junhai on 16/9/24.
 */
public class BaseCompiler {

    protected ProcessingEnvironment processingEnvironment;

    public BaseCompiler(ProcessingEnvironment processingEnvironment){
        this.processingEnvironment = processingEnvironment;
    }


    public Messager getMessager() {
        return processingEnvironment.getMessager();
    }

    public void logError(String error, Object... args) {
        getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("*==========*\n" + error + "\n*==========*", args));
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTrace : stackTraceElements) {
            getMessager().printMessage(Diagnostic.Kind.ERROR, stackTrace + "");
        }
    }

    public void logError(Class callingClass, String error, Object... args) {
        logError(callingClass + ": " + error, args);
    }

    public void logWarning(String error, Object... args) {
        getMessager().printMessage(Diagnostic.Kind.WARNING, String.format("*==========*\n" + error + "\n*==========*", args));
    }

    public void logWarning(Class callingClass, String error, Object... args) {
        logWarning(callingClass + ":" + error, args);
    }

}
