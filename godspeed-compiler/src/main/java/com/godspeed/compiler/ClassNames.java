package com.godspeed.compiler;

import com.squareup.javapoet.ClassName;

/**
 * Description: The static FQCN string file to assist in providing class names for imports and more in the Compiler
 */
public class ClassNames {

    public static final ClassName JSON_CONVERT_UTILS =
            ClassName.get("com.godspeed.source.util.io", "JsonConvertUtils");
    public static final ClassName JSCKSION_JAVATYPE =
            ClassName.get("com.fasterxml.jackson.databind", "JavaType");
    public static final ClassName DBFLOW_TYPE_CONVERTS =
            ClassName.get("com.raizlabs.android.dbflow.converter", "TypeConverter");


}
