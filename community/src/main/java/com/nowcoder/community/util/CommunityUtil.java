package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {
    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密
    // hello -> abc123def456
    // hello + 3e4a8 -> abc123def456abc
    public static String md5(String key) {
        //先对参数为空判断
        if (StringUtils.isBlank(key)) {
            return null;
        }
        //调用spring自带的工具，加密成16进制返回，然后将string转换为Bytes
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
