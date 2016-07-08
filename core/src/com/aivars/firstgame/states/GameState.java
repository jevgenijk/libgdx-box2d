package com.aivars.firstgame.states;

import com.aivars.firstgame.Constants;
import com.aivars.firstgame.handlers.AssetHandler;
import com.aivars.firstgame.handlers.ContactHandler;
import com.aivars.firstgame.handlers.InputHandler;
import com.aivars.firstgame.handlers.StateHandler;
import com.aivars.firstgame.utils.BodyFactory;
import com.aivars.firstgame.utils.RevoluteJoint;
import com.aivars.firstgame.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.aivars.firstgame.Constants.*;
import static com.aivars.firstgame.utils.Utils.scale;

public class GameState extends State {

    public static int circleCount = -1;
    public static boolean gameOver = false;
    /**
     * TODO:
     * 1. Top highscore
     * 2. Resetting game / SPLASH SCREEN
     * 3. Randomize obstacle distance
     * 4. Refactoring
     */

    private static boolean isBallOutside = true;
    private World world;
    private RevoluteJoint revoluteJoint;
    private Body circle;
    private Body ball;
    private Joint joint;
    private boolean debugging = false;
    private int lastAngle = 360;
    private ContactHandler contactHandler = new ContactHandler();
    private InputHandler inputHandler;
    private BodyFactory bodyFactory;
    private Preferences preferences;
    private Box2DDebugRenderer debugger;

    public GameState(StateHandler gameStateHandler) {
        super(gameStateHandler);
        gameOver = false;
        assetHandler.load();
        world = new World(new Vector2(0, -9.81f), true);
        bodyFactory = new BodyFactory(world);
        world.setContactListener(contactHandler);
        preferences = gameStateHandler.getApplication().getPreferences();
        debugger = new Box2DDebugRenderer();
        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

        circle = bodyFactory.createCircle(world, BodyDef.BodyType.StaticBody, scale(Constants.WIDTH / 2), scale(Constants.HEIGHT / 2), BIG_CIRCLE_RADIUS);
        ball = bodyFactory.createCircle(world, BodyDef.BodyType.DynamicBody, 0, 0, SMALL_CIRCLE_RADIUS);
        MassData massData = new MassData();
        massData.I = (float) 1;
        ball.setMassData(massData);

        createJoint(BALL_OUTER_LENGTH);
    }

    public static boolean isBallOutside() {
        return isBallOutside;
    }

    public static void setBallOutside(boolean isOutside) {
        isBallOutside = isOutside;
    }

    public static int getCircleCount() {
        return circleCount;
    }

    public static void setGameOver(boolean status) {
        gameOver = status;
    }

    public void createJoint(int scaleB) {
        if (joint != null) {
            world.destroyJoint(joint);
        }

        revoluteJoint = new RevoluteJoint(circle, ball, false);
        revoluteJoint.SetAnchorA(scale(0), scale(0));
        revoluteJoint.SetAnchorB(scale(scaleB), scale(0));
        revoluteJoint.SetMotor(20, 90);
        joint = revoluteJoint.CreateJoint(world);
    }

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);
        disposeBodies();

        double spikeAngle = Utils.calcRotationAngleInDegrees(circle.getPosition().x, circle.getPosition().y, ball.getPosition().x, ball.getPosition().y);
        //Resetting circle angle if current spike angle is somewhere between 0 and 5 degrees
        if (lastAngle >= 360 && spikeAngle > 0 && spikeAngle < 5) {
            lastAngle = 0;
            circleCount++;
        }

        if (lastAngle < spikeAngle) {
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

            lastAngle = (int) (spikeAngle + ANGLE_DISTANCE_BETWEEN_OBSTACLES);
        }

        if (gameOver) {
            stateHandler.setState(StateHandler.StateName.PAUSE);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255 / 255f, 252 / 255f, 252 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        font.setColor(69 / 255f, 68 / 255f, 64 / 255f, 1f);
        font.draw(application.getSpriteBatch(), "SCORE: " + (circleCount == -1 ? 0 : circleCount), 30, Constants.HEIGHT - 30);
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

        if (debugging) {
            debugger.render(world, camera.combined.scl(Constants.PPM));
        }
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        debugger.dispose();
        world.dispose();
        assetHandler.dispose();
    }

    private void disposeBodies() {
        Array<Body> bodies = contactHandler.getRemovableBodies();
        for (int i = 0; i < bodies.size; i++) {
            world.destroyBody(bodies.get(i));
        }
        bodies.clear();
    }

}
