package com.aivars.firstgame.levels.first;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.levels.Level;
import com.aivars.firstgame.levels.first.handlers.ContactHandler;
import com.aivars.firstgame.levels.first.handlers.InputHandler;
import com.aivars.firstgame.states.GameState;
import com.aivars.firstgame.utils.RevoluteJoint;
import com.aivars.firstgame.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.utils.Array;

import static com.aivars.firstgame.Constants.*;
import static com.aivars.firstgame.utils.Utils.scale;

public class FirstLevel extends Level {

    private static int circleCount = 0;
    private boolean isBallOutside = true;
    private Body circle;
    private Body ball;
    private Joint joint;
    private int nextObstaclePosition = 360;


    public FirstLevel(GameState gameState) {
        super(gameState);
        world.setContactListener(contactHandler);
        Gdx.input.setInputProcessor(inputHandler);

        circleCount = 0;
        circle = bodyFactory.createCircle(BodyDef.BodyType.StaticBody, new Vector2(scale(Constants.WIDTH / 2), scale(Constants.HEIGHT / 2)), BIG_CIRCLE_RADIUS);
        ball = bodyFactory.createCircle(BodyDef.BodyType.DynamicBody, new Vector2(0, 0), SMALL_CIRCLE_RADIUS);
        MassData massData = new MassData();
        massData.I = (float) 1;
        ball.setMassData(massData);

        createJoint(BALL_OUTER_LENGTH);
    }

    public void increaseCircleCount() {
        circleCount++;
    }

    public boolean isBallOutside() {
        return isBallOutside;
    }

    public void setBallOutside(boolean isOutside) {
        isBallOutside = isOutside;
    }

    public void createJoint(int scaleB) {
        if (joint != null) {
            world.destroyJoint(joint);
        }

        RevoluteJoint revoluteJoint = new RevoluteJoint(circle, ball, false);
        revoluteJoint.SetAnchorA(scale(0), scale(0));
        revoluteJoint.SetAnchorB(scale(scaleB), scale(0));
        revoluteJoint.SetMotor(20, 120);
        joint = revoluteJoint.CreateJoint(world);
    }

    public void update(float dt) {
        double spikeAngle = Utils.calcRotationAngleInDegrees(circle.getPosition().x, circle.getPosition().y, ball.getPosition().x, ball.getPosition().y);

        if (nextObstaclePosition < 0) {
            nextObstaclePosition = (int) (360 + (spikeAngle - ANGLE_DISTANCE_BETWEEN_OBSTACLES));
        }

        if (nextObstaclePosition > spikeAngle && (nextObstaclePosition - spikeAngle < ANGLE_DISTANCE_BETWEEN_OBSTACLES)) {
            int obstacleRandomPosition = (int) (Math.random() * 2 + 1);
            int x = (int) (circle.getPosition().x * Constants.PPM + (BIG_CIRCLE_RADIUS + 10) * Math.cos(spikeAngle * Math.PI / 180F));
            int y = (int) (circle.getPosition().y * Constants.PPM + (BIG_CIRCLE_RADIUS + 10) * Math.sin(spikeAngle * Math.PI / 180F));
            int sensorX = (int) (circle.getPosition().x * Constants.PPM + (BIG_CIRCLE_RADIUS - 10) * Math.cos(spikeAngle * Math.PI / 180F));
            int sensorY = (int) (circle.getPosition().y * Constants.PPM + (BIG_CIRCLE_RADIUS - 10) * Math.sin(spikeAngle * Math.PI / 180F));

            Vector2 obstaclePosition = obstacleRandomPosition == 1 ? new Vector2(scale(x), scale(y)) : new Vector2(scale(sensorX), scale(sensorY));
            Vector2 sensorPosition = obstacleRandomPosition == 1 ? new Vector2(scale(sensorX), scale(sensorY)) : new Vector2(scale(x), scale(y));
            float angle = (float) Math.toRadians(spikeAngle + 90);
            float obstacleWidth = scale(3);
            float obstacleHeight = scale(10);

            Body obstacleBody = bodyFactory.createRectangle(BodyDef.BodyType.StaticBody, obstaclePosition, angle, obstacleWidth, obstacleHeight, "obstacle", false);
            Body sensorBody = bodyFactory.createRectangle(BodyDef.BodyType.StaticBody, sensorPosition, angle, obstacleWidth, obstacleHeight, obstacleBody, true);

            nextObstaclePosition = (int) spikeAngle - ANGLE_DISTANCE_BETWEEN_OBSTACLES;
        }
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

    public void dispose() {

    }

    @Override
    public void setContactHandler() {
        contactHandler = new ContactHandler(this);
    }

    @Override
    public void setInputHandler() {
        inputHandler = new InputHandler(this);
    }

}
