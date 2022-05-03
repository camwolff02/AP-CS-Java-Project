import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class YumYum extends GameObject {
    
    private Handler handler;
    
    public YumYum(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        
        this.handler = handler;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }
    
    public void tick() {}
    
    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillRect(x, y, 16, 16);
    }
}