package com.bcg.json2javabean;

import java.io.IOException;

/**
 * @author Administrator
 * date 26/07/2018.
 */
public interface JavaBeanConverter {

    /***
     *
     * 使用本地 Json 文件生成 javaBean
     * @param filePath string 本地文件路径
     * @throws IOException IOExceptions 抛出所有IO异常
     *
     */
    void jsonFromFile(String filePath) throws IOException;

    /***
     *
     * 从网络连接直接获得json 生成本地javaBean
     * @param url string  URL 地址
     * @param filename string 从url 获取的json 无法获得生成的类名 需要指定
     * @throws IOException IOExceptions
     *
     */
    void jsonFromUrl(String url, String filename) throws IOException;

    /***
     * xml from local
     * @param filePath local xml path
     * @throws IOException IO异常
     */
    void xmlFromFile(String filePath) throws IOException;

    /***
     * xml from url
     * @param url  url
     * @param name Main java bean name
     * @throws IOException
     */
    void xmlFromUrl(String url, String name) throws IOException;
}


