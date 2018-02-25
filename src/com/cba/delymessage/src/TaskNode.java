package com.cba.delymessage.src;

/**
 * @author Administrator
 * date 25/02/2018.
 */
public class TaskNode {
    /** 循环圈数 每小时一圈 超过一小时 圈数+1 e.g 延迟为两小时后执行 cycleNumber=1 从0 开始 */
    private long cycleNumber;
    /**所在秒数位置（3600 格 每秒一格）*/
    private int slotNumber;
    /** 用来封装具体业务信息*/
    private InfoEntity infos;


    public void setInfos(InfoEntity infos) {
        this.infos = infos;
    }

    public InfoEntity getInfoEntity() {
        return infos;
    }

    public long getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(long cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    @Override
    public String toString() {
        return "TaskNode{" +
                "cycleNumber=" + cycleNumber +
                ", slotNumber=" + slotNumber +
                ", infos=" + infos +
                '}';
    }
}
