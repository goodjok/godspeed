package com.godspeed.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface GenerateJSONModelCovert {

    /**
     * @return The name of the column. The default is the field name.
     */
    String name() default "";
}
