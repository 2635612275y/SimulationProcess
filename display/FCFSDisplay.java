package systemoperator.testone.display;


import systemoperator.testone.process.Pcb;
import systemoperator.testone.queue.FinishQueue;
import systemoperator.testone.queue.ReadyQueue;
import systemoperator.testone.queue.RunQueue;
import systemoperator.testone.queue.WaitQueue;

import java.awt.*;
import java.io.IOException;

/**
 * @author xfb
 * @create 2020--11--21  9:02
 * @date 2020/11/21 - 9:02
 */
public class FCFSDisplay {
    private static ReadyQueue readQueue = ReadyQueue.getInstance();
    private static RunQueue runQueue = RunQueue.getInstance();
    private static FinishQueue finishQueue = FinishQueue.getInstance();
    private static WaitQueue waitQueue = WaitQueue.getInstance();

    //先来先到服务算法
    public static synchronized void firstArrive(TextArea textArea) throws IOException {
        while(readQueue.getReadqueue().size() > 0){
            int size = readQueue.getReadqueue().size();
            for(int i = 0;i < size;i++){
                Pcb pcb = readQueue.peekProcess();
                if(pcb != null){
                    textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
                    readQueue.showQueue(textArea);
                    readQueue.removeProcess();
                    runQueue.addProcess(pcb);
                    textArea.append("\n\n");
                    textArea.append("********************************************************************运行队列********************************************************************" + "\n\n");
                    runQueue.showQueue(textArea);
                    textArea.append(pcb.getName() + "正在运行..." + "\n");
                    pcb.run();
                    runQueue.removeProcess();
                    textArea.append("\n\n");
                    textArea.append("********************************************************************已完成队列********************************************************************" + "\n\n");
                    if(!pcb.getState().equalsIgnoreCase("wait")){
                        finishQueue.addProcess(pcb);
                    }
                    finishQueue.showQueue(textArea);
                    textArea.append("\n\n");
                    textArea.append("********************************************************************阻塞队列********************************************************************" + "\n\n");
                    waitQueue.showQueue(textArea);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                textArea.setText("");
            }
            textArea.setText("");
            textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
            readQueue.showQueue(textArea);
            textArea.append("\n\n");
            textArea.append("********************************************************************运行队列********************************************************************" + "\n\n");
            runQueue.showQueue(textArea);
            textArea.append("\n\n");
            textArea.append("********************************************************************已完成队列********************************************************************" + "\n\n");
            finishQueue.showQueue(textArea);
            textArea.append("\n\n");
            textArea.append("********************************************************************阻塞队列********************************************************************" + "\n\n");
            waitQueue.showQueue(textArea);
        }
    }

}
