package com.mads.tdt4240.game.AI;


import com.mads.tdt4240.game.sprites.Ball;
import com.mads.tdt4240.game.sprites.Paddle;

public interface AIState {

    public void update(Paddle paddle, Ball ball);
}
