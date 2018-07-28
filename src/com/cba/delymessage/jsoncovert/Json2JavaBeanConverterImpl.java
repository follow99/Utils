package com.bcg.raesite.jsoncovert;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author Administrator
 * date 27/07/2018.
 *
 * Json2JavaBeanConverter 方法实现类
 */
public class Json2JavaBeanConverterImpl implements Json2JavaBeanConverter {

    /***
     * 主功能实现类
     */
    private Converter converter = new Converter();


    /**
     * 使用spring 的 RestTemplate 进行网络连接
     * 需要spring boot 的运行环境
     * 因为懒得写了
     */
    private RestTemplate restTemplate = new RestTemplate();


    /**
     * @param filePath string 本地文件路径
     * @throws IOException IOExceptions
     */
    @Override
    public void fromFile(String filePath) throws IOException {
        converter.convert(filePath, InputTypes.ISFILE);

    }


    /**
     * @param url string  URL 地址
     * @param filename string 从url 获取的json 无法获得生成的类名 需要指定
     * @throws IOException IOExceptions
     */

    @Override
    public void fromUrl(String url, String filename) throws IOException {
        String forObject = restTemplate.getForObject(url, String.class);
        converter.convert(forObject, filename);

    }
}
