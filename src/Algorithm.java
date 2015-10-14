import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Huiyu on 2015/10/12.
 */
public class Algorithm extends Thread{
    static List waitingPeopleList = new ArrayList();

    @Override
    public void run() {
        // 网络层 使用UDP
        byte buf[] = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(6666);
        } catch (SocketException e) {
            e.printStackTrace();
        }
//        System.out.println("The port is:" + ds.getLocalPort());

        while (true) {
            try {
                ds.receive(dp);
                String message = new String(buf, 0, dp.getLength(), "utf-8");
                Scanner scanner = new Scanner(message);
                int loadFloor = scanner.nextInt();
                int unloadFloor = scanner.nextInt();
                waitingPeopleList.add(new People(loadFloor, unloadFloor));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Elevator elevator = new Elevator();
        // 多线程 multiple thread
        new Algorithm().start();
//        int pNum;
//        System.out.println("有几个人按下电梯按钮：");
//        Scanner scanner = new Scanner(System.in);
//        pNum = scanner.nextInt();
//        System.out.println("请输入他们所在楼层以及想去楼层：");
//
//        for (int i = 0; i < pNum; i++) {
//            int loadFloor = scanner.nextInt();
//            int unloadFloor = scanner.nextInt();
//            waitingPeopleList.add(new People(loadFloor, unloadFloor));
//        }

        while (true) {
            while(waitingPeopleList.size() != 0 || elevator.getElevatorList().size() != 0) {
                System.out.printf("现在在第" + elevator.getCurrentFloor() + "层 ");

                // 判断此层是否有人上电梯
                // judge if there are anyone get in the elevator
                // 上电梯的人，必须方向相同
                // the one who get in elevator must have the same direction with the elevator
                for (int i = waitingPeopleList.size() - 1; i >= 0; i--) {
                    People p = (People) waitingPeopleList.get(i);
                    if (elevator.getCurrentFloor() == p.getLoadFloor() && elevator.getDirection() == p.getUpOrDown()) {
                        waitingPeopleList.remove(i);
                        elevator.loadPeople(p);
                        System.out.print("上 ");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                //判断此层是否有人下电梯
                List elevatorList = elevator.getElevatorList();
                for (int i = elevatorList.size() - 1; i >= 0; i--) {
                    People p = (People) elevatorList.get(i);
                    if (p.getUnloadFloor() == elevator.getCurrentFloor()) {
                        elevator.unloadPeople(i);
                        System.out.printf("下 ");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                //判断此方向上是否还有人在等待
                int flag = 0;
                for (int i = 0; i < waitingPeopleList.size(); i++) {
                    People p = (People) waitingPeopleList.get(i);
                    if (elevator.getDirection() == 1) {
                        if (p.getLoadFloor() > elevator.getCurrentFloor()) {
                            flag = 1;
                        }
                    } else {
                        if (p.getLoadFloor() < elevator.getCurrentFloor()) {
                            flag = 1;
                        }
                    }
                }

//                if (flag != 1 && elevatorList.size() == 0) {
//                    // 转向
//                    if (elevator.getDirection() == 1) {
//                        elevator.setDirection(0);
//                    } else {
//                        elevator.setDirection(1);
//                    }
//                    // 如果转向的话，就判断有没有需要上电梯 暂停一段时间（实际中运行相应动作）
//                    // 然后向下运动（并不需要判断需不需要下人），根据本算法，要下电梯已经下了
//                    for (int i = waitingPeopleList.size() - 1; i >= 0; i--) {
//                        People p = (People) waitingPeopleList.get(i);
//                        if (elevator.getCurrentFloor() == p.getLoadFloor() && elevator.getDirection() == p.getUpOrDown()) {
//                            waitingPeopleList.remove(i);
//                            elevator.loadPeople(p);
//                            System.out.print("上 ");
//                            try {
//                                Thread.sleep(500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    if (elevator.getDirection() == 1) {
//                        elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
//                    } else {
//                        elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
//                    }
//                } else {
//                    if (elevator.getDirection() == 1) {
//                        elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
//                    } else {
//                        elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
//                    }
//                }

                System.out.println("电梯上有" + elevatorList.size() + "个人");

                if (flag != 1 && elevatorList.size() == 0) {
                    if (elevator.getDirection() == 1) {
                        elevator.setDirection(0);
                    } else {
                        elevator.setDirection(1);
                    }
                }

                // 如果还有人就不会改变方向
                if (flag == 1 || elevatorList.size() != 0) {
                    if (elevator.getDirection() == 1) {
                        elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
                    } else {
                        elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
                    }
                }

//                // 如果还有人就不会改变方向
//                if (flag == 1 || elevatorList.size() != 0) {
//
//                }


                // 过某一层楼所必要的时间
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


//        System.out.println("the result is:");
//        for (int i = 0; i < pNum; i++) {
//            People p = (People) waitingPeopleList.get(i);
//            System.out.println(p.getLoadFloor() + " " + p.getUnloadFloor());
//        }
//        waitingPeopleList.remove(3);
//        System.out.println(waitingPeopleList.size());


    }
}
