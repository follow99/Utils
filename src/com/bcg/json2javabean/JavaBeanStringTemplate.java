package com.bcg.json2javabean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 * date 28/07/2018.
 */
public interface JavaBeanStringTemplate {

    /***
     * 生成类文件的string 头
     * @return AtomicReference<String> 生成类文件的string 头
     * 默认头文件包含 lombok,spring boot-data-jpa 依赖引用
     */
    default AtomicReference<String> getBeanStringTemplate() {
        return new AtomicReference<>("import lombok.AllArgsConstructor;\n" +
                "import lombok.Data;\n" +
                "import lombok.NoArgsConstructor;\n" +
                "import org.hibernate.annotations.DynamicUpdate;\n" +
                "import javax.persistence.Entity;\n" +
                "import javax.persistence.GeneratedValue;\n" +
                "import javax.persistence.Id;\n" +
                "\n" +
                "/**\n" +
                " * @author Auto-generated by com.bcg.www\n" +
                " * \n" +
                " */\n"+
                "@Entity\n" +
                "@Data\n" +
                "@AllArgsConstructor\n" +
                "@NoArgsConstructor\n" +
                "@DynamicUpdate\n" +
                "public class ");
    }
}
