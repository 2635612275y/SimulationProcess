package systemoperator.testone.thread;


import systemoperator.testone.display.MFQDisplay;

import java.awt.*;

/**
 * @author xfb
 * @create 2020--11--21  16:32
 * @date 2020/11/21 - 16:32
 */
public class MFQThread implements Runnable{
    private TextArea textArea;
    private int time;
    public MFQThread(TextArea textArea, int time){

        this.textArea=textArea;
        this.time=time;
    }
    @Override
    public void run() {
        try {
            MFQDisplay.manyQueue(textArea,time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
