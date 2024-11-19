package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepNicholas {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 70, Math.toRadians(270)))
                .lineToY(35)
                .waitSeconds(2)
                .turn(Math.toRadians(-90))
                .lineToX(-38)
                .turn(Math.toRadians(90))
                .lineToY(15)
                .turn(Math.toRadians(-90))
                .lineToX(-48)
                .turn(Math.toRadians(-90))
                .waitSeconds(2)
                .lineToY(60)
                .turn(Math.toRadians(90))
                .lineToX(-60)


                .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
