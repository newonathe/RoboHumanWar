import javax.swing.ImageIcon;

public class ArrowAngle extends GameObject {
    int angle;
    boolean willGoDown;
    boolean willGoUp;

    public ArrowAngle(int x, int y, ImageIcon image) {
        super(x, y, image);
    }

    public void generateAngle(Player player) {
        if (player.playerPosition()){
            angle = (int) (Math.random() * 180 + 90); //90-270
            setAngle(angle);
            goUp();
        } else {
            angle = (int) (Math.random() * 180 - 90); //90 - -90
            setAngle(angle);
            goUp();
        }
        isUpOrDown(player);
    }

    public int getAngle() {
        return angle;
    }
    
    public void isUpOrDown(Player player) {
        if (player.playerPosition()) {
            if (angle == 270) {
                goDown();
            } else if (angle == 90) {
                goUp();
            }
        } else {
            if (angle == 90) {
                goDown();
            } else if (angle == -90) {
                goUp();
            }
        }
    }

    public void rotateAngle(Player player) {
        if (willGoUp) {
            rotateAngleUp(player);
        } else if (willGoDown) {
            rotateAngleDown(player);
        }
    }

    private void setAngle(int angle){
        this.angle = angle;
    }

    private void goUp(){
        willGoUp = true;
        willGoDown = false;
    }

    private void goDown(){
        willGoDown = true;
        willGoUp = false;
    }

    private void rotateAngleUp(Player player) {
        if (player.playerPosition()){
            if (270>angle && angle>90) {
                angle++;
            } else {
                angle = 270;
            }
        } else {
            if (90>angle && angle>-90) {
                angle++;
            } else {
                angle = 90;
            }
        }
    }


    private void rotateAngleDown(Player player) {
        if (player.playerPosition()){
            if (270>angle && angle>90) {
                angle--;
            } else {
                angle = 90;
            }
        } else {
            if (90>angle && angle>-90) {
                angle--;
            } else {
                angle = -90;
            }
        }
    }
}
