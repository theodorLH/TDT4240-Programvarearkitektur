package com.mads.tdt4240.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mads.tdt4240.game.Game;
import com.mads.tdt4240.game.AI.PongAI;
import com.mads.tdt4240.game.sprites.Ball;
import com.mads.tdt4240.game.sprites.Button;
import com.mads.tdt4240.game.sprites.Paddle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PongState extends State{

    private final int WIN_TARGET = 21;

    private Texture bg;
    private Ball ball;
    private Paddle paddleL;
    private Paddle paddleR;
    private Button exit;

    BitmapFont font = new BitmapFont();

    private com.mads.tdt4240.game.AI.PongAI PongAI;


    public PongState(GameStateManagerSingleton gsm, Paddle paddleL, Paddle paddleR) {
        super(gsm);
        bg = new Texture("bg.png");
        exit = new Button("exit.png");
        exit.setPosition(Game.WIDTH/2 - exit.getTexture().getWidth()/2 ,Game.HEIGHT - exit.getTexture().getHeight());
        ball = new Ball();
        ball.setPosition(Game.WIDTH / 2 - ball.getTexture().getWidth()/2, Game.HEIGHT / 2- ball.getTexture().getHeight()/2);
        ball.setRandomVelocity();
        this.paddleL = paddleL;
        this.paddleR = paddleR;
        PongAI = new PongAI(paddleR, paddleL, ball);
        paddleL.resetPos();
        paddleR.resetPos();

    }

    private void checkCollision() {
        if(ball.getBounds().overlaps(paddleR.getBounds()) || ball.getBounds().overlaps(paddleL.getBounds())) {
            ball.collide();
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touch);
            paddleL.touchControl(touch);
            if (exit.getBounds().contains(touch.x, touch.y)) {
                gsm.set(new MenuState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        if(paddleR.getPoints() >= WIN_TARGET){
            gsm.set(new EndState(gsm, "CPU"));
        }
        if(paddleL.getPoints() >= WIN_TARGET){
            gsm.set(new EndState(gsm, "Player"));
        }
        handleInput();
        checkCollision();
        ball.update(dt);
        paddleL.update(dt);
        PongAI.update();
        paddleR.update(dt);
        if(ball.getPosition().x < 0){
            paddleR.addPoint();
            gsm.set(new PongState(gsm, paddleL, paddleR));
        } else if(ball.getPosition().x + ball.getBounds().getWidth() > Game.WIDTH){
            paddleL.addPoint();
            gsm.set(new PongState(gsm, paddleL, paddleR));
        }

    }



    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg,0,0, Game.WIDTH, Game.HEIGHT);
        sb.draw(ball.getTexture(), ball.getPosition().x, ball.getPosition().y);
        sb.draw(paddleL.getTexture(), paddleL.getPosition().x, paddleL.getPosition().y);
        sb.draw(paddleR.getTexture(), paddleR.getPosition().x, paddleR.getPosition().y);
        font.draw(sb, "Player: " + paddleL.getPoints(), 10, Game.HEIGHT -10);
        font.draw(sb, "CPU: " + paddleR.getPoints(),Game.WIDTH-75 ,Game.HEIGHT-10);
        sb.draw(exit.getTexture(), exit.getPosition().x, exit.getPosition().y);
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        ball.dispose();
    }
}
