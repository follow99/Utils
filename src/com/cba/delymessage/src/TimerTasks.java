package com.cba.delymessage.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TimerTask;

/**
 * @author Administrator
 * date 25/02/2018.
 */
public class TimerTasks extends TimerTask{

    /** 设置每秒在hashSet 内 下标 */
    private int index=0;
    /** 注入hashSet */
    private HashSet<TaskNode>[] sets;

    /** 确定循环边界值*/
    private final int cycleRange=9;

    public void setSets(HashSet<TaskNode>[] sets) {
        this.sets = sets;
    }

    /** 处理逻辑 */
    private void send(){
        Iterator<TaskNode> iterator = sets[index].iterator();
        iterator.forEachRemaining(taskNode -> {
            if (taskNode.getCycleNumber()==0){

                /*只是简单取出封装内的消息*/
                System.out.println("延迟消息: "+taskNode.getInfoEntity().getInfos()+" 已投递");
                sets[index].remove(taskNode);
            }else {
                System.out.println("本轮 taskNode 圈数 为 ："+taskNode.getCycleNumber());
                taskNode.setCycleNumber(taskNode.getCycleNumber()-1);
                System.out.println("下轮 taskNode 圈数 为 ："+taskNode);
            }

        });

    }

    @Override
    public void run() {
        System.out.println(index);

        send();

        // index==9
        if (index==cycleRange){
            index=0;
        }else {
            index++;
        }
    }
}
