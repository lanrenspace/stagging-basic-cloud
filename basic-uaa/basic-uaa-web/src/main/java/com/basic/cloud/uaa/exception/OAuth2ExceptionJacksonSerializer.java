package com.basic.cloud.uaa.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class OAuth2ExceptionJacksonSerializer extends StdSerializer<OAuth2ExceptionHandler> {

    protected OAuth2ExceptionJacksonSerializer() {
        super(OAuth2ExceptionHandler.class);
    }

    @Override
    public void serialize(OAuth2ExceptionHandler oAuth2ExceptionHandler, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        jsonGenerator.writeObjectField("data", oAuth2ExceptionHandler.getData());
        jsonGenerator.writeObjectField("msg", oAuth2ExceptionHandler.getMsg());
        if (oAuth2ExceptionHandler.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : oAuth2ExceptionHandler.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jsonGenerator.writeStringField(key, add);
            }
        }
        jsonGenerator.writeEndObject();

    }
}
