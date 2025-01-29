package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Macro {

    static OpMode opMode;
    private static DiffyClaw diffy;
    private static Intake intake;
    public Macro(OpMode opmode){

        Macro.diffy = new DiffyClaw(opMode);
        Macro.intake = new Intake(opMode);
    }

    public static void macro_run(OpMode opmode){

    }
}
