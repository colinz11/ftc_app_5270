package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="autonomous", group="Mecanum")
    public class AutoTest extends LinearOpMode
{

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor lift = null;
    private DcMotor armExtension = null;
    private DcMotor intake = null;
    private DcMotor intakeArm = null;

        //Declares detector
        private GoldAlignDetector detector;

        @Override
        public void runOpMode()
        {
            telemetry.addData("Status", "Initialized");
            telemetry.update();

            frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            backRight  = hardwareMap.get(DcMotor.class, "backRight");
            lift = hardwareMap.get(DcMotor.class, "lift");
            armExtension = hardwareMap.get(DcMotor.class, "armExtension");
            intake = hardwareMap.get(DcMotor.class, "intake");
            intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
            // Most robots need the motor on one side to be reversed to drive forward
            // Reverse the motor that runs backwards when connected directly to the battery

            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);

            // Tell the driver that initialization is complete.
            telemetry.addData("Status", "Initialized");
                waitForStart();
            lift.setPower(-1);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 6.6))
            {
            telemetry.addData("Path", "Lowering", runtime.seconds());
            telemetry.update();
            }
            lift.setPower(0);

            runtime.reset();
            backRight.setPower(-.5);
            frontLeft.setPower(.5);
            frontRight.setPower(.5);
            backLeft.setPower(-.5);
            while (opModeIsActive() && (runtime.seconds() < 1))
            {
                telemetry.addData("Path", "Lowering", runtime.seconds());
                telemetry.update();
            }
            runtime.reset();

            backRight.setPower(.5);
            frontLeft.setPower(.5);
            frontRight.setPower(.5);
            backLeft.setPower(.5);
            while (opModeIsActive() && (runtime.seconds() < 1.5))
            {
                telemetry.addData("Path", "Lowering", runtime.seconds());
                telemetry.update();
            }

            backRight.setPower(0);
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);

            /*
             * DodgeCV portion
             * Scanners: Gold Align Detector
             */

            telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");

            // Set up detector
            detector = new GoldAlignDetector(); // Create detector
            detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
            detector.useDefaults(); // Set detector to use default settings

            // Optional tuning
            detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
            detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
            detector.downscale = 0.4; // How much to downscale the input frames

            detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
            //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
            detector.maxAreaScorer.weight = 0.005; //

            detector.ratioScorer.weight = 5; //
            detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

            detector.enable(); // Start the detector!

            /*
             * Check if the Robot is aligned
             * And Position
             */
            while (opModeIsActive() && (runtime.seconds() < 27)) {
                while(detector.getAligned() == false) {
                    detector.getXPosition();
                    while (detector.isFound() == false) {
                       //code for moving the robot to find gold blocks
                        runtime.reset();
                        backRight.setPower(1);
                        while (opModeIsActive() && (runtime.seconds() < 1.5))
                        {
                            telemetry.addData("Path", "Lowering", runtime.seconds());
                            telemetry.update();
                        }


                    }
                    while (detector.getXPosition() > 300) {
                        //code for moving the robot forward to meet gold block
                    }
                }
                //General code for knocking block (Lower Arm and turn robot to knock the block


                telemetry.addData("IsAligned", detector.getAligned()); // Is the bot aligned with the gold mineral?
                telemetry.addData("X Pos", detector.getXPosition()); // Gold X position.

                // Disable the detector
                detector.disable();
            }
        }
}


