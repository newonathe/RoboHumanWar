import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    private Socket p1Socket, p2Socket;
    private ReadFromClient p1ReadRunnable, p2ReadRunnable;
    private WriteToClient p1WriteRunnable, p2WriteRunnable;

    private double humanX, humanY, robotX, robotY;

    public GameServer() {
        // System.out.println("Game Server");
        numPlayers = 0;
        maxPlayers = 2;

/*         p1x = 100;
        p1y = 400;
        p2x = 490;
        p2y = 400; */

        try {
            ss = new ServerSocket(45651);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer Constructor");
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");

            while (numPlayers < maxPlayers) {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;

                    /* p1WriteRunnable.sendStartMessage();
                    p2WriteRunnable.sendStartMessage(); */

                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();

                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }
            }

            System.out.println("No longer acceptingConections()");
        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");

        }
    }

    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pID, DataInputStream in) {
            playerID = pID;
            dataIn = in;
            System.out.println("RFC " + playerID+ " Runnable created");
        }

        public void run() {
            try {
                while(true) {
                    if (playerID == 1) {
                        humanX = dataIn.readDouble();
                        humanY = dataIn.readDouble();
                    } else {
                        robotX = dataIn.readDouble();
                        robotY = dataIn.readDouble();
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }
        }
    }

    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pID, DataOutputStream out) {
            playerID = pID;
            dataOut = out;
            System.out.println("WTC " + playerID+ " Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    if (playerID==1) {
                        dataOut.writeDouble(robotX);
                        dataOut.writeDouble(robotY);
                        dataOut.flush();
                    } else {
                        dataOut.writeDouble(humanX);
                        dataOut.writeDouble(humanY);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC run()");
            }
        }

        /* public void sendStartMessage() {
            try{
                dataOut.writeUTF("Two players have begun");
            } catch (IOException ex){
                System.out.println("IOException from sendStartMessage()");
            }
        } */
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
