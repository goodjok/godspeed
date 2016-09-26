package com.godspeed.compiler.definition;

import com.godspeed.compiler.BaseCompiler;
import com.godspeed.compiler.model.ProcessorManager;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.Arrays;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Description: Holds onto a common-set of fields and provides a common-set of methods to output class files.
 */
public abstract class BaseDefinition extends BaseCompiler implements TypeDefinition {

    public final ProcessorManager manager;

    public ClassName elementClassName;
    public TypeName elementTypeName;
    public ClassName outputClassName;
    public TypeName erasedTypeName;

    public Element element;
    public TypeElement typeElement;
    public String elementName;

    public String packageName;

    public BaseDefinition(ProcessingEnvironment processingEnvironment, ExecutableElement element, ProcessorManager processorManager) {
        super(processingEnvironment);
        this.manager = processorManager;
        this.element = element;
        packageName = manager.getElements().getPackageOf(element).toString();
        elementName = element.getSimpleName().toString();

        try {
            TypeMirror typeMirror = element.asType();
            elementTypeName = TypeName.get(typeMirror);
            if (!elementTypeName.isPrimitive()) {
                elementClassName = getElementClassName(element);
            }
            TypeMirror erasedType = processorManager.getTypeUtils().erasure(typeMirror);
            erasedTypeName = TypeName.get(erasedType);
        } catch (Exception e) {

        }
    }

    public BaseDefinition(ProcessingEnvironment processingEnvironment, Element element, ProcessorManager processorManager) {
        super(processingEnvironment);
        this.manager = processorManager;
        this.element = element;
        packageName = manager.getElements().getPackageOf(element).toString();
        try {
            TypeMirror typeMirror;
            if (element instanceof ExecutableElement) {
                typeMirror = ((ExecutableElement) element).getReturnType();
                elementTypeName = TypeName.get(typeMirror);
            } else {
                typeMirror = element.asType();
                elementTypeName = TypeName.get(typeMirror);
            }
            TypeMirror erasedType = processorManager.getTypeUtils().erasure(typeMirror);
            erasedTypeName = TypeName.get(erasedType);
        } catch (IllegalArgumentException i) {
            manager.logError("Found illegal type:" + element.asType() + " for " + element.getSimpleName().toString());
            manager.logError("Exception here:" + i.toString());
        }
        elementName = element.getSimpleName().toString();
        if (!elementTypeName.isPrimitive()) {
            elementClassName = getElementClassName(element);
        }

        if (element instanceof TypeElement) {
            typeElement = ((TypeElement) element);
        }
    }

    public BaseDefinition(ProcessingEnvironment processingEnvironment, TypeElement element, ProcessorManager processorManager) {
        super(processingEnvironment);
        this.manager = processorManager;
        this.typeElement = element;
        elementClassName = ClassName.get(typeElement);
        elementTypeName = TypeName.get(element.asType());
        elementName = element.getSimpleName().toString();
        packageName = manager.getElements().getPackageOf(element).toString();
    }

    protected ClassName getElementClassName(Element element) {
        try {
            return ClassName.bestGuess(element.asType().toString());
        } catch (Exception e) {
            return null;
        }
    }

    protected void setOutputClassName(String postfix) {
        String outputName;
        if (elementClassName == null) {
            if (elementTypeName instanceof ClassName) {
                outputName = ((ClassName) elementTypeName).simpleName();
            } else if (elementTypeName instanceof ParameterizedTypeName) {
                outputName = ((ParameterizedTypeName) elementTypeName).rawType.simpleName();
                elementClassName = ((ParameterizedTypeName) elementTypeName).rawType;
            } else {
                outputName = elementTypeName.toString();
            }
        } else {
            outputName = elementClassName.simpleName();
        }
        outputClassName = ClassName.get(packageName, outputName + postfix);
    }

    protected void setOutputClassNameFull(String fullName) {
        outputClassName = ClassName.get(packageName, fullName);
    }

    @Override
    public TypeSpec getTypeSpec() {


        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(outputClassName.simpleName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterfaces(Arrays.asList(getImplementsClasses()));
        TypeName extendsClass = getExtendsClass();
        if (extendsClass != null) {
            typeBuilder.superclass(extendsClass);
        }
        typeBuilder.addJavadoc("This is generated code. Please do not modify");
        onWriteDefinition(typeBuilder);
        return typeBuilder.build();
    }

    public ProcessorManager getManager() {
        return manager;
    }

    protected TypeName getExtendsClass() {
        return null;
    }

    protected TypeName[] getImplementsClasses() {
        return new TypeName[0];
    }

    public void onWriteDefinition(TypeSpec.Builder typeBuilder) {

    }
}