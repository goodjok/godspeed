package com.godspeed.service.dbflow.serialize;

import com.fasterxml.jackson.databind.JavaType;
import com.godspeed.source.util.io.JsonConvertUtils;
import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 用于JSON 数据转换，暂时只支持<String,Class> 其他类型数据不要来捣乱
 * 使用者必须继承这个类  mmmmmmmmm extends ModelSerializableConverter<String,tttttt>
 * 才能使用 @Column(typeConverter = xxx.class) 标注方式
 *
 * @param <DataClass>
 * @param <ModelClass>
 */
public abstract class ModelSerializableConverter<DataClass, ModelClass> extends TypeConverter<DataClass, ModelClass> {

    /**
     * 获取泛型参数，可能得调整
     *
     * @param entity
     * @return
     */
    private JavaType getActualTypeClass(Class entity) {

        ParameterizedType type = (ParameterizedType) entity.getGenericSuperclass();

        Type subType = type.getActualTypeArguments()[1];


        JavaType javaType = null;
        if (subType instanceof ParameterizedType) {
            ParameterizedType trueType = (ParameterizedType) subType;

            Class rawTypeClass = (Class) (trueType.getActualTypeArguments()[0]);

            Type rawType = trueType.getRawType();

            javaType = JsonConvertUtils.getJavaType((Class) rawType, rawTypeClass);

        } else {
            Class rawTypeClass = (Class) subType;
            javaType = JsonConvertUtils.getJavaType(rawTypeClass);

        }

        return javaType;
    }


    @Override
    public DataClass getDBValue(ModelClass model) {
        if (model == null)
            return null;

        try {
            return (DataClass) JsonConvertUtils.getStringValue(model);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public ModelClass getModelValue(DataClass data) {
        if (data == null)
            return null;

        try {
            JavaType clazzType = getActualTypeClass(this.getClass());
            return (ModelClass) JsonConvertUtils.getModelValue((String) data, clazzType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
