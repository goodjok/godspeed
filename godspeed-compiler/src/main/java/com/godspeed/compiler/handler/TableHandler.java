package com.godspeed.compiler.handler;

import com.godspeed.annotation.GenerateTableDao;
import com.godspeed.compiler.definition.TableDaoDefinition;
import com.godspeed.compiler.model.ProcessorManager;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class TableHandler extends BaseContainerHandler<GenerateTableDao> {

    public TableHandler(ProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected Class<GenerateTableDao> getAnnotationClass() {
        return GenerateTableDao.class;
    }

    @Override
    protected void onProcessElement(ProcessorManager processorManager, Element element) {
        if (element instanceof TypeElement && element.getAnnotation(getAnnotationClass()) != null) {
            TableDaoDefinition tableDefinition = new TableDaoDefinition(processingEnvironment, (TypeElement) element, processorManager);

            processorManager.addTableDaoDefinition(tableDefinition);

        }
    }
}
