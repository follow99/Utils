package com.bcg.json2javabean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Administrator
 * date 30/07/2018.
 */
public class XmlConverter {

    private XmlMapper xmlMapper = new XmlMapper();

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonConverter jsonConverter= new JsonConverter();

    private RestTemplate restTemplate= new RestTemplate();


    public void xmlFromFile(String filePath) throws IOException {

        JsonNode jsonNode = xmlMapper.readTree(new File(filePath));
        String fileName= Paths.get(filePath).getFileName().toString();
        String firstFileName= fileName.substring(0,fileName.lastIndexOf("."));
        String s = objectMapper.writeValueAsString(jsonNode);

        jsonConverter.convert(s,firstFileName);

    }


    public void xmlFromURL(String receivedString, String fileName) throws IOException {


        JsonNode jsonNode = xmlMapper.readTree(receivedString);

        String s = objectMapper.writeValueAsString(jsonNode);

        jsonConverter.convert(s,fileName);
    }


    public static void main(String[] args) throws IOException {
        XmlConverter xmlConverter= new XmlConverter();

        String path="D:\\Users\\Administrator\\raesite\\src\\test\\testXml\\test.xml";
        xmlConverter.xmlFromFile(path);
        String url="";
        xmlConverter.xmlFromURL("http://api.geoiplookup.net/?query=46.7.13.129","ipets");
    }
}
