import javax.swing.*;

public class PowerUp {
    
    ImageIcon heal;
    ImageIcon bigBone;
    ImageIcon fish;
    ImageIcon wind;
    ImageIcon tripleThrow;

    public PowerUp() {
        // heal = new Image("heal.png");
        // bigBone = new Image("bigBone.png");
        // fish = new Image("fish.png");
        // wind = new Image("wind.png");
        // tripleThrow = new Image("tripleThrow.png");
    }

    public void healPowerUp(Player player) {
        if (player.health < player.maxHealth) {
            player.health += 10;
        }
        else {
            player.health = player.maxHealth;
        }
      }
    
    public void bigBonePowerUp(Player player) {
        // player.bigBone();
    }
    
    public void fishPowerUp(Player player) {
        // player.fish();
    }

    public void windPowerUp(Player player) {
        // player.wind();
    }

    public void tripleThrowPowerUp(Player player) {
        // player.tripleThrow();
    }
}
