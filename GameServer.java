import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    private Socket p1Socket;
    private Socket p2Socket;

   private ReadRotateArrow rra1;
   private ReadRotateArrow rra2;
   private ReadChargePower rcp1;
   private ReadChargePower rcp2;
   private ReadFiringProjectile rfp1;
   private ReadFiringProjectile rfp2;
   private WriteRotateArrow wra1;
   private WriteRotateArrow wra2;
   private WriteChargePower wcp1;
   private WriteChargePower wcp2;
   private WriteFiringProjectile wfp1;
   private WriteFiringProjectile wfp2;

    private boolean humanTurn;
    private Player player;

    private int humanXPosition, YPosition, robotXPosition, ThrowStrength;
    private ImageIcon gunshot, energyorb;
    private String s;
    private double angle;

    public GameServer() {
        System.out.println("===== GAME SERVER =====");
        numPlayers = 0;
        maxPlayers = 2;

        try {
            ss = new ServerSocket(45371);
        } catch (IOException e) {
            System.out.println("Error from Constructor GS");
        }
    }

    public void acceptConnections () {
        try {
            System.out.println("Waiting for players...");
            while (numPlayers < maxPlayers) {
                Socket s = ss.accept();
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected!");
                ReadRotateArrow rra = new ReadRotateArrow(in, numPlayers);
                ReadChargePower rcp = new ReadChargePower(in, numPlayers);
                ReadFiringProjectile rfp = new ReadFiringProjectile(in, numPlayers);
                WriteRotateArrow wra = new WriteRotateArrow(out, numPlayers);
                WriteChargePower wcp = new WriteChargePower(out, numPlayers);
                WriteFiringProjectile wfp = new WriteFiringProjectile(out, numPlayers);
                if (numPlayers == 1) {
                    p1Socket = s;
                    rra1 = rra;
                    rcp1 = rcp;
                    rfp1 = rfp;
                    wra1 = wra;
                    wcp1 = wcp;
                    wfp1 = wfp;

                } else if (numPlayers == 2) {
                    p2Socket = s;
                    rra2 = rra;
                    rcp2 = rcp;
                    rfp2 = rfp;
                    wra2 = wra;
                    wcp2 = wcp;
                    wfp2 = wfp;
                    
                    Thread readThread1 = new Thread(rra1);
                    Thread readThread2 = new Thread(rcp1);
                    Thread readThread3 = new Thread(rfp1);
                    Thread readThread4 = new Thread(rra2);
                    Thread readThread5 = new Thread(rcp2);
                    Thread readThread6 = new Thread(rfp2);

                    readThread1.start();
                    readThread2.start();
                    readThread3.start();
                    readThread4.start();
                    readThread5.start();
                    readThread6.start();

                    Thread writeThread1 = new Thread(wra1);
                    Thread writeThread2 = new Thread(wcp1);
                    Thread writeThread3 = new Thread(wfp1);
                    Thread writeThread4 = new Thread(wra2);
                    Thread writeThread5 = new Thread(wcp2);
                    Thread writeThread6 = new Thread(wfp2);
                    
                    writeThread1.start();
                    writeThread2.start();
                    writeThread3.start();
                    writeThread4.start();
                    writeThread5.start();
                    writeThread6.start();
                }

            }
        } catch (IOException ex) {
                System.out.println("Error from acceptConnections GS");
        }
    }

    private class ReadRotateArrow implements Runnable {
        private ObjectInputStream in;
        private int playerID;

        public ReadRotateArrow(ObjectInputStream in, int player) {
            this.in = in;
            playerID = player;
            System.out.println("RFC " + player + " Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    humanTurn = in.readBoolean();
                    try {
                        player = (Player) in.readObject(); // Cast the object to Player type
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error: Player class not found");
                    }
                } else {
                    humanTurn = in.readBoolean();
                    try {
                        player = (Player) in.readObject(); // Cast the object to Player type
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error: Player class not found");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error from ReadRotateArrow");
            }
        }
    }

    private class ReadChargePower implements Runnable {
        private ObjectInputStream in;
        private int playerID;

        public ReadChargePower(ObjectInputStream in, int player) {
            this.in = in;
            playerID = player;
            System.out.println("RFC " + player + " Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    humanTurn = in.readBoolean();
                } else {
                    humanTurn = in.readBoolean();
                }
            } catch (IOException e) {
                System.out.println("Error from ReadChargePower");
            }
        }
    }

    private class ReadFiringProjectile implements Runnable {
        private ObjectInputStream in;
        private int playerID;
        
        public ReadFiringProjectile(ObjectInputStream in, int player) {
            this.in = in;
            playerID = player;
            System.out.println("RFP " + player + " Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    humanTurn = in.readBoolean();
                    humanXPosition = in.readInt();
                    YPosition = in.readInt();
                    gunshot = (ImageIcon) gunshot;
                    ThrowStrength = in.readInt();
                    angle = in.readDouble();
                    s = in.readUTF();
                } else {
                    humanTurn = in.readBoolean();
                    robotXPosition = in.readInt();
                    YPosition = in.readInt();
                    energyorb = (ImageIcon) energyorb;
                    ThrowStrength = in.readInt();
                    angle = in.readDouble();
                    s = in.readUTF();
                }
            } catch (IOException e) {
                System.out.println("Error from ReadFiringProjectile");
            }
        }
    }

    public class WriteRotateArrow implements Runnable {
        private ObjectOutputStream out;
        private int playerID;

        public WriteRotateArrow(ObjectOutputStream out, int player) {
            this.out = out;
            playerID = player;
            System.out.println("WRA " + player + " Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    out.writeBoolean(humanTurn);
                    out.writeObject(player);
                } else {
                    out.writeBoolean(!humanTurn);
                    out.writeObject(player);
                }
            } catch (IOException e) {
                System.out.println("Error from WriteRotateArrow");
            }
        }
    }

    public class WriteChargePower implements Runnable {
        private ObjectOutputStream out;
        private int playerID;

        public WriteChargePower(ObjectOutputStream out, int player) {
            this.out = out;
            playerID = player;
            System.out.println("WCP " + player + " Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    out.writeBoolean(humanTurn);
                } else {
                    out.writeBoolean(!humanTurn);
                }
            } catch (IOException e) {
                System.out.println("Error from WriteChargePower");
            }
        }
    }

    public class WriteFiringProjectile implements Runnable {
        private ObjectOutputStream out;
        private int playerID;

        public WriteFiringProjectile(ObjectOutputStream out, int player) {
            this.out = out;
            playerID = player;
            System.out.println("WFP " + player + " Runnable created");
        }

        public void run() {
            try {
                if (playerID == 1) {
                    out.writeBoolean(humanTurn);
                    out.writeInt(humanXPosition);
                    out.writeInt(YPosition);
                    out.writeObject(gunshot);
                    out.writeInt(ThrowStrength);
                    out.writeDouble(angle);
                    out.writeUTF(s);
                } else {
                    out.writeBoolean(!humanTurn);
                    out.writeInt(robotXPosition);
                    out.writeInt(YPosition);
                    out.writeObject(energyorb);
                    out.writeInt(ThrowStrength);
                    out.writeDouble(angle);
                    out.writeUTF(s);
                }
            } catch (IOException e) {
                System.out.println("Error from WriteFiringProjectile");
            }
        }
    }


    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}




