package systemoperator.testone.queue;


import systemoperator.testone.process.Pcb;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @author xfb
 * @create 2020--11--21  8:49
 * @date 2020/11/21 - 8:49
 */
public class ReadQueueHigher implements queue {
    private java.util.Queue<Pcb> highReadQueue=new LinkedList<Pcb>();
    private  static final ReadQueueHigher instance=new ReadQueueHigher();

    @Override
    public void  addProcess(Pcb pcb) {
        highReadQueue.offer(pcb);
    }

    @Override
    public void removeProcess() {
        highReadQueue.poll();
    }

    @Override
    public void showQueue(TextArea textArea) {
        textArea.append("  进程名" + "      " + "进程ID" + "      " + "优先级"+ "      " +"到达时间"+
                "      " + "等待时间" + "      " + "需求运行时间" + "      " + "已用CPU时间" + "      " + "响应比" + "      " +
                "进程状态" + "      " + "阻塞原因" + "\n");
        for (Pcb pcb : highReadQueue) {
            textArea.append(pcb.toString() + "\n");
        }

    }

    @Override
    public Pcb peekProcess() {
        if(highReadQueue.size()==0){
            return null;
        }
        return highReadQueue.peek();
    }
    //按照优先级排序
    public void PRISort(){
        int count = highReadQueue.size();
        List<Pcb> list = new ArrayList<>(highReadQueue);
        for(int i = 0;i < count;i++){
            highReadQueue.poll();
        }
        list.sort((o1, o2)->(int)(o2.getNumber()-o1.getNumber()));
        for(Pcb pcb: list){
            highReadQueue.offer(pcb);
        }
        list.clear();
    }

    public java.util.Queue getReadqueue() {
        return highReadQueue;
    }

    public static ReadQueueHigher getInstance(){
        return instance;
    }

}
