package com.godspeed.compiler.definition;


import com.godspeed.annotation.GenerateTableDao;
import com.godspeed.compiler.model.ProcessorManager;
import com.godspeed.compiler.utils.ElementUtility;
import com.godspeed.compiler.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class TableDaoDefinition extends BaseDefinition {
    public static final String DBFLOW_TABLE_TAG = "Dao";

    protected Map<String,Element> classElementLookUpMap = new HashMap<>();

    public TableDaoDefinition(ProcessingEnvironment processingEnvironment, TypeElement element, ProcessorManager processorManager) {
        super(processingEnvironment, element, processorManager);
    }


    protected void createColumnDefinitions(TypeElement typeElement) {
        List<? extends Element> elements = ElementUtility.getAllElements(typeElement, manager);

        for (Element element : elements) {
            classElementLookUpMap.put(element.getSimpleName().toString(), element);
        }

        AtomicInteger integer = new AtomicInteger(0);
        for (Element element : elements) {
            //TODO 子节点处理

        }
    }


    public void prepareForWrite() {

        GenerateTableDao tableDao = typeElement.getAnnotation(GenerateTableDao.class);
        if (tableDao != null) {
            String outputName = tableDao.name();
            if (StringUtils.isNullOrEmpty(outputName)) {

                setOutputClassName(DBFLOW_TABLE_TAG);
            } else {
                setOutputClassNameFull(outputName);

            }

        }
    }
}
