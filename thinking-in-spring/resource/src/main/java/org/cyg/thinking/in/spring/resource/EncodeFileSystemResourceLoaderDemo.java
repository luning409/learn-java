package org.cyg.thinking.in.spring.resource;

import org.cyg.thinking.in.spring.resource.util.ResourceUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

/**
 * 带有字符编码的 {@link FileSystemResourceLoader} 示例
 *
 * @see FileSystemResourceLoader
 * @see FileSystemResource
 * @see EncodedResource
 */
public class EncodeFileSystemResourceLoaderDemo {

    public static void main(String[] args) throws IOException {
        String currentJavaFilePath = System.getProperty("user.dir") + "\\thinking-in-spring\\resource\\" + "src/main/java/org/cyg/thinking/in/spring/resource/EncodeFileSystemResourceLoaderDemo.java";
        // 新建一个 FileSystemResourceLoader 对象
        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        // FileSystemResource => WritableResource => Resource
        Resource resource = resourceLoader.getResource(currentJavaFilePath);

        System.out.println(ResourceUtils.getContent(resource));
    }
}
