package systemoperator.testone.queue;

import systemoperator.testone.process.Pcb;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xfb
 * @create 2020--11--121  8:49
 * @date 2020/11/21 - 8:49
 */
public class ReadyQueue implements queue {
    private java.util.Queue<Pcb> readQueue=new LinkedList<Pcb>();
    private  static final ReadyQueue instance=new ReadyQueue();

    @Override
    public void  addProcess(Pcb pcb) {
        readQueue.offer(pcb);
    }

    @Override
    public void removeProcess() {
        readQueue.poll();
    }

    @Override
    public void showQueue(TextArea textArea) {
        textArea.append("  进程名" + "      " + "进程ID" + "      " + "优先级"+ "      " +"到达时间"+
                "      " + "等待时间" + "      " + "需求运行时间" + "      " + "已用CPU时间" + "      " + "响应比" + "      " +
                "进程状态" + "      " + "阻塞原因" + "\n");
        for (Pcb pcb : readQueue) {
            textArea.append(pcb.toString()+'\n');
        }

    }

    @Override
    public Pcb peekProcess() {
        if(readQueue.size() == 0){
            return null;
        }
        return readQueue.peek();
    }
    //按照优先级排序
    public void PRISort(){
        int count = readQueue.size();
        List<Pcb> list = new ArrayList<>(readQueue);
        for(int i = 0;i < count;i++){
            readQueue.poll();
        }

        list.sort((o1,o2)-> (int) (o2.getNumber()-o1.getNumber()));
        for(Pcb pcb:list){
            readQueue.offer(pcb);
        }
        list.clear();

    }
    //计算响应比的时间，然后排序
    //响应比的计算：(等待时间 + 运行时间) / 运行时间
    public void HRRNSort(){
        int count = readQueue.size();
        List<Pcb> list = new ArrayList<>(readQueue);
        for(int i = 0;i < count;i++){
            readQueue.poll();
        }

        list.sort((o1,o2)->o2.getRepose()-o1.getRepose());
        for(Pcb pcb: list){
            readQueue.offer(pcb);
        }
        list.clear();
    }

    public java.util.Queue getReadqueue() {
        return readQueue;
    }

    public static ReadyQueue getInstance(){
        return instance;
    }

}
