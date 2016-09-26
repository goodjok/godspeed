package com.godspeed.compiler.model;

import com.godspeed.compiler.definition.JSONModelDefinition;
import com.godspeed.compiler.definition.TableDaoDefinition;
import com.godspeed.compiler.handler.BaseContainerHandler;
import com.godspeed.compiler.handler.Handler;
import com.godspeed.compiler.utils.WriterUtils;
import com.godspeed.compiler.validator.JSONModelValidator;
import com.google.common.collect.Maps;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class ProcessorManager implements Handler {

    private static ProcessorManager manager;

    public static ProcessorManager getManager() {
        return manager;
    }

    public static void setManager(ProcessorManager manager) {
        ProcessorManager.manager = manager;
    }

    private ProcessingEnvironment processingEnvironment;
    private List<BaseContainerHandler> handlers = new ArrayList<>();

    private Map<TypeName, JSONModelDefinition> jsonModelDefinitionHashMap = Maps.newHashMap();
    private Map<TypeName, TableDaoDefinition> tableDaoDefinitionHashMap = Maps.newHashMap();

    public ProcessorManager(ProcessingEnvironment processingEnv) {
        processingEnvironment = processingEnv;
        setManager(this);
    }

    public void addHandlers(BaseContainerHandler... containerHandlers) {
        for (BaseContainerHandler containerHandler : containerHandlers) {
            if (!handlers.contains(containerHandler)) {
                handlers.add(containerHandler);
            }
        }
    }

    public Messager getMessager() {
        return processingEnvironment.getMessager();
    }

    public Types getTypeUtils() {
        return processingEnvironment.getTypeUtils();
    }

    public Elements getElements() {
        return processingEnvironment.getElementUtils();
    }

    public ProcessingEnvironment getProcessingEnvironment() {
        return processingEnvironment;
    }


    public void addJsonModelDefinition(JSONModelDefinition jsonModelDefinition) {
        jsonModelDefinitionHashMap.put(jsonModelDefinition.elementTypeName, jsonModelDefinition);
    }
    public void addTableDaoDefinition(TableDaoDefinition tableDaoDefinition) {
        tableDaoDefinitionHashMap.put(tableDaoDefinition.elementTypeName, tableDaoDefinition);
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


    @Override
    public void handle(ProcessorManager processorManager, RoundEnvironment roundEnvironment) {
        for (BaseContainerHandler containerHandler : handlers) {
            containerHandler.handle(processorManager, roundEnvironment);
        }

        {

            Collection<JSONModelDefinition> tableDefinitions = jsonModelDefinitionHashMap.values();

            JSONModelValidator validator = new JSONModelValidator();
            for (JSONModelDefinition jsonModelDefinition : tableDefinitions) {
                if (validator.validate(processorManager, jsonModelDefinition)) {

                    jsonModelDefinition.prepareForWrite();

                    WriterUtils.writeBaseDefinition(jsonModelDefinition, processorManager);
                }
            }
        }
        {
            Collection<TableDaoDefinition> tableDefinitions = tableDaoDefinitionHashMap.values();

            for (TableDaoDefinition tableDaoDefinition : tableDefinitions) {
                    tableDaoDefinition.prepareForWrite();

                    WriterUtils.writeBaseDefinition(tableDaoDefinition, processorManager);
            }
        }


    }
}
