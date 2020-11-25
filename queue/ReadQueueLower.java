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
public class ReadQueueLower implements queue {
    private java.util.Queue<Pcb> lowReadQueue=new LinkedList<Pcb>();
    private  static final ReadQueueLower instance=new ReadQueueLower();

    @Override
    public void  addProcess(Pcb pcb) {
        lowReadQueue.offer(pcb);
    }

    @Override
    public void removeProcess() {
        lowReadQueue.poll();
    }

    @Override
    public void showQueue(TextArea textArea) {
        textArea.append("  进程名" + "      " + "进程ID" + "      " + "优先级"+ "      " +"到达时间"+
                "      " + "等待时间" + "      " + "需求运行时间" + "      " + "已用CPU时间" + "      " + "响应比" + "      " +
                "进程状态" + "      " + "阻塞原因" + "\n");
        for (Pcb pcb : lowReadQueue) {
            textArea.append(pcb.toString() + "\n");
        }

    }

    @Override
    public Pcb peekProcess() {
        if(lowReadQueue.size()==0){
            return null;
        }
        return lowReadQueue.peek();
    }
    //按照优先级排序
    public void PRISort(){
        int count = lowReadQueue.size();
        List<Pcb> list = new ArrayList<>(lowReadQueue);
        for(int i = 0;i < count;i++){
            lowReadQueue.poll();
        }
        list.sort((o1, o2) -> (int) (o2.getNumber() - o1.getNumber()));
        for(Pcb pcb: list){
            lowReadQueue.offer(pcb);
        }
        list.clear();
    }

    public java.util.Queue getReadqueue() {
        return lowReadQueue;
    }

    public static ReadQueueLower getInstance(){
        return instance;
    }

}
