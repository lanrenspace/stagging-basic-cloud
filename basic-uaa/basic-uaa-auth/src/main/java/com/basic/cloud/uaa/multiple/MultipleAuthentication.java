package com.basic.cloud.uaa.multiple;

import lombok.Data;

import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class MultipleAuthentication {

    private String authType;
    private String username;
    private Map<String, String[]> authParameters;

    public String getAuthParameter(String parameter) {
        String[] values = this.authParameters.get(parameter);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return null;
    }
}
