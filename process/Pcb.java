package systemoperator.testone.process;

/**
 * @author xfb
 * @create 2020--11--21  8:27
 * @date 2020/11/21 - 8:27
 */
public class Pcb {
    private String name;//进程名
    private int ID;//进程ID
    private int number;//优先级数
    private int arriveTime;//到达时间
    private int waitTime;//等待时间
    private int runTime;//需要运行时间
    private int usedCPUTime;//已用时间
    private int repose;//响应比
    private boolean flag;
    private String state;//进程状态
    private String cause;//阻塞原因

    public Pcb() {
    }

    public Pcb(String name, int ID, int number, int arriveTime,
               int runTime, int usedCPUTime, String state, String cause) {
        this.name = name;
        this.ID = ID;
        this.number = number;
        this.arriveTime = arriveTime;
        this.runTime = runTime;
        this.usedCPUTime = usedCPUTime;
        this.state = state;
        this.cause = cause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(int arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getRuntime() {
        return runTime;
    }

    public void setRuntime(int runTime) {
        this.runTime = runTime;
    }

    public int getUsedCPUTime() {
        return usedCPUTime;
    }

    public void setUsedCPUTime(int usedCPUTime) {
        this.usedCPUTime = usedCPUTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getRepose() {
        return repose;
    }

    public void setRepose(int repose) {
        this.repose = repose;
    }

    public void run(){
//        System.out.println(this.name + "正在运行...");
        try {
            Thread.sleep(this.runTime*2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return name +"\t " + ID + "\t     " + number + "\t\t "+ arriveTime + "\t\t"+
                waitTime + "\t\t   " + runTime + "\t\t        " + usedCPUTime + "\t\t       " + repose + "\t\t"+
                 state +"\t   " + cause + '\t'
                ;
    }
}
