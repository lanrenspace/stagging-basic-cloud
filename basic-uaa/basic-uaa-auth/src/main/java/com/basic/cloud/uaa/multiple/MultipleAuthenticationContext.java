package com.basic.cloud.uaa.multiple;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class MultipleAuthenticationContext {

    private static ThreadLocal<MultipleAuthentication> holder = new ThreadLocal<>();

    public static void set(MultipleAuthentication multipleAuthentication) {
        holder.set(multipleAuthentication);
    }

    public static MultipleAuthentication get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
