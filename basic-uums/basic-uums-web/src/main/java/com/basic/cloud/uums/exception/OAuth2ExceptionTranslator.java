package com.basic.cloud.uums.exception;

import com.basic.cloud.common.enums.SysErrorTypeEnum;
import com.basic.cloud.common.exceptions.ServiceException;
import com.basic.cloud.uums.contstant.enums.UumErrorTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class OAuth2ExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.OK);
        if (e instanceof InvalidGrantException) {
            InvalidGrantException grantException = (InvalidGrantException) e;
            return builder.body(new OAuth2ExceptionHandler(UumErrorTypeEnum.ACCOUNT_LOCKED.getMsg(), grantException.getOAuth2ErrorCode()));
        }
        if (e instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            return ResponseEntity.status(HttpStatus.OK).body(new OAuth2ExceptionHandler(oAuth2Exception.getMessage(), oAuth2Exception.getOAuth2ErrorCode()));
        }
        if (e instanceof InternalAuthenticationServiceException) {
            InternalAuthenticationServiceException serviceException = (InternalAuthenticationServiceException) e;
            return ResponseEntity.status(HttpStatus.OK).body(new OAuth2ExceptionHandler(SysErrorTypeEnum.SYSTEM_ERROR.getMsg(), serviceException.getMessage()));
        }
        e.printStackTrace();
        if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            return builder.body(new OAuth2ExceptionHandler(serviceException.getMsg(), serviceException.getCode().toString()));
        }
        return null;
    }
}
