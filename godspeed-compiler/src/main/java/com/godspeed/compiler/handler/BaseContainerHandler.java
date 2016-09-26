package com.godspeed.compiler.handler;

import com.godspeed.compiler.BaseCompiler;
import com.godspeed.compiler.model.ProcessorManager;
import com.google.common.collect.Sets;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Description: The base handler than provides common callbacks into processing annotated top-level elements
 */
public abstract class BaseContainerHandler<AnnotationClass extends Annotation> extends BaseCompiler implements Handler {

    public BaseContainerHandler(ProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    public void handle(ProcessorManager processorManager, RoundEnvironment roundEnvironment) {
        final Set<Element> annotatedElements = Sets.newHashSet(roundEnvironment.getElementsAnnotatedWith(getAnnotationClass()));
        processElements(processorManager, annotatedElements);
        if (annotatedElements.size() > 0) {
            for (Element element : annotatedElements) {
                onProcessElement(processorManager, element);
            }
        }
    }

    protected abstract Class<AnnotationClass> getAnnotationClass();

    public void processElements(ProcessorManager processorManager, Set<Element> annotatedElements) {

    }

    protected abstract void onProcessElement(ProcessorManager processorManager, Element element);
}
