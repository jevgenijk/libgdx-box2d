package com.aivars.firstgame.levels;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.states.GameState;
import com.aivars.firstgame.utils.RevoluteJoint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.aivars.firstgame.utils.Utils.scale;

public class SHITLEVEL extends Level implements ContactListener, InputProcessor {

    private int topCircleRadius = 85;
    private int topBallRadius = 5;
    private int bottomCircleRadius = 85;
    private int bottomBallRadius = 5;
    private int rotationSpeed = -150;//150

    private int topBallOuterLength = 90;
    private int topBallInnerLength = 70;
    private int bottomBallOuterLength = 90;
    private int bottomBallInnerLength = 70;

    private Body topCircle;
    private Body topBall;
    private Body bottomCircle;
    private Body bottomBall;
    private Joint currentJoint;

    private boolean createTop = false;
    private boolean createBottom = false;
    private boolean top = true;
    private boolean outside = true;

    private int ballOffset = 350;

    public SHITLEVEL(GameState gameState) {
        super(gameState);

        topCircle = bodyFactory.createCircle(BodyDef.BodyType.StaticBody, new Vector2(scale(Constants.WIDTH / 2), scale(Constants.HEIGHT / 1.5f)), topCircleRadius);
        Body ballSensor = bodyFactory.createRectangle(BodyDef.BodyType.StaticBody, new Vector2(topCircle.getPosition().x,topCircle.getPosition().y-scale(topCircleRadius)), 0, scale(5), scale(30), "ballSensor", true);
        bottomCircle = bodyFactory.createCircle(BodyDef.BodyType.StaticBody, new Vector2(scale(Constants.WIDTH / 2), topCircle.getPosition().y - scale(2 * topCircleRadius + 15)), bottomCircleRadius);

        topBall = bodyFactory.createCircle(BodyDef.BodyType.DynamicBody, new Vector2(0, scale(Constants.HEIGHT)), topBallRadius, true,"topBall");

        bottomBall = bodyFactory.createCircle(BodyDef.BodyType.DynamicBody, new Vector2(topBall.getPosition().x, topBall.getPosition().y+scale(ballOffset)), bottomBallRadius, true,"bottomBall");
        currentJoint = createJoint(topCircle, topBall, topBallOuterLength, rotationSpeed);
    }

    public Joint createJoint(Body circle, Body ball, int scaleB, int speed) {
        if (currentJoint != null) {
            world.destroyJoint(currentJoint);
        }

        RevoluteJoint revoluteJoint = new RevoluteJoint(circle, ball, false);
        revoluteJoint.SetAnchorA(scale(0), scale(0));
        revoluteJoint.SetAnchorB(scale(scaleB), scale(0));
        revoluteJoint.SetMotor(20, speed);
        return revoluteJoint.CreateJoint(world);
    }

    private int totalDT = 0;
    private int nextDT = 0;
    private int timeOffset = 20;

    public void update(float dt) {
        totalDT++;

        if(totalDT > nextDT){
            if (createBottom) {
                top = false;
                gameState.addRemovableBody(topBall);
                bottomBall = bodyFactory.createCircle(BodyDef.BodyType.DynamicBody, new Vector2(topBall.getPosition().x, topBall.getPosition().y+scale(ballOffset)), bottomBallRadius, true,"bottomBall");
                currentJoint = createJoint(bottomCircle, bottomBall, bottomBallOuterLength, -rotationSpeed);
                nextDT = totalDT + timeOffset;
                createBottom = false;
            }

            if (createTop) {
                top = true;
                gameState.addRemovableBody(bottomBall);
                topBall = bodyFactory.createCircle(BodyDef.BodyType.DynamicBody, new Vector2(bottomBall.getPosition().x, bottomBall.getPosition().y-scale(ballOffset)), topBallRadius, true,"topBall");
                currentJoint = createJoint(topCircle, topBall, topBallOuterLength, rotationSpeed);
                nextDT = totalDT + timeOffset;
                createTop = false;
            }
        }else{
            createBottom = false;
            createTop = false;
        }

    }

    public void render() {
        Gdx.gl.glClearColor(255 / 255f, 252 / 255f, 252 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        int circleSize = 178;
        int ballSize = 16;
        spriteBatch.draw(assetHandler.getSprite("circle.png"), topCircle.getPosition().x * Constants.PPM - (circleSize / 2), topCircle.getPosition().y * Constants.PPM - (circleSize / 2), circleSize, circleSize);
        spriteBatch.draw(assetHandler.getSprite("circle.png"), bottomCircle.getPosition().x * Constants.PPM - (circleSize / 2), bottomCircle.getPosition().y * Constants.PPM - (circleSize / 2), circleSize, circleSize);

        if(top){
            spriteBatch.draw(assetHandler.getSprite("ball.png"), topBall.getPosition().x * Constants.PPM - (ballSize / 2), topBall.getPosition().y * Constants.PPM - (ballSize / 2), ballSize, ballSize);
        }else{
            spriteBatch.draw(assetHandler.getSprite("ball.png"), bottomBall.getPosition().x * Constants.PPM - (ballSize / 2), bottomBall.getPosition().y * Constants.PPM - (ballSize / 2), ballSize, ballSize);
        }
        spriteBatch.end();
    }


    public void beginContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();
        System.out.println(fa.isSensor() + " - " + fb.isSensor());
        if (fa.isSensor() && fb.isSensor() && fa.getBody().getUserData() != null && fb.getBody().getUserData() != null) {
            if (fa.getBody().getUserData().equals("topBall") || fb.getBody().getUserData().equals("topBall")) {
                System.out.println("gotcha1");
                createBottom = true;
            }else if(fa.getBody().getUserData().equals("bottomBall") || fb.getBody().getUserData().equals("bottomBall")){
                System.out.println("gotcha2");
                createTop = true;
            }
        }
    }

    public void endContact(Contact c) {

    }

    public void preSolve(Contact c, Manifold m) {

    }

    public void postSolve(Contact c, ContactImpulse ci) {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (outside) {
            if (currentJoint.getBodyA().equals(bottomCircle)) {
                createJoint(bottomCircle, bottomBall, bottomBallInnerLength, rotationSpeed);
            } else {
                createJoint(topCircle, topBall, topBallInnerLength, rotationSpeed);
            }
        } else {
            if (currentJoint.getBodyA().equals(bottomCircle)) {
                createJoint(bottomCircle, bottomBall, bottomBallOuterLength, rotationSpeed);
            } else {
                createJoint(topCircle, topBall, topBallOuterLength, rotationSpeed);
            }
        }

        outside = !outside;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
