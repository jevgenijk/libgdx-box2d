package com.aivars.firstgame.levels;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.StateHandler;
import com.aivars.firstgame.states.GameState;
import com.aivars.firstgame.utils.RevoluteJoint;
import com.aivars.firstgame.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import static com.aivars.firstgame.utils.Utils.scale;

public class FirstLevel extends Level implements ContactListener, InputProcessor {

    private int bigCircleRadius = 85;
    private int ballRadius = 5;
    private int ballOuterLength = 100;
    private int ballInnerLength = 70;
    private int circleCount = 0;
    private float startSpawnDelayTime = 0.6f;
    private float spawnDelayTime = 0.5f;
    private boolean isBallOutside = true;
    private Body circle;
    private Body ball;
    private Joint joint;

    public FirstLevel(GameState gameState) {
        super(gameState);
        circle = bodyFactory.createCircle(BodyDef.BodyType.StaticBody, new Vector2(scale(Constants.WIDTH / 2), scale(Constants.HEIGHT / 2)), bigCircleRadius);
        ball = bodyFactory.createCircle(BodyDef.BodyType.DynamicBody, new Vector2(0, 0), ballRadius);
        MassData massData = new MassData();
        massData.I = (float) 1;
        ball.setMassData(massData);

        createJoint(ballOuterLength);

        Timer.schedule(new Timer.Task() {
                           @Override
                           public void run() {
                               updateUsingTimer();
                           }
                       }
                , startSpawnDelayTime
                , spawnDelayTime
        );
    }

    public void createJoint(int scaleB) {
        if (joint != null) {
            world.destroyJoint(joint);
        }

        RevoluteJoint revoluteJoint = new RevoluteJoint(circle, ball, false);
        revoluteJoint.SetAnchorA(scale(0), scale(0));
        revoluteJoint.SetAnchorB(scale(scaleB), scale(0));
        revoluteJoint.SetMotor(20, 150);
        joint = revoluteJoint.CreateJoint(world);
    }

    public void updateUsingTimer() {
        double spikeAngle = Utils.calcRotationAngleInDegrees(circle.getPosition().x, circle.getPosition().y, ball.getPosition().x, ball.getPosition().y);
        int obstacleRandomPosition = (int) (Math.random() * 2 + 1);
        int x = (int) (circle.getPosition().x * Constants.PPM + (bigCircleRadius + 10) * Math.cos(spikeAngle * Math.PI / 180F));
        int y = (int) (circle.getPosition().y * Constants.PPM + (bigCircleRadius + 10) * Math.sin(spikeAngle * Math.PI / 180F));
        int sensorX = (int) (circle.getPosition().x * Constants.PPM + (bigCircleRadius - 10) * Math.cos(spikeAngle * Math.PI / 180F));
        int sensorY = (int) (circle.getPosition().y * Constants.PPM + (bigCircleRadius - 10) * Math.sin(spikeAngle * Math.PI / 180F));

        Vector2 obstaclePosition = obstacleRandomPosition == 1 ? new Vector2(scale(x), scale(y)) : new Vector2(scale(sensorX), scale(sensorY));
        Vector2 sensorPosition = obstacleRandomPosition == 1 ? new Vector2(scale(sensorX), scale(sensorY)) : new Vector2(scale(x), scale(y));
        float angle = (float) Math.toRadians(spikeAngle + 90);
        float obstacleWidth = scale(3);
        float obstacleHeight = scale(10);

        Body obstacleBody = bodyFactory.createRectangle(BodyDef.BodyType.StaticBody, obstaclePosition, angle, obstacleWidth, obstacleHeight, "obstacle", false);
        bodyFactory.createRectangle(BodyDef.BodyType.StaticBody, sensorPosition, angle, obstacleWidth, obstacleHeight, obstacleBody, true);

    }

    public void update(float dt) {
    }

    public void render() {
        Gdx.gl.glClearColor(255 / 255f, 252 / 255f, 252 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        bitmapFont.setColor(69 / 255f, 68 / 255f, 64 / 255f, 1f);
        bitmapFont.draw(application.getSpriteBatch(), "SCORE: " + circleCount, 30, Constants.HEIGHT - 30);
        int circleSize = 178;
        spriteBatch.draw(assetHandler.getSprite("circle.png"), circle.getPosition().x * Constants.PPM - (circleSize / 2), circle.getPosition().y * Constants.PPM - (circleSize / 2), circleSize, circleSize);

        int ballSize = 16;
        spriteBatch.draw(assetHandler.getSprite("ball.png"), ball.getPosition().x * Constants.PPM - (ballSize / 2), ball.getPosition().y * Constants.PPM - (ballSize / 2), ballSize, ballSize);


        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() != null && body.getUserData().equals("obstacle")) {
                Sprite obstacleSprite = assetHandler.getSprite("obstacle.png");
                obstacleSprite.setRotation((float) Math.toDegrees(body.getAngle()));
                obstacleSprite.setSize(6, 20);
                obstacleSprite.setPosition(body.getPosition().x * Constants.PPM - (6 / 2), body.getPosition().y * Constants.PPM - (20 / 2));
                obstacleSprite.draw(spriteBatch);
            }
        }

        spriteBatch.end();
    }


    public void beginContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getBody().getUserData() != null) {
            if (fa.isSensor()) {
                Body userData = (Body) fa.getBody().getUserData();
                gameState.addRemovableBody(userData);
                gameState.addRemovableBody(fa.getBody());
                circleCount++;
            } else if (fa.getBody().getUserData().equals("obstacle")) {
                StateHandler.setState(StateHandler.StateName.GAME_OVER);
                if(preferencesHandler.getInteger("first_highscore") < circleCount){
                    preferencesHandler.putInteger("first_highscore",circleCount);
                }
            }
        }

        if (fb.getBody().getUserData() != null) {
            if (fb.isSensor()) {
                Body userData = (Body) fb.getBody().getUserData();
                gameState.addRemovableBody(userData);
                gameState.addRemovableBody(fb.getBody());
                circleCount++;
            } else if (fb.getBody().getUserData().equals("obstacle")) {
                StateHandler.setState(StateHandler.StateName.GAME_OVER);
                if(preferencesHandler.getInteger("first_highscore") < circleCount){
                    preferencesHandler.putInteger("first_highscore",circleCount);
                }
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
        if (isBallOutside) {
            createJoint(ballInnerLength);
        } else {
            createJoint(ballOuterLength);
        }

        isBallOutside = !isBallOutside;
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
