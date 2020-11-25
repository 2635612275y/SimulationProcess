package systemoperator.testone.queue;


import systemoperator.testone.process.Pcb;

import java.awt.*;

/**
 * @author xfb
 * @create 2020--11--21  8:47
 * @date 2020/11/21 - 8:47
 */
public interface queue {
    public void addProcess(Pcb pcb);
    public void removeProcess();
    public void showQueue(TextArea textArea);
    public Pcb peekProcess();
}
