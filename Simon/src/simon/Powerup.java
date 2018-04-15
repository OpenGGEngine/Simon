package simon;

public class Powerup {
    float time = 0;
    Runnable activate;
    Runnable deactivate;
    float length;

    public Powerup(Runnable activate, Runnable deactivate, float length) {
        this.activate = activate;
        this.deactivate = deactivate;
        this.length = length;
    }
}
