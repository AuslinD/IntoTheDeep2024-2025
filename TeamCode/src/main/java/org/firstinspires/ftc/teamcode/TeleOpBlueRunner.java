package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "driveTeleOpBlue", group = "test")
public class TeleOpBlueRunner extends OpMode {
    TeleOpMethods teleOpMethods;
    @Override
    public void init() {
        teleOpMethods = new TeleOpMethods(this, true);
    }

    @Override
    public void loop() {
        teleOpMethods.teleOpControls(gamepad1, gamepad2);
    }
}
