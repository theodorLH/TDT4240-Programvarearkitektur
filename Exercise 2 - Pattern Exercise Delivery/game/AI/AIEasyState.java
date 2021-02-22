package com.mads.tdt4240.game.AI;

import com.mads.tdt4240.game.sprites.Ball;
import com.mads.tdt4240.game.sprites.Paddle;


public class AIEasyState implements AIState {

    private static final float SPEED = 0.15f;


    public AIEasyState() {
    }

    @Override
    public void update(Paddle paddle, Ball ball) {
        if(paddle.getPosition().y > ball.getPosition().y) {
            paddle.setVelocity(false, this.SPEED);
        } else {
            paddle.setVelocity(true, this.SPEED);
        }
    }
}

