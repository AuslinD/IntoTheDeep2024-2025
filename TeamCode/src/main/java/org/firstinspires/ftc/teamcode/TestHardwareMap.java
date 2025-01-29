package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "hwMapspinleftArm", group = "test")
public class TestHardwareMap extends OpMode {

    DiffyClaw claw;

    @Override
    public void init() {
        claw = new DiffyClaw(this);
    }

    @Override
    public void loop() {

        if(gamepad2.right_trigger > .1){
            claw.leftArm.setPosition(0);
        }
        if(gamepad2.left_trigger > .1){
            claw.leftArm.setPosition(1);
        }
    }
}
