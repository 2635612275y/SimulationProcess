package systemoperator.testone.queue;

import systemoperator.testone.process.Pcb;

import java.awt.*;
import java.util.LinkedList;

import static java.lang.StrictMath.random;

/**
 * @author xfb
 * @create 2020--11--21  10:37
 * @date 2020/11/21 - 10:37
 */
public class FinishQueue implements queue {
    private java.util.Queue<Pcb> finishQueue=new LinkedList<Pcb>();
    private  static final FinishQueue instance=new FinishQueue();

    @Override
    public void addProcess(Pcb pcb) {
        pcb.setState("Finished");
        finishQueue.offer(pcb);
    }

    @Override
    public void removeProcess() {
        finishQueue.poll();
    }

    @Override
    public void showQueue(TextArea textArea) {
        textArea.append("  进程名" + "      " + "进程ID" + "      " + "优先级"+ "      " +"到达时间"+
                "      " + "等待时间" + "      " + "需求运行时间" + "      " + "已用CPU时间" + "      " + "响应比" + "      " +
                "进程状态" + "      " + "阻塞原因" + "\n");
        for (Pcb pcb : finishQueue) {
            textArea.append(pcb.toString()+"\n");
        }

    }

    @Override
    public Pcb peekProcess() {
        return finishQueue.peek();
    }

    public java.util.Queue getFinishQueue() {
        return finishQueue;
    }

    public void showtime(TextArea textArea){
        int aroundSum = 0;//记录队列总的周转时间
        int turnAroundSum = 0;//记录队列总的带权周转时间
        textArea.setText("");
        textArea.append("**********************************************************CompletedQueueTurnAroundState**************************************************************" + "\n\n");
        for (Pcb pcb : finishQueue) {
            textArea.append("  进程名" + "      " + "进程ID" + "      " + "优先级"+ "      " +"到达时间"+
                    "      " + "等待时间" + "      " + "需求运行时间" + "      " + "已用CPU时间" + "      " + "响应比" + "      " +
                    "进程状态" + "      " + "阻塞原因" + "\n");
            textArea.append(pcb.toString() + "\n\n");
            int runTime = pcb.getRuntime();
            int arriveTime = pcb.getArriveTime();
            int finTime=(int)Math.abs(random()*10 + runTime + arriveTime);
            //周转时间 = 完成时间 - 到达时间
            //带权周转时间 = 周转时间 / 运行时间（服务时间）
            textArea.append("\t\t\t" + "周转时间：  " + (finTime - arriveTime) + "\t\t\t\t" +
                    "带权周转时间：  "+(finTime - arriveTime) / runTime+"\n\n\n");

            aroundSum += (finTime - arriveTime);
            turnAroundSum +=((finTime - arriveTime) / runTime);

        }
        //平均周转时间 = 周转总时间 / 作业个数
        //平均带权周转时间 = 带权周转总时间 / 作业个数
        textArea.append("***********************************************************CompletedQueueAveTurnAroundState***********************************************************" + "\n\n");
        textArea.append("\t\t\t" + "平均周转时间：  "+(aroundSum / finishQueue.size()) + "\t\t\t\t" +
                " 平均带权周转时间：  "+(turnAroundSum / finishQueue.size())+ "\n");
    }

    public static FinishQueue getInstance(){
        return instance;
    }

    public void removewaitPcb(Pcb pcb){
        for (Pcb pcb1 : finishQueue) {
            if (pcb1.equals(pcb)) {
                finishQueue.remove(pcb);
            }
        }

    }

}
