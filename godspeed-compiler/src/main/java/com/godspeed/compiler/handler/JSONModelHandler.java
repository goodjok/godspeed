package com.godspeed.compiler.handler;


import com.godspeed.annotation.GenerateJSONModelCovert;
import com.godspeed.compiler.definition.JSONModelDefinition;
import com.godspeed.compiler.model.ProcessorManager;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

public class JSONModelHandler extends BaseContainerHandler<GenerateJSONModelCovert> {


    public JSONModelHandler(ProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    protected Class<GenerateJSONModelCovert> getAnnotationClass() {
        return GenerateJSONModelCovert.class;
    }

    @Override
    protected void onProcessElement(ProcessorManager manager, Element element) {


        if (element.getAnnotation(getAnnotationClass()) != null) {

            JSONModelDefinition tableDefinition = new JSONModelDefinition(processingEnvironment, element, manager);
            manager.addJsonModelDefinition(tableDefinition);

        }
    }
}
