package com.bcg.raesite.jsoncovert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 * date 27/07/2018.
 */
public class Converter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private JavaBeanStringTemplate javaBeanStringTemplate;

    /***
     * 无参构造 使用Lombok heading 模板 ->模板接口中的默认方法
     */
    public Converter() {
        this.javaBeanStringTemplate = new LombokHeadingTemplate();
    }

    /***
     * 有参构造 用于注入自定义文件头
     * @param javaBeanStringTemplate 头文件模板
     */
    public Converter(JavaBeanStringTemplate javaBeanStringTemplate) {
        this.javaBeanStringTemplate = javaBeanStringTemplate;
    }


    /***
     * 用于 循环内读取到的Object 或者 数组 或者 网络文件读取 需要指定 生成类名 在下边文件中会注释出来
     * @param filePath 文件路径
     * @param name  文件名字
     * @throws IOException
     */
    public void convert(String filePath, String name) throws IOException {
        convert(filePath, InputTypes.ISTEXT, name);
    }

    /***
     * 用于读取本地json文件 本地文件使用 文件名作为初始类名 所以无需指定
     * @param filePath
     * @throws IOException
     */
    public void convert(String filePath) throws IOException {
        convert(filePath, InputTypes.ISFILE, "");
    }

    /***
     *全参主方法
     * @param filePath
     * @param inputTypes
     * @param name
     * @throws IOException
     */
    private void convert(String filePath, InputTypes inputTypes, String name) throws IOException {
        AtomicReference<String> pojoTemplateHeader = javaBeanStringTemplate.getBeanStringTemplate();

        StringBuffer propertiesBuffer = new StringBuffer();

        String metaStringCollect = null;

        String pojoName = null;
        switch (inputTypes) {

            case ISFILE:
                metaStringCollect = Files.
                        readAllLines(Paths.get(filePath))
                        .stream()
                        .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                        .toString();
                String filename = Paths.get(filePath).getFileName().toString();
                pojoName = filename.substring(0, filename.lastIndexOf("."));
                System.out.println(pojoName);
                pojoTemplateHeader.set(pojoTemplateHeader + pojoName.substring(0, 1).toUpperCase() + pojoName.substring(1, pojoName.length()) + "{\n\n");
                break;

            case ISTEXT:
                metaStringCollect = filePath;
                pojoName = name;
                pojoTemplateHeader.set(pojoTemplateHeader + pojoName.substring(0, 1).toUpperCase() + pojoName.substring(1, pojoName.length()) + "{\n\n");
                break;
            default:
        }

        getJsonNode(metaStringCollect)
                .fields()
                .forEachRemaining(x -> {

                    String key = x.getKey();

                    String keyType = key.substring(0, 1).toUpperCase() +
                            key.substring(1, key.length());

                    String nodeType = x.getValue().getClass().getSimpleName();

                    System.out.println(x.getKey() + ":::" + nodeType);

                    switch (nodeType) {

                        case "TextNode":
                            String string = "  private String " + key + ";\n";
                            propertiesBuffer.append(string);
                            System.out.println(string);
                            break;
                        case "IntNode":
                            String stringInt = "  private int " + key + ";\n";
                            propertiesBuffer.append(stringInt);
                            System.out.println(stringInt);
                            break;
                        case "ArrayNode":
                            String stringArray = "  private List<" + keyType + "> " + key + ";\n";
                            try {
                                String arrayString = x.getValue().get(0).toString();


                                //数组形式的json因为读取逻辑会导致无法获得key 所以在调用convert的时候getJsonNode方法会处理的
                                //作为主类数组变量，泛型类型应该是该key名称的首字母大写
                                //由于是object 数组 应同时生成 这个类型的object类 所以传入的是该key名称
                                //因为可能会出现非object得数组 如："models":[ "Fiesta", "Focus", "Mustang" ],因为没有属性
                                //会出现构造一个空类的状态
                                convert(arrayString, keyType);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            propertiesBuffer.append(stringArray);
                            System.out.println(stringArray);
                            break;
                        case "ObjectNode":
                            String stringObj = "  private " + keyType + " " + key + ";\n";
                            try {

                                String objectString = x.getValue().toString();

                                //循环读取到ObJNode -> 生成独立的类文件
                                //对于object 作为主类文件来说的这个变量的类型type 实际就是该Key名首字母大写
                                //如："person":{"name":"abc","age":50,"sex":"male","DB":"1988-2-16"}
                                //作为主类来说 是 private Person person; 这里的Person 是key 也是 变量名并以此生成的Person类
                                //也应该以Person 为类名 此处传入convert 方法 （主类person 的 value ,这个key）,之所以重新搞了个keyType
                                //实际上只是将person 首字母大写 作为 属性 就是key的type
                                convert(objectString, keyType);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            propertiesBuffer.append(stringObj);
                            System.out.println(stringObj);
                            break;
                        case "BooleanNode":
                            String stringBoolean = "  private Boolean " + key + ";\n";
                            propertiesBuffer.append(stringBoolean);
                            break;
                        case "DoubleNode":
                            String stringDouble = "  private Double " + key + ";\n";
                            propertiesBuffer.append(stringDouble);
                            break;
                        case "BigIntegerNode":
                            String stringBigInteger = "  private BigInteger " + key + ";\n";
                            propertiesBuffer.append(stringBigInteger);
                            break;
                        case "NullNode":
                            String stringNull = "/*Null value found, check and confirm it*/\n  private NULLType " + key + ";\n";
                            propertiesBuffer.append(stringNull);
                            break;
                        default:
                            String stringUNKNOWTYPE = "This is a UNKNOWTYPE fix it " + key + ";\n";
                            propertiesBuffer.append(stringUNKNOWTYPE);

                    }

                });

        creatJavaPOJOFile(propertiesBuffer, pojoName, pojoTemplateHeader.get());
    }


    /***
     *
     * 写入到文件 .java
     * @param bodyString propertiesBuffer
     * @param pojoName   文件名
     * @param pojoTemplateHeader  文件头
     * @throws IOException IO异常
     */
    private void creatJavaPOJOFile(StringBuffer bodyString, String pojoName, String pojoTemplateHeader) throws IOException {

        String body = bodyString.append("}").toString();
        String fullString = pojoTemplateHeader + body;
        String pojoClassName = Paths.get(pojoName).toString() + ".java";
        Files.deleteIfExists(Paths.get(pojoClassName));
        Files.write(Paths.get(pojoClassName), Collections.singleton(fullString));
    }


    /***
     * 用来处理 Json 文件 是否是数组形式 e.g  [{...}，{...}]
     * 这种形式 因为没有key 会导致读取逻辑失效
     * @param metaString json 的String 文件
     * @return JsonNode
     * @throws IOException IO异常
     */
    private JsonNode getJsonNode(String metaString) throws IOException {

        JsonNode jsonNode = objectMapper.readTree(metaString);
        String nodeType = jsonNode.getClass().getSimpleName();

        if ("ArrayNode".equals(nodeType)) {

            return jsonNode.get(0);
        }

        return jsonNode;
    }

}
