package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="autoFinal", group="Mecanum")
public class autoFinal extends LinearOpMode {

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
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
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

        //Move the bot forward
        runtime.reset();
        backRight.setPower(1);
        backLeft.setPower(1);
        frontRight.setPower(1);
        frontLeft.setPower(1);
        while (opModeIsActive() && (runtime.seconds() < .25)) {
            telemetry.addData("Path:", "Moving Forward", runtime.seconds());
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
            if (detector.isFound() == false) {
                runtime.reset();
                backRight.setPower(1);
                backLeft.setPower(-1);
                frontRight.setPower(1);
                frontLeft.setPower(-1);
                while (opModeIsActive() && (runtime.seconds() < .5) && detector.isFound() == false) {
                    telemetry.addData("Block Status:", "Locating Gold", runtime.seconds());
                    telemetry.update();
                }
                runtime.reset();
                backRight.setPower(-1);
                backLeft.setPower(1);
                frontRight.setPower(-1);
                frontLeft.setPower(1);
                while (opModeIsActive() && (runtime.seconds() < 1) && detector.isFound() == false) {
                    telemetry.addData("Block Status:", "Locating Gold", runtime.seconds());
                    telemetry.update();
                }
            }
            telemetry.addData("Block Status:", "Located", runtime.seconds());

            runtime.reset();
            backRight.setPower(1);
            backLeft.setPower(1);
            frontRight.setPower(1);
            frontLeft.setPower(1);
            while (opModeIsActive() && (runtime.seconds() < .5)) {
                telemetry.addData("Block Status:", "Moving Forward", runtime.seconds());
                telemetry.update();
            }


            //code for aligning the robot
            if (detector.getAligned() == false) {
                runtime.reset();
                backRight.setPower(1);
                backLeft.setPower(-1);
                frontRight.setPower(1);
                frontLeft.setPower(-1);
                while (opModeIsActive() && (runtime.seconds() < .5) && detector.getAligned() == false) {
                    telemetry.addData("Block Status:", "Aligning Gold", runtime.seconds());
                    telemetry.update();
                }
                runtime.reset();
                backRight.setPower(-1);
                backLeft.setPower(1);
                frontRight.setPower(-1);
                frontLeft.setPower(1);
                while (opModeIsActive() && (runtime.seconds() < .5) && detector.getAligned() == false) {
                    telemetry.addData("Block Status:", "Aligning Gold", runtime.seconds());
                    telemetry.update();
                }
            }
            //code to knock block
            runtime.reset();
            backRight.setPower(1);
            backLeft.setPower(1);
            frontRight.setPower(1);
            frontLeft.setPower(1);
            while (opModeIsActive() && (runtime.seconds() < .5)) {
                telemetry.addData("Block Status:", "Knocking gold", runtime.seconds());
                telemetry.update();
            }
            // Disable the detector
            detector.disable();

            //Drive into the crater

            break;
        }

    }
}

