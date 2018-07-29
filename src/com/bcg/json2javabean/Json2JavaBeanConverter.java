package com.bcg.json2javabean;

import java.io.IOException;

/**
 * @author Administrator
 * date 26/07/2018.
 */
public interface Json2JavaBeanConverter {

    /***
     *
     * 使用本地 Json 文件生成 javaBean
     * @param filePath string 本地文件路径
     * @throws IOException IOExceptions 抛出所有IO异常
     *
     */
    void fromFile(String filePath) throws IOException;

    /***
     *
     * 从网络连接直接获得json 生成本地javaBean
     * @param url string  URL 地址
     * @param filename string 从url 获取的json 无法获得生成的类名 需要指定
     * @throws IOException IOExceptions
     *
     */
    void fromUrl(String url, String filename) throws IOException;

}
