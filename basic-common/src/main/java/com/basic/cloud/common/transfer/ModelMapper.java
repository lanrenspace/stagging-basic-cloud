package com.basic.cloud.common.transfer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author lanrenspace@163.com
 * @Description: model转换工具
 **/
public class ModelMapper {

    /**
     * list转换
     *
     * @param targetType  目标类型
     * @param sourcesList 数据源
     * @param <T>
     * @param <ST>
     * @return
     */
    public static <T, ST> List<T> mapFromCollection(Class<T> targetType, Collection<ST> sourcesList) {
        return mapFromCollection(targetType, sourcesList, null);
    }

    /**
     * list转换
     *
     * @param targetType  目标类型
     * @param sourcesList 数据源
     * @param mapping     自定义映射
     * @param <T>
     * @param <ST>
     * @return
     */
    public static <T, ST> List<T> mapFromCollection(Class<T> targetType, Collection<ST> sourcesList, Mapping<ST, T> mapping) {
        return sourcesList.stream().map((s) -> mapFrom(targetType, s, mapping)).collect(Collectors.toList());
    }


    /**
     * list转换
     *
     * @param targetType  目标类型
     * @param sourcesList 数据源
     * @param mapping     自定义映射
     * @param <T>
     * @param <ST>
     * @return
     */
    public static <T, ST> List<T> mapFromCollection(Class<T> targetType, Stream<ST> sourcesList, Mapping<ST, T> mapping) {
        return sourcesList.map((s) -> mapFrom(targetType, s, mapping)).collect(Collectors.toList());
    }

    /**
     * 对象转换
     *
     * @param targetType 目标类型
     * @param sources    数据源
     * @param <T>
     * @param <ST>
     * @return
     */
    public static <T, ST> T mapFrom(Class<T> targetType, ST sources) {
        return mapFrom(targetType, sources, null);

    }

    public static <T, ST> T mapFrom(Class<T> targetType, ST sources, Mapping<ST, T> mapping) {
        T target;
        try {
            target = targetType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MapperException(e);
        }
        map(target, sources, mapping);
        return target;
    }

    public static <T, ST> void map(T target, ST sources) {
        map(target, sources, null);
    }


    private static void applyValue(Object target, Object source, String targetName, String sourceName) {
        MapperUtil.setFieldValue(target, targetName, MapperUtil.getFieldValue(source, sourceName));
    }

    @SuppressWarnings("unchecked")
    public static <T, ST> void map(T target, ST source, Mapping<ST, T> mapping) {
        Objects.requireNonNull(target, "Target object could not be null.");
        Objects.requireNonNull(source, "Source object could not be null.");


        List<FieldDescription> sourceFields = MapperUtil.getMappedFields(source.getClass());
        List<FieldDescription> targetFields = MapperUtil.getMappedFields(target.getClass());

        // eg. dto to entity
        if (targetFields.size() == 0 && sourceFields.size() > 0) {
            for (FieldDescription field : sourceFields) {
                if (MapperUtil.checkSourceType(field, source.getClass())) {
                    applyValue(target, source, field.mappingName, field.realName);
                }

            }
        }
        // eg. entity to dto
        else if (targetFields.size() > 0 && sourceFields.size() == 0) {
            for (FieldDescription field : targetFields) {
                if (MapperUtil.checkSourceType(field, source.getClass())) {
                    applyValue(target, source, field.realName, field.mappingName);
                }
            }
        }
        if (mapping != null) {
            mapping.mapTo(source, target);
        }
    }
}
