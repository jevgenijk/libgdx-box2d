package com.aivars.firstgame.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class RevoluteJoint {
    RevoluteJointDef revoluteJointDef;

    public RevoluteJoint(Body bodyA, Body bodyB, boolean collideConnected){
        revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA=bodyA;
        revoluteJointDef.bodyB=bodyB;
        revoluteJointDef.collideConnected=collideConnected;
    }

    public void SetAnchorA(float x,float y){
        revoluteJointDef.localAnchorA.set(x,y);
    }

    public void SetAnchorB(float x,float y){
        revoluteJointDef.localAnchorB.set(x,y);
    }

    public void SetMotor(float torque,float speed){
        revoluteJointDef.enableMotor=true;
        revoluteJointDef.maxMotorTorque=torque;
        revoluteJointDef.motorSpeed=-speed*MathUtils.degreesToRadians;
    }

    public Joint CreateJoint(World world){
        return world.createJoint(revoluteJointDef);
    }
}