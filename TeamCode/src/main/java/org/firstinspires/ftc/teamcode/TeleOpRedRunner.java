package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "driveTeleOpRed", group = "test")
public class TeleOpRedRunner extends OpMode {
    TeleOpMethods teleOpMethods;
    @Override
    public void init() {
        teleOpMethods = new TeleOpMethods(this, false);
    }

    @Override
    public void loop() {
        teleOpMethods.teleOpControls(gamepad1, gamepad2);
    }
}
