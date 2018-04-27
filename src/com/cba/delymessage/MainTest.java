package com.cba.delymessage;

import com.cba.delymessage.src.InfoEntity;
import com.cba.delymessage.src.TaskNode;
import com.cba.delymessage.src.TimerTasks;

import java.util.HashSet;
import java.util.Timer;
import java.util.stream.IntStream;

/**
 * @author Administrator
 * date 25/02/2018.
 */
public class MainTest {


    public static void main(String[] args) {

        //初始化一个为hashSet 的 数组 长度=3600 为测试方便 暂定为10
        HashSet<TaskNode>[] sets= new HashSet[10];
        //初始化每一个数组单位的hashSet
        IntStream.range(0,10).forEach(slot->sets[slot]=new HashSet<TaskNode>());
        /*------------------------------------------------------------*/
        InfoEntity infoEntity = new InfoEntity();

        infoEntity.setInfos("11111111");
        TaskNode taskNode = new TaskNode();
        taskNode.setCycleNumber(0);
        taskNode.setSlotNumber(2);
        taskNode.setInfos(infoEntity);
        /*------------------------------------------------------------*/
        InfoEntity infoEntity2 = new InfoEntity();

        infoEntity2.setInfos("222222222");
        TaskNode taskNode2 = new TaskNode();
        taskNode2.setCycleNumber(1L);
        taskNode2.setSlotNumber(5);
        taskNode2.setInfos(infoEntity2);
        /*------------------------------------------------------------*/

        InfoEntity infoEntity3 = new InfoEntity();

        infoEntity3.setInfos("3333333333333333333333");
        TaskNode taskNode3 = new TaskNode();
        taskNode3.setCycleNumber(2L);
        taskNode3.setSlotNumber(5);
        taskNode3.setInfos(infoEntity3);

        sets[taskNode.getSlotNumber()].add(taskNode);
        sets[taskNode2.getSlotNumber()].add(taskNode2);
        sets[taskNode3.getSlotNumber()].add(taskNode3);


        TimerTasks timerTasks=new TimerTasks();
        timerTasks.setSets(sets);
        Timer timer= new Timer();

        timer.schedule(timerTasks,0,1000);//0=startNow
    }
}
