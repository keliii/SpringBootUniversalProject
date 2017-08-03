package com.keliii.user.shiro;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by keliii on 2017/6/20.
 */
public class PasswordUtil {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "MD5";
    /**
     * 散列次数
     */
    private final int hashIterations = 2;

    public String getSalt() {
        return randomNumberGenerator.nextBytes().toHex();
    }

    public String encryptPassword(String password, String salt) {
        return new SimpleHash(algorithmName, password,
                ByteSource.Util.bytes(salt), hashIterations).toHex();
    }


}
