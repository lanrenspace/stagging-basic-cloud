package com.basic.cloud.common.bcrypt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class BCryptPassword {

    private final Pattern BCRYPT_PATTERN;

    /**
     * logger
     */
    private final Log logger = LogFactory.getLog(this.getClass());
    private final int strength;
    private final SecureRandom random;

    public BCryptPassword(int strength, SecureRandom random) {
        this.BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        if (strength == -1 || strength >= 4 && strength <= 31) {
            this.strength = strength;
            this.random = random;
        } else {
            throw new IllegalArgumentException("Bad strength");
        }
    }

    /**
     * 加密
     *
     * @param rawPassword
     * @return
     */
    public String encode(CharSequence rawPassword) {
        String salt;
        if (this.strength > 0) {
            if (this.random != null) {
                salt = BCrypt.genSalt(this.strength, this.random);
            } else {
                salt = BCrypt.genSalt(this.strength);
            }
        } else {
            salt = BCrypt.genSalt();
        }

        return BCrypt.hashPw(rawPassword.toString(), salt);
    }


    /**
     * 密码匹配
     *
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
                this.logger.warn("Encoded password does not look like BCrypt");
                return false;
            } else {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }
    }
}
