import javax.swing.ImageIcon;

public class ArrowAngle extends GameObject {
    int angle;
    boolean isMaxAngle;
    boolean isMinAngle;

    public ArrowAngle(int x, int y, ImageIcon image) {
        super(x, y, image);   
    }

    public void generateAngle(Player player) {
        if (player.playerPosition()){
            angle = (int) (Math.random() * 180 + 90); //90-270
            setAngle(angle);
        } else {
            angle = (int) (Math.random() * 180 - 90); //90 - -90
            setAngle(angle);
        }
        isMaxAngle(player);
    }

    private void setAngle(int angle){
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public void isMaxAngle(Player player) {
        if (player.playerPosition()) {
            if (angle == 270) {
                isMaxAngle = true;
            } else {
                isMaxAngle = false;
            }
        } else {
            if (angle == 90) {
                isMaxAngle = true;
            } else {
                isMaxAngle = false;
            }
        }
    }

    public void rotateAngleUp(Player player) {
        if (player.playerPosition()){
            if (270>angle && !isMaxAngle) {
                angle++;
                isMaxAngle(player);
            } else {
                angle = 270;
            }
        } else {
            if (90>angle && !isMaxAngle) {
                angle++;
                isMaxAngle(player);
            } else {
                angle = 90;
            }
        }
    }

    public void isMinAngle(Player player) {
        if (player.playerPosition()) {
            if (angle == 90) {
                isMinAngle = true;
            } else {
                isMinAngle = false;
            }
        } else {
            if (angle == -90) {
                isMinAngle = true;
            } else {
                isMinAngle = false;
            }
        }
    }

    public void rotateAngleDown() {
    }
}
