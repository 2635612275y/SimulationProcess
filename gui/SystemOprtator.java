package systemoperator.testone.gui;


import systemoperator.testone.process.Pcb;
import systemoperator.testone.queue.*;
import systemoperator.testone.thread.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

import static java.lang.StrictMath.random;


/**
 * @author xfb
 * @create 2020--11--21  10:37
 * @date 2020/11/21 - 10:37
 */
public class SystemOprtator extends JFrame {
    private TextArea textArea=new TextArea(27,77);
    private JComboBox<String> jComboBox=new JComboBox<>();

    private JLabel input = new JLabel("Input Process Number:");
    private JFrame jFrame = new JFrame();
    private TextField text1=new TextField(2);
    private JPanel panel=new JPanel();
    private JButton start=new JButton("Start");
    private JButton add = new JButton("AddProcess");
    private JButton show = new JButton("showTurnaroundTime");
    private JButton wait = new JButton("WaitProcess");

    public SystemOprtator(){
        setJFrame();
    }
    private void setJFrame(){
        this.setSize(1000,600);
        this.setTitle("Simulation process");
        panel.setLayout(new FlowLayout());
        jComboBox.setFont(new Font("宋体", Font.BOLD, 15));
        jComboBox.addItem("FCFSProcess");
        jComboBox.addItem("RRProcess");
        jComboBox.addItem("PRIProcess");
        jComboBox.addItem("HRRNProcess");
        jComboBox.addItem("MFQProcess");
        input.setFont(new Font("宋体", Font.BOLD, 15));
        textArea.setFont(new Font("宋体",Font.BOLD,15));
        start.setFont(new Font("宋体",Font.BOLD,15));
        start.setBackground(Color.red);
        show.setFont(new Font("宋体",Font.BOLD,15));
        show.setBackground(Color.yellow);
        add.setFont(new Font("宋体",Font.BOLD,15));
        add.setBackground(Color.green);
        wait.setFont(new Font("宋体",Font.BOLD,15));
        wait.setBackground(Color.pink);

        panel.add(input);
        panel.add(text1);
        panel.add(jComboBox);
        panel.add(start);
        panel.add(show);
        panel.add(add);
        panel.add(wait);
        panel.add(textArea);
        this.add(panel);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation( (width - 1000) / 2, (height - 600) / 2);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //对选项算法按钮进行监听
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String info=(String) jComboBox.getSelectedItem();
                initInfo(info);
                switch (Objects.requireNonNull(info)){
                    case "FCFSProcess" ://先来先服务调度算法
                        new Thread(new FCFSThread(textArea)).start();
                        break;
                    case "RRProcess" ://时间片轮转调度算法
                        String str = JOptionPane.showInputDialog(panel,
                                "InputTimeLice", "Enter", JOptionPane.PLAIN_MESSAGE);
                        if(str != null){
                            new Thread(new RRThread(textArea,Integer.parseInt(str))).start();
                        }
                        break;
                    case "PRIProcess" ://优先调度算法
                        new Thread(new PRIThread(textArea)).start();
                        break;
                    case "HRRNProcess" ://作业调度算法
                        new Thread(new HRRNThread(textArea)).start();
                        break;
                    case "MFQProcess" ://多级反馈队列调度算法
                        String str1 = JOptionPane.showInputDialog(panel,
                                "InputTimeLice","输入对话框",JOptionPane.PLAIN_MESSAGE);
                        if(str1 != null){
                            new Thread(new MFQThread(textArea,Integer.parseInt(str1))).start();
                        }
                        break;
                }
            }
        });
        //对添加进程进行监听
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new AddProcessThread(textArea, text1)).start();
            }
        });
        //对显示带权周转时间进行监听
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinishQueue finishqueue = FinishQueue.getInstance();
                finishqueue.showtime(textArea);
            }
        });
        wait.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new WaitProcessThread(textArea)).start();
            }
        });

    }
    private void initInfo(String msg){
        ReadyQueue readQueue = ReadyQueue.getInstance();
        ReadQueueHigher higherQueue = ReadQueueHigher.getInstance();
        ReadQueueLower lowQueue = ReadQueueLower.getInstance();
        RunQueue runQueue= RunQueue.getInstance();
        FinishQueue finishQueue= FinishQueue.getInstance();
        readQueue.getReadqueue().clear();
        higherQueue.getReadqueue().clear();
        lowQueue.getReadqueue().clear();
        runQueue.getRunQueue().clear();
        finishQueue.getFinishQueue().clear();
        String info = text1.getText();
        if (info == null) {
            info = "0";
        }
        int count = Integer.parseInt(info);
        //进入多级反馈队列
        if(msg.equals("MFQProcess")) {
            for (int i = 0; i < count; i++) {
                Pcb pcb = new Pcb();
                Pcb nextPcb = readQueue.peekProcess();
                //初始化进程数据进程名/进程ID/进程优先级数/到达时间/等待时间/响应比/需要运行时间/已用CPU时间
                //响应比的计算：(等待时间+运行时间)/运行时间
                pcb.setName("process" + (i + 1));
                pcb.setID(i + 1);//
                if (nextPcb == null) {
                    pcb.setNumber((int)Math.abs(random()*9+1));
                    pcb.setArriveTime(0);
                    pcb.setWaitTime(0);
                    pcb.setRepose(1);
                    pcb.setRuntime((int)Math.abs(random()*5+1));
                    pcb.setUsedCPUTime(pcb.getRuntime());
                } else {
                    pcb.setArriveTime(nextPcb.getRuntime());
                    pcb.setNumber((int)Math.abs(random()*9+1));
                    pcb.setRuntime(new Random().nextInt(4) + 1);
                    pcb.setUsedCPUTime(new Random().nextInt(pcb.getRuntime()) + 1);
                    pcb.setUsedCPUTime(pcb.getRuntime());
                    int sum = (Math.abs(nextPcb.getArriveTime() + nextPcb.getRuntime()) - pcb.getArriveTime());
                    pcb.setWaitTime(sum);
                    if (pcb.getWaitTime() == 0) {
                        pcb.setWaitTime(new Random().nextInt(8) + 1); //等待时间：上一个进程开始时间+上一个进程运行时间-本次进程的到达时间
                        int waitTime = pcb.getWaitTime();
                        int runtime = pcb.getRuntime();
                        pcb.setRepose((waitTime / runtime) + 1);  //初始化响应比(等待时间 / 运行时间) + 1
                    } else{
                        int waittime = pcb.getWaitTime();
                        int runtime = pcb.getRuntime();
                        pcb.setRepose((waittime / runtime) + 1);  //计算响应比
                    }
                }
                pcb.setState("Read");
                //按照优先级划分 0-3进lower 4-6进middle 7-9进higher
//                System.out.println(pcb.getNumber() + "\n");
                if(pcb.getNumber() > 0 && pcb.getNumber() <= 3){
                    lowQueue.addProcess(pcb);
                } else if(pcb.getNumber() > 3 && pcb.getNumber() <= 6){
                    readQueue.addProcess(pcb);
                } else{
                    higherQueue.addProcess(pcb);
                }
            }
            lowQueue.PRISort();
            readQueue.PRISort();
            higherQueue.PRISort();
        } else{
           for(int i = 0; i < count;i++){
                Pcb pcb = new Pcb();
                Pcb lastpcb = readQueue.peekProcess();
                pcb.setName("process" + (i + 1));
                pcb.setID(i + 1);
                if(lastpcb == null){
                    pcb.setNumber((int)(Math.abs(random()*9 + 1)));
                    pcb.setArriveTime(0);
                    pcb.setWaitTime(0);
                    pcb.setRepose(1);
                    pcb.setRuntime((int)Math.abs(random()*5 + 1));
                    pcb.setUsedCPUTime(pcb.getRuntime());

                }else{
                    pcb.setArriveTime(lastpcb.getRuntime());
                    pcb.setNumber((int)Math.abs(random()*9 + 1));
                    pcb.setRuntime((int)Math.abs(random()*4 + 1));
                    pcb.setUsedCPUTime((int)Math.abs(random()*pcb.getRuntime()+1));
                    pcb.setUsedCPUTime(pcb.getRuntime());

                    int sum=(Math.abs(lastpcb.getArriveTime()+lastpcb.getRuntime())-pcb.getArriveTime());
//                    System.out.println(lastpcb.getArriveTime() + "," + lastpcb.getRuntime() + "," + pcb.getArriveTime() + "," + sum);
                    pcb.setWaitTime(sum);//等待时间：上一个进程等待时间 + 上一个进程运行时间 - 本次进程的到达时间
                    if(pcb.getWaitTime() == 0){
                        pcb.setWaitTime(new Random().nextInt(8)+1);
                        int waitTime = pcb.getWaitTime();
                        int runTime = pcb.getRuntime();
                        pcb.setRepose((waitTime / runTime) + 1);
                    }
                    else {
                        int waitTime = pcb.getWaitTime();
                        int runTime = pcb.getRuntime();
                        pcb.setRepose((waitTime / runTime) + 1);
                    }
                }
                pcb.setState("Read");
               readQueue.addProcess(pcb);
           }
        }

    }
}
