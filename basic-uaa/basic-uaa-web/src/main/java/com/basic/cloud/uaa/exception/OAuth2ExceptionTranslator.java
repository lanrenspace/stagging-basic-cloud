package com.basic.cloud.uaa.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;

import static com.basic.cloud.common.contstant.ExceptionEnum.SERVICE_ERROR;

/**
 * @Author lanrenspace@163.com
 * @Description: OAuth2异常处理
 **/
public class OAuth2ExceptionTranslator implements WebResponseExceptionTranslator {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity translate(Exception e) {
        Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);
        Exception ase = (OAuth2Exception) this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            if (ase instanceof InvalidGrantException) {
                InvalidGrantException invalidGrantException = (InvalidGrantException) ase;
                return ResponseEntity.ok(new OAuth2ExceptionHandler(invalidGrantException.getLocalizedMessage(), invalidGrantException.getOAuth2ErrorCode()));
            }
        }
        if (e instanceof AuthenticationException) {
            AuthenticationException authenticationException = (AuthenticationException) e;
            return ResponseEntity.ok(new OAuth2ExceptionHandler(SERVICE_ERROR.getErrorMsg(), authenticationException.getLocalizedMessage()));
        }
        e.printStackTrace();
        return ResponseEntity.ok(new OAuth2ExceptionHandler(SERVICE_ERROR.getErrorMsg(), e.getMessage()));
    }
}
