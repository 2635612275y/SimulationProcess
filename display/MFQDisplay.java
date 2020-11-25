package systemoperator.testone.display;


import systemoperator.testone.process.Pcb;
import systemoperator.testone.queue.*;

import java.awt.*;

/**
 * @author xfb
 * @create 2020--11--21  9:02
 * @date 2020/11/21 - 9:02
 */
public class MFQDisplay {
    private static ReadyQueue readQueue= ReadyQueue.getInstance();
    private static ReadQueueLower readQueuelow= ReadQueueLower.getInstance();
    private static ReadQueueHigher readQueuehigh= ReadQueueHigher.getInstance();
    private static RunQueue runQueue= RunQueue.getInstance();
    private static FinishQueue finishQueue= FinishQueue.getInstance();
    private static WaitQueue waitQueue= WaitQueue.getInstance();

    //多级反馈队列调度算法
    public static synchronized void manyQueue(TextArea textArea, int time) throws Exception {
        while(readQueue.getReadqueue().size()>0){
            int lowerTime = time*4;
            int middleTime = time*2;
            int higherTime = time;

            int higherSize = readQueuehigh.getReadqueue().size();
            int middleSize = readQueue.getReadqueue().size();
            int lowerSize = readQueuelow.getReadqueue().size();
            for(int i = 0;i < higherSize;i++){
                Pcb pcb = readQueuehigh.peekProcess();
                if(pcb != null){
                    textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
                    textArea.append("------HigherQueue--------\n");
                    readQueuehigh.showQueue(textArea);
                    textArea.append("------MiddleQueue--------\n");
                    readQueue.showQueue(textArea);
                    textArea.append("------LowerQueue---------\n");
                    readQueuelow.showQueue(textArea);
                    if(pcb.getRuntime() > higherTime && !pcb.isFlag()){
                        pcb.setFlag(true);
                        Thread.sleep(higherTime*2000);
                        readQueuehigh.removeProcess();
                        readQueue.addProcess(pcb);
                    }else{
                        readQueuehigh.removeProcess();
                        runQueue.addProcess(pcb);
                        textArea.append("\n\n\n");
                        textArea.append("********************************************************************运行队列********************************************************************" + "\n\n");
                        runQueue.showQueue(textArea);
                        textArea.append(pcb.getName()+"正在运行..."+'\n');
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
            for(int i = 0;i < middleSize;i++){
                Pcb pcb = readQueue.peekProcess();
                if(pcb != null){
                    textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
                    textArea.append("------HigherQueue--------"+'\n');
                    readQueuehigh.showQueue(textArea);
                    textArea.append("\n\n");
                    textArea.append("------MiddleQueue--------"+'\n');
                    readQueue.showQueue(textArea);
                    textArea.append("\n\n");
                    textArea.append("------LowerQueue--------"+'\n');
                    readQueuelow.showQueue(textArea);
                    if(pcb.getRuntime() > middleTime && !pcb.isFlag()){
                        pcb.setFlag(true);
                        Thread.sleep(middleTime*2000);
                        readQueue.removeProcess();
                        readQueuelow.addProcess(pcb);
                    } else{
                        readQueue.removeProcess();
                        runQueue.addProcess(pcb);
                        textArea.append("\n\n\n");
                        textArea.append("********************************************************************运行队列********************************************************************" + "\n\n");
                        runQueue.showQueue(textArea);
                        textArea.append(pcb.getName()+"正在运行..."+'\n');
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
            for (int i = 0;i < lowerSize;i++) {
                textArea.setText("");
                Pcb pcb = readQueuelow.peekProcess();
                if (pcb != null) {
                    textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
                    textArea.append("------高优先级队列--------" + '\n');
                    readQueuehigh.showQueue(textArea);
                    textArea.append("\n\n");
                    textArea.append("------中等优先级队列--------" + '\n');
                    readQueue.showQueue(textArea);
                    textArea.append("\n\n");
                    textArea.append("------低等优先级队列--------" + '\n');
                    readQueuelow.showQueue(textArea);
                    if(pcb.getRuntime()>lowerTime&&!pcb.isFlag()){
                        pcb.setFlag(true);
                        Thread.sleep(lowerTime*2000);
                        readQueuelow.removeProcess();
                        readQueuelow.addProcess(pcb);
                    }
                    else{
                        readQueuelow.removeProcess();
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
            }
            textArea.setText("");
            textArea.append("********************************************************************就绪队列********************************************************************" + "\n\n");
            textArea.append("------高优先级队列--------" + '\n');
            readQueuehigh.showQueue(textArea);
            textArea.append("\n\n");
            textArea.append("------中等优先级队列--------" + '\n');
            readQueue.showQueue(textArea);
            textArea.append("\n\n");
            textArea.append("------低等优先级队列--------" + '\n');
            readQueuelow.showQueue(textArea);
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
