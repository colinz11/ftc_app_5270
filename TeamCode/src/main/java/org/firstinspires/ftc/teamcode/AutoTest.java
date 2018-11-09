package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="mecanum time drive", group="Mecanum")
    public class AutoTest extends LinearOpMode
{

        private ElapsedTime runtime = new ElapsedTime();
        private DcMotor frontLeft = null;
        private DcMotor frontRight = null;
        private DcMotor backLeft = null;
        private DcMotor backRight = null;
        private DcMotor lift = null;
        private DcMotor liftArm = null;
        private DcMotor intake = null;
        private DcMotor intakeArm = null;
        private Servo linearServo = null;

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
            liftArm = hardwareMap.get(DcMotor.class, "liftArm");
            intake = hardwareMap.get(DcMotor.class, "intake");
            intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
            linearServo = hardwareMap.get(Servo.class, "linearServo");
            // Most robots need the motor on one side to be reversed to drive forward
            // Reverse the motor that runs backwards when connected directly to the battery

            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);

            // Tell the driver that initialization is complete.
            telemetry.addData("Status", "Initialized");
                waitForStart();
            lift.setPower(-1);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 1.5))
            {
            telemetry.addData("Path", "Lowering", runtime.seconds());
            telemetry.update();
            }
            runtime.reset();
            lift.setPower(0);
            backRight.setPower(1);
            frontLeft.setPower(1);
            frontRight.setPower(1);
            backLeft.setPower(1);
            while (opModeIsActive() && (runtime.seconds() < 1))
            {
                telemetry.addData("Path", "Lowering", runtime.seconds());
                telemetry.update();
            }
            backRight.setPower(0);
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
        }
}


