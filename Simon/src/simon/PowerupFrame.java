package simon;

public class PowerupFrame {

    float time;

    Runnable activate;

    Runnable deactivate;

    public Powerup getPowerup() {
        return new Powerup(activate, deactivate, time);
    }
}
