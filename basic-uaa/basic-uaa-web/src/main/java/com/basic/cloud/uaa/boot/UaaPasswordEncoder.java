package com.basic.cloud.uaa.boot;

import com.basic.cloud.uaa.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author lanrenspace@163.com
 * @Description: 用户密码加解密bean
 **/
@Component
public class UaaPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        String rasP = "";
        try {
            rasP = MD5Util.getEncryptedPwd(charSequence.toString());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rasP;

    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        boolean flag = true;
        try {
            flag = MD5Util.validPassword(charSequence.toString(), s);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;

    }
}
