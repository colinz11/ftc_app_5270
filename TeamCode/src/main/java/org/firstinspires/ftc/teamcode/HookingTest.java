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
        int targetPos = 24000;
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
            telemetry.addData("Block Status:", "Lowering: " + lift.getCurrentPosition());
            telemetry.update();
        }
    }
}