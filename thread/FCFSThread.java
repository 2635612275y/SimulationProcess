package systemoperator.testone.thread;

import systemoperator.testone.display.FCFSDisplay;

import java.awt.*;

/**
 * @author xfb
 * @create 2020--11--21  16:32
 * @date 2020/11/21 - 16:32
 */
public class FCFSThread implements Runnable{
    private TextArea textArea;
    public FCFSThread(TextArea textArea){
        this.textArea = textArea;
    }
    @Override
    public void run() {
        try {
            FCFSDisplay.firstArrive(this.textArea);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
