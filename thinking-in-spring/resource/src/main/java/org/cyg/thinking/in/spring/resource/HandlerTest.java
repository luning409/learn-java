package org.cyg.thinking.in.spring.resource;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * X Handler 测试示例
 */
public class HandlerTest {

    public static void main(String[] args) throws IOException {
        URL url = new URL("x:///META-INF/default.properties"); // 类型于 classpath://META-INF

        InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));
    }
}
