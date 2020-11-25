package systemoperator.testone.thread;

import systemoperator.testone.display.RRDisplay;

import java.awt.*;

/**
 * @author xfb
 * @create 2020--11--21  16:32
 * @date 2020/11/21 - 16:32
 */
public class RRThread implements Runnable{
    private TextArea textArea;
    private int timeLice;
    public RRThread(TextArea textArea, int timeLice){
        this.textArea = textArea;
        this.timeLice = timeLice;
    }
    @Override
    public void run() {
        try {
            RRDisplay.timeSlice(textArea, timeLice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
