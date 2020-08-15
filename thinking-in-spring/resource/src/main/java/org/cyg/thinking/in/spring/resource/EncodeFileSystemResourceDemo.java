package org.cyg.thinking.in.spring.resource;

import org.cyg.thinking.in.spring.resource.util.ResourceUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.File;
import java.io.IOException;

/**
 * 带有字符编码的 {@link FileSystemResource} 示例
 *
 * @see FileSystemResource
 * @see EncodedResource
 */
public class EncodeFileSystemResourceDemo {

    public static void main(String[] args) throws IOException {
        String currentJavaFilePath = System.getProperty("user.dir") + "\\thinking-in-spring\\resource\\" + "src/main/java/org/cyg/thinking/in/spring/resource/EncodeFileSystemResourceDemo.java";
        File currentJavaFile = new File(currentJavaFilePath);
        // FileSystemResource => WritableResource => Resource
        FileSystemResource fileSystemResource = new FileSystemResource(currentJavaFile);

        System.out.println(ResourceUtils.getContent(fileSystemResource));
    }
}
