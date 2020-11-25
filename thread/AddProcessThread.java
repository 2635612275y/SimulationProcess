package systemoperator.testone.thread;

import systemoperator.testone.process.Pcb;
import systemoperator.testone.queue.ReadyQueue;
import systemoperator.testone.display.FCFSDisplay;

import java.awt.*;
import java.io.IOException;

import static java.lang.StrictMath.random;

/**
 * @description:
 * @author: xfb
 * @time: 2020/11/18 22:25
 */
public class AddProcessThread implements Runnable{
    private TextArea textArea;
    private TextField text1;
    public AddProcessThread(TextArea textArea, TextField text1){
        this.textArea = textArea;
        this.text1 = text1;
    }
    @Override
    public void run() {
        int count = Integer.parseInt(text1.getText());
        ReadyQueue readqueue = ReadyQueue.getInstance();
        Pcb process = readqueue.peekProcess();
        Pcb pcb=new Pcb();
        if(process == null){
            pcb.setName("process" + (count + 1));
            pcb.setArriveTime(0);
            pcb.setID(count);
            pcb.setNumber((int) Math.abs((random()*8 + 1)));
            pcb.setRuntime((int) Math.abs((random()*4 + 1)));
            pcb.setUsedCPUTime((int) Math.abs((random()*pcb.getRuntime()+1)));
            pcb.setState("Read");
            readqueue.addProcess(pcb);
            count++;
            text1.setText("" + count);
        }
        else{
            pcb.setName("process" + (count));
            pcb.setArriveTime(readqueue.peekProcess().getArriveTime()+pcb.getRuntime());
            pcb.setID(count);
            pcb.setNumber((int) Math.abs((random()*8 + 1)));
            pcb.setRuntime((int) Math.abs((random()*4 + 1)));
            pcb.setUsedCPUTime((int) Math.abs((random()*pcb.getRuntime() + 1)));
            pcb.setState("Read");
            readqueue.addProcess(pcb);
            count++;
            text1.setText(""+count);
        }

        try {
            FCFSDisplay.firstArrive(this.textArea);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}