package com.cba.delymessage.src;

/**
 * @author Administrator
 * date 25/02/2018.
 */
public class InfoEntity {


    //具体业务逻辑类 暂时只封装一个 string
    //可以根据业务需要添加需要的属性  如 id function 这些
    private String infos;

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "InfoEntity{" +
                "infos='" + infos + '\'' +
                '}';
    }
}
