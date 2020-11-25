package systemoperator.testone.thread;

import systemoperator.testone.display.HRRNDisplay;

import java.awt.*;

/**
 * @author xfb
 * @create 2020--11--21  16:32
 * @date 2020/11/21 - 16:32
 */
public class HRRNThread implements Runnable{
    private TextArea textArea;
    public HRRNThread(TextArea textArea){
        this.textArea=textArea;
    }
    @Override
    public void run() {
        try {
            HRRNDisplay.Respons(textArea);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
