import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.util.Random;

public class Player extends GameObject {

    Random r = new Random();
    Handler handler = new Handler();

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH - 38);
        y = Game.clamp(y, 0, Game.HEIGHT - 60);
        
        handler.addObject(new Trail(x, y, ID.Trail, Color.white, 32, 32, 0.12f, handler));
        
        collision();
        
    }

    private void collision() {
        for(GameObject tempObject : handler.object) {
            if(tempObject.getID() == ID.BasicEnemy) { //tempObject is now BasicEnemy
                if(getBounds().intersects(tempObject.getBounds())) {
                    //collision code
                    HUD.HEALTH -= 2;
                }
            }
        }
    }
    
    public void end() {
        if(HUD.HEALTH == 0)  {
            x = Game.getWIDTH()/2-32;
            y = Game.getHEIGHT()/2-32;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, 32, 32);
    }   
}
