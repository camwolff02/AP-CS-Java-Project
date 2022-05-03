import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Random;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;

    public static final int WIDTH = 640, HEIGHT = WIDTH/12 * 9;

    private Thread thread;
    private boolean running = false;
    private int spawner = 0;
    Color[] colors = new Color[4];

    private Random r;
    private Handler handler;
    private HUD hud;

    public Game() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);

        hud = new HUD(ID.HUD, handler);

        r = new Random();

        handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
        handler.addObject(new YumYum(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.YumYum, handler));
        handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy, handler));
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now; 

            while(delta >= 1) {
                tick();
                delta--;
            }

            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public void move() {
        GameObject tempObj = handler.object.get(0);
        GameObject tempTwo = handler.object.get(0);
        int index = 0;

        for(int i = 0; i < handler.object.size(); i++) {
            if(handler.object.get(i).getID() == ID.YumYum) {
                tempObj = handler.object.get(i);
                index = i;
            }
            if(handler.object.get(i).getID() == ID.Player) {
                tempTwo = handler.object.get(i);
            }
        }

        if(tempObj.getBounds().intersects(tempTwo.getBounds()) ||
            tempObj.getBounds().intersects(hud.getBounds())) {
            handler.object.get(index).setX(r.nextInt(WIDTH));
            handler.object.get(index).setY(r.nextInt(HEIGHT));

            handler.object.get(index).setX(Game.clamp(handler.object.get(index).getX(), 0, Game.WIDTH - 38));
            handler.object.get(index).setY(Game.clamp(handler.object.get(index).getY(), 0, Game.HEIGHT - 60));

            spawner++;
        }           
    }

    private void tick() {
        handler.tick();
        hud.tick();

        move();

        if(spawner % 5 == 0) {
            handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy, handler));
            spawner += 1;
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0, WIDTH, HEIGHT);

        handler.render(g);

        hud.render(g);

        g.dispose();
        bs.show();
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int clamp(int var, int min, int max) {
        if(var >= max)
            return var = max;
        else if(var <= min)
            return var = min;
        return var;
    }

    public static void main(String[] args) {
        new Game();
    }
}