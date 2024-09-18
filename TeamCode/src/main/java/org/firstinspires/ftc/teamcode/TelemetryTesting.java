package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TelemetryTesting", group = "test")
public class TelemetryTesting extends OpMode {
    //this code is for telemtry testing
    DcMotor fr, bl;
    @Override
    public void init() {
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
    }

    @Override
    public void loop() {
        telemetry.addData("horizontal", fr.getCurrentPosition());
        telemetry.addData("vertical", bl.getCurrentPosition());
    }
}
