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
public class HRRNDisplay {
    private static ReadyQueue readqueue= ReadyQueue.getInstance();
    private static RunQueue runqueue= RunQueue.getInstance();
    private static FinishQueue finishqueue= FinishQueue.getInstance();
    private static WaitQueue waitqueue= WaitQueue.getInstance();

    //最高响应比调度
    public static synchronized void Respons(TextArea textArea) throws IOException {
        while(readqueue.getReadqueue().size() > 0){
            int size=readqueue.getReadqueue().size();
            for(int i = 0;i < size;i++){
                textArea.setText("");
                readqueue.HRRNSort();//对就绪队列排序
                Pcb pcb = readqueue.peekProcess();
                if(pcb!=null){
                    textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
                    readqueue.showQueue(textArea);
                    readqueue.removeProcess();
                    runqueue.addProcess(pcb);
                    textArea.append("\n\n\n");
                    textArea.append("********************************************************************运行队列********************************************************************" + "\n\n");
                    runqueue.showQueue(textArea);
                    textArea.append(pcb.getName()+"正在运行..."+'\n');
                    pcb.run();
                    runqueue.removeProcess();
                    textArea.append("\n\n\n");
                    textArea.append("********************************************************************已完成队列********************************************************************" + "\n\n");
                    if(!pcb.getState().equalsIgnoreCase("wait")){
                        finishqueue.addProcess(pcb);
                    }
                    finishqueue.showQueue(textArea);
                    textArea.append("\n\n\n");
                    textArea.append("********************************************************************阻塞队列********************************************************************" + "\n\n");
                    waitqueue.showQueue(textArea);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            textArea.setText("");
            textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
            readqueue.showQueue(textArea);
            textArea.append("\n\n\n");
            textArea.append("********************************************************************运行队列********************************************************************" + "\n\n");
            runqueue.showQueue(textArea);
            textArea.append("\n\n\n");
            textArea.append("********************************************************************已完成队列********************************************************************" + "\n\n");
            finishqueue.showQueue(textArea);
            textArea.append("\n\n\n");
            textArea.append("********************************************************************阻塞队列********************************************************************" + "\n\n");
            waitqueue.showQueue(textArea);
        }

    }

}
