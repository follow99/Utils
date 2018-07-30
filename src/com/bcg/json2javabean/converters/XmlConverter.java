package com.bcg.json2javabean.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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

    private JsonConverter jsonConverter = new JsonConverter();


    public void xmlFromFile(String filePath) throws IOException {

        JsonNode jsonNode = xmlMapper.readTree(new File(filePath));
        String fileName = Paths.get(filePath).getFileName().toString();
        String firstFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String s = objectMapper.writeValueAsString(jsonNode);

        jsonConverter.convert(s, firstFileName);

    }


    public void xmlFromURL(String receivedString, String fileName) throws IOException {


        JsonNode jsonNode = xmlMapper.readTree(receivedString);

        String s = objectMapper.writeValueAsString(jsonNode);

        jsonConverter.convert(s, fileName);
    }

}
