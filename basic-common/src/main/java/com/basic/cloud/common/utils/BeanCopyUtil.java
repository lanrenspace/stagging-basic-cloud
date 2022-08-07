package com.basic.cloud.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author huxuning
 */
public class BeanCopyUtil extends BeanUtils {

    /**
     * 对象集合类型转化，字段和类型相同自动赋值
     * @param sources 源数据
     * @param target 目标对象的构建方法 Supplier
     * @param <S> 源数据类型
     * @param <T> 目标对象类型
     * @return 目标对象数组
     */
    public static<S,T> List<T> copyListProperties(Collection<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }

    /**
     * 对象集合类型转化，字段和类型相同自动赋值，对于字段名或类型不同的字段可通过回调方法转化
     * @param sources 源数据
     * @param target 目标对象的构建方法 Supplier
     * @param callBack 回调方法
     * @param <S> 源数据类型
     * @param <T> 目标对象类型
     * @return 目标对象数组
     */
    public static<S,T> List<T> copyListProperties(Collection<S> sources, Supplier<T> target, BeanCopyCallBack<S,T> callBack) {
        if(sources == null || sources.isEmpty()){
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
            // 回调
            if (callBack != null) {
                callBack.callBack(source, t);
            }
        }
        return list;
    }

    @FunctionalInterface
    public interface BeanCopyCallBack<S, T> {
        /**
         * 回调
         * @param s 源数据对象
         * @param t 目标对象
         */
        void callBack(S s, T t);
    }
}
