package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="hooking test", group="Mecanum")
public class HookingTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor lift = null;
    private DcMotor armExtension = null;
    private DcMotor intake = null;
    private DcMotor intakeArm = null;

    @Override
    public void runOpMode() {
        int targetPos = 34000;
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
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setTargetPosition(targetPos);
        lift.setPower(1);
        runtime.reset();
        lift.getTargetPosition();
        while (opModeIsActive() && lift.getCurrentPosition() < targetPos) {
            telemetry.addData("Auto Stat:", "Lowering: " + lift.getCurrentPosition());
            telemetry.update();
        }
        backRight.setPower(-1);
        backLeft.setPower(-1);
        frontRight.setPower(-1);
        frontLeft.setPower(-1);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .3)) {
            telemetry.addData("Path:", "Moving Backwards", runtime.seconds());
            telemetry.update();
        }
        backRight.setPower(1);
        backLeft.setPower(-.5);
        frontRight.setPower(1);
        frontLeft.setPower(-.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1)) {
            telemetry.addData("Path:", "Turning", runtime.seconds());
            telemetry.update();
        }
        backRight.setPower(1);
        backLeft.setPower(1);
        frontRight.setPower(1);
        frontLeft.setPower(1);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .2)) {
            telemetry.addData("Path:", "Moving Forwards", runtime.seconds());
            telemetry.update();
        }
        backRight.setPower(1);
        backLeft.setPower(-1);
        frontRight.setPower(1);
        frontLeft.setPower(-1);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .5)) {
            telemetry.addData("Path:", "Moving Turning", runtime.seconds());
            telemetry.update();
        }
        backRight.setPower(1);
        backLeft.setPower(-1);
        frontRight.setPower(-1);
        frontLeft.setPower(1);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .4)) {
            telemetry.addData("Path:", "Moving Sideways", runtime.seconds());
            telemetry.update();
        }
        backRight.setPower(0);
        backLeft.setPower(-1);
        frontRight.setPower(0);
        frontLeft.setPower(-1);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .2)) {
            telemetry.addData("Path:", "Moving Turning", runtime.seconds());
            telemetry.update();
        }
    }
}