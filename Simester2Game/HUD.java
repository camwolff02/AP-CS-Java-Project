import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class HUD {

    public static int HEALTH = 100;
    private ID id;
    private Handler handler;

    public HUD(ID id, Handler handler) {
        this.id = id;
    }

    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0, 100);
    }

    public Rectangle getBounds() {
        return new Rectangle(14, 14, 202, 34);
    }

    public void endGame(Graphics g) {

    }

    public void render(Graphics g) {
        if(HEALTH != 0) { 
            g.setColor(Color.white);
            g.fillRect(14, 14, 202, 34);
            g.setColor(Color.gray);
            g.fillRect(15, 15, 200, 32);
            g.setColor(Color.green);
            g.fillRect(15, 15, HEALTH * 2, 32);
        } else {
            g.setColor(Color.black);
            g.fillRect(0, 0, 640, 640/12*9);
            g.setColor(Color.white);
            g.fillRect(14, 14, 202, 34);
            g.setColor(Color.gray);
            g.fillRect(15, 15, 200, 32);
            g.setColor(Color.white);
            //needs work
//             for(GameObject tempObj : handler.object) {
//                 if(tempObj.getID() == ID.Player) {
//                     g.fillRect(tempObj.getX(), tempObj.getY(), 32, 32);
//                 }
//             }
        }
    }
}