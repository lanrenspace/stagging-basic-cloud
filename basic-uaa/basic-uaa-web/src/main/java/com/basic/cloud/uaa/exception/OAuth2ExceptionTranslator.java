package com.basic.cloud.uaa.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;

/**
 * @Author lanrenspace@163.com
 * @Description: OAuth2异常处理
 **/
public class OAuth2ExceptionTranslator implements WebResponseExceptionTranslator<OAuth2ExceptionHandler> {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2ExceptionHandler> translate(Exception e) {
        Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);
        Exception ase = (OAuth2Exception) this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            if (ase instanceof InvalidGrantException) {
                InvalidGrantException invalidGrantException = (InvalidGrantException) ase;
                return ResponseEntity.ok(new OAuth2ExceptionHandler(invalidGrantException.getLocalizedMessage(), invalidGrantException.getOAuth2ErrorCode()));
            }
        }
        return null;
    }
}
