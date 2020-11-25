package systemoperator.testone.display;


import systemoperator.testone.process.Pcb;
import systemoperator.testone.queue.FinishQueue;
import systemoperator.testone.queue.ReadyQueue;
import systemoperator.testone.queue.RunQueue;
import systemoperator.testone.queue.WaitQueue;

import java.awt.*;

/**
 * @author xfb
 * @create 2020--11--21  9:02
 * @date 2020/11/21 - 9:02
 */
public class RRDisplay {
    private static ReadyQueue readQueue= ReadyQueue.getInstance();
    private static RunQueue runQueue= RunQueue.getInstance();
    private static FinishQueue finishQueue= FinishQueue.getInstance();
    private static WaitQueue waitQueue= WaitQueue.getInstance();

    //时间片轮转调度算法
    public static synchronized void timeSlice(TextArea textArea, int timeLice) throws Exception {
        int runTime = timeLice;//时间片
        while(readQueue.getReadqueue().size() > 0){
            int count = readQueue.getReadqueue().size();
            for(int i = 0;i < count;i++){
                Pcb pcb = readQueue.peekProcess();
                if(pcb != null){
                    textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
                    readQueue.showQueue(textArea);
                    if(pcb.getUsedCPUTime() > runTime){
                        pcb.setUsedCPUTime(pcb.getUsedCPUTime() - runTime);
                        Thread.sleep(runTime*2000);
                        readQueue.removeProcess();
                        readQueue.addProcess(pcb);
                    }else{
                        readQueue.removeProcess();
                        runQueue.addProcess(pcb);
                        textArea.append("\n\n\n");
                        textArea.append("********************************************************************运行队列********************************************************************" + "\n\n");
                        runQueue.showQueue(textArea);
                        textArea.append(pcb.getName() + "正在运行..." + '\n');
                        pcb.run();
                        runQueue.removeProcess();
                        textArea.append("\n\n\n");
                        textArea.append("********************************************************************已完成队列********************************************************************" + "\n\n");
                        if(!pcb.getState().equalsIgnoreCase("wait")){
                            finishQueue.addProcess(pcb);
                        }
                        finishQueue.showQueue(textArea);
                        textArea.append("\n\n\n");
                        textArea.append("********************************************************************阻塞队列********************************************************************" + "\n\n");
                        waitQueue.showQueue(textArea);
                        Thread.sleep(3000);
                    }
                }
                textArea.setText("");
            }
            textArea.setText("");
            textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
            readQueue.showQueue(textArea);
            textArea.append("\n\n\n");
            textArea.append("********************************************************************运行队列********************************************************************" + "\n\n");
            runQueue.showQueue(textArea);
            textArea.append("\n\n\n");
            textArea.append("********************************************************************已完成队列********************************************************************" + "\n\n");
            finishQueue.showQueue(textArea);
            textArea.append("\n\n\n");
            textArea.append("********************************************************************阻塞队列********************************************************************" + "\n\n");
            waitQueue.showQueue(textArea);
        }

    }

}
