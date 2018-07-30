package com.bcg.json2javabean;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author Administrator
 * date 27/07/2018.
 *
 * JavaBeanConverter 方法实现类
 */
public class JavaBeanConverterImpl implements JavaBeanConverter {

    /***
     * 主功能实现类
     */
    private JsonConverter jsonConverter = new JsonConverter();

    private XmlConverter xmlConverter= new XmlConverter();


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
    public void jsonFromFile(String filePath) throws IOException {
        jsonConverter.convert(filePath);

    }


    /**
     * @param url string  URL 地址
     * @param filename string 从url 获取的json 无法获得生成的类名 需要指定
     * @throws IOException IOExceptions
     */

    @Override
    public void jsonFromUrl(String url, String filename) throws IOException {
        String forObject = restTemplate.getForObject(url, String.class);
        jsonConverter.convert(forObject, filename);

    }

    @Override
    public void xmlFromFile(String s) throws IOException {
        xmlConverter.xmlFromFile(s);
    }

    @Override
    public void xmlFromUrl(String url, String name) throws IOException {

        String forObject = restTemplate.getForObject(url, String.class);
        xmlConverter.xmlFromURL(forObject,name);
    }
}
