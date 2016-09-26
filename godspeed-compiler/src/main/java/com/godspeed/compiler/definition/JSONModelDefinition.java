package com.godspeed.compiler.definition;

import com.godspeed.annotation.GenerateJSONModelCovert;
import com.godspeed.compiler.ClassNames;
import com.godspeed.compiler.model.ProcessorManager;
import com.godspeed.compiler.utils.StringUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Description: Used in writing ModelAdapters
 */
public class JSONModelDefinition extends BaseDefinition {

    public static final String DBFLOW_TABLE_TAG = "_Covert";

    public int index = 0;

    public JSONModelDefinition(ProcessingEnvironment processingEnvironment, Element typeElement, ProcessorManager processorManager) {
        super(processingEnvironment, typeElement, processorManager);
    }

    public void prepareForWrite() {

        GenerateJSONModelCovert table = element.getAnnotation(GenerateJSONModelCovert.class);
        if (table != null) {
            String outputName = table.name();
            if (StringUtils.isNullOrEmpty(outputName)) {

                String[] outputNames =  buildTypeStatement(elementTypeName);
                outputName =outputNames[0]+outputNames[1];

                setOutputClassNameFull(outputName + DBFLOW_TABLE_TAG);
            } else {
                setOutputClassNameFull(outputName);

            }

        }
    }

    public String[] buildTypeStatement(TypeName typeName) {
        if (typeName instanceof ParameterizedTypeName) {


            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
            ClassName rawType = parameterizedTypeName.rawType;

            List<TypeName> typeArguments = parameterizedTypeName.typeArguments;
            if (typeArguments.size() > 0) {
                String[] outputNames =  buildTypeStatement(typeArguments.get(typeArguments.size() - 1));

              return   new String[]{outputNames[0],rawType.simpleName()+outputNames[1]} ;


            } else {
                return new String[]{rawType.simpleName(),""};

            }


        } else {
            ClassName rawType = (ClassName) typeName;
            return new String[]{rawType.simpleName(),""};

        }
    }

    @Override
    public void onWriteDefinition(TypeSpec.Builder typeBuilder) {
        super.onWriteDefinition(typeBuilder);

        index = 0;

        typeBuilder.superclass(ParameterizedTypeName.get(ClassNames.DBFLOW_TYPE_CONVERTS, TypeName.get(String.class), elementTypeName));

        MethodSpec getDBValue = MethodSpec.methodBuilder("getDBValue")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class).addAnnotation(Override.class)
                .addParameter(elementTypeName, "model")
                .addStatement("return ($T) $T.getStringValue(model)",String.class, ClassNames.JSON_CONVERT_UTILS)
                .build();


        MethodSpec.Builder builder2 = MethodSpec.methodBuilder("getModelValue");
        builder2.addModifiers(Modifier.PUBLIC)
                .returns(elementTypeName).addAnnotation(Override.class)
                .addParameter(String.class, "data");

        String name = buildTypeStatement(builder2, elementTypeName);

        MethodSpec getModelValue = builder2
                .addStatement("return ($T) $T.getModelValue(data, $L)", elementTypeName, ClassNames.JSON_CONVERT_UTILS, name)
                .build();


        typeBuilder.addMethod(getDBValue).addMethod(getModelValue);

    }



    public synchronized String getTypeNameIndex() {
        String returnName = "" + index;
        if(index==0)
            returnName = "";

        index++;
        return returnName;

    }

    public String buildTypeStatement(MethodSpec.Builder builder, TypeName typeName) {
        if (typeName instanceof ParameterizedTypeName) {

            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
            ClassName rawType = parameterizedTypeName.rawType;

            List<TypeName> typeArguments = parameterizedTypeName.typeArguments;
            if (typeArguments.size() > 0) {

                if (typeArguments.size() > 1) {

                    String listName ="javaTypeList" + getTypeNameIndex();

                    builder.addStatement("$T<$T> $L = new $T<$T>()", List.class, ClassNames.JSCKSION_JAVATYPE, listName, ArrayList.class, ClassNames.JSCKSION_JAVATYPE);
                    for (TypeName subTypeName : typeArguments) {
                        String typeValue = buildTypeStatement(builder, subTypeName);
                        builder.addStatement("$L.add($L)", listName, typeValue);
                    }
                    String returnName = "javaType" + getTypeNameIndex();

                    builder.addStatement("$T $L = $T.getSimpleType($T.class,($T[])$L.toArray())", ClassNames.JSCKSION_JAVATYPE, returnName, ClassNames.JSON_CONVERT_UTILS, rawType, ClassNames.JSCKSION_JAVATYPE,listName);


                    return returnName;
                } else {
                    String typeValue = buildTypeStatement(builder, typeArguments.get(0));
                    String returnName = "javaType" +getTypeNameIndex();

                    builder.addStatement("$T $L = $T.getSimpleType($T.class,$L)",ClassNames.JSCKSION_JAVATYPE, returnName, ClassNames.JSON_CONVERT_UTILS, rawType, typeValue);


                    return returnName;


                }

            }
        } else if (typeName.getClass().getSimpleName().equals("ClassName")) {

            ClassName className = (ClassName) typeName;

            String returnName = "javaType" +getTypeNameIndex();

            builder.addStatement("$T $L = $T.getJavaType($T.class)", ClassNames.JSCKSION_JAVATYPE, returnName, ClassNames.JSON_CONVERT_UTILS, className);

            return returnName;
        }
        return "";
    }
}
