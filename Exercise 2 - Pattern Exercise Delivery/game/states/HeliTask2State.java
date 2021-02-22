package com.mads.tdt4240.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mads.tdt4240.game.Game;
import com.mads.tdt4240.game.sprites.Button;
import com.mads.tdt4240.game.sprites.Heli;


public class HeliTask2State extends State {

    BitmapFont font = new BitmapFont();

    private Texture background;
    private Heli heli;
    private Button exit;



    public HeliTask2State(GameStateManagerSingleton gsm){
        super(gsm);
        background = new Texture("bg_heli.png");
        exit = new Button("exit.png");
        exit.setPosition(Game.WIDTH/2 - exit.getTexture().getWidth()/2 ,Game.HEIGHT - exit.getTexture().getHeight());
        heli = new Heli(Game.WIDTH / 2, Game.HEIGHT / 2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touch);
            heli.touchControl(touch);
            if (exit.getBounds().contains(touch.x, touch.y)) {
                gsm.set(new MenuState(gsm));
            }
            }
    }



    @Override
    public void update(float dt) {
        handleInput();
        heli.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(heli.getTexture(), heli.getPosition().x, heli.getPosition().y);
        font.draw(sb, "x: "+heli.getPosition().x+"y:"+heli.getPosition().y,10, Game.HEIGHT-25);
        sb.draw(exit.getTexture(), exit.getPosition().x, exit.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
