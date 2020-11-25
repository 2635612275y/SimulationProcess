package systemoperator.testone.thread;

import systemoperator.testone.display.PRIDisplay;

import java.awt.*;

/**
 * @author xfb
 * @create 2020--11--21  16:32
 * @date 2020/11/21 - 16:32
 */
public class PRIThread implements Runnable{
    private TextArea textArea;
    public PRIThread(TextArea textArea){
        this.textArea=textArea;
    }
    @Override
    public void run() {
        try {
            PRIDisplay.priority(textArea);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
