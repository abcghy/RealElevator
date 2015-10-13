import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by Huiyu on 2015/10/13.
 */
public class Client {
    public static void main(String[] args) throws Exception{
        Client client = new Client();
        client.launchFrame();

//        System.out.println("Please input an Customer:");
//        Scanner scanner = new Scanner(System.in);
//        String sendStr = scanner.nextLine();
//
//        byte buf[] = sendStr.getBytes("utf-8");
//        DatagramSocket ds = new DatagramSocket(6667);
//        DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress("127.0.0.1", 6666));
//        ds.send(dp);
    }

    private void launchFrame() throws Exception{
        Frame sendFrame = new Frame("EASY");
        DatagramSocket ds = new DatagramSocket(6667);
        sendFrame.setBounds(300, 300, 300, 300);

        Panel messagePanel = new Panel();
        TextArea messageText = new TextArea(2, 50);
        Button sendButton = new Button("SEND");

        messagePanel.add(messageText);
        messagePanel.add(sendButton);

        sendFrame.add(messagePanel);
        sendFrame.pack();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesStr = messageText.getText();
                if (!mesStr.isEmpty()) {
                    try {
                        byte buf[] = mesStr.getBytes("utf-8");
                        DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress("127.0.0.1", 6666));
                        ds.send(dp);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                messageText.setText("");
            }
        });
        sendFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ds.close();
                System.exit(1);
            }
        });
        sendFrame.setVisible(true);
    }
}
