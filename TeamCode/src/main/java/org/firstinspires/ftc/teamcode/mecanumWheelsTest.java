package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;




@TeleOp(name="mecanumWheelsTest", group="Iterative Opmode")
    public class mecanumWheelsTest extends OpMode {


        // Declare OpMode members.
        private ElapsedTime runtime = new ElapsedTime();
        private DcMotor leftFrontMotor = null;
        private DcMotor rightFrontMotor = null;
        private DcMotor leftBackMotor = null;
        private DcMotor rightBackMotor = null;
        private DcMotor intakeMotor = null;
        private DcMotor armMotor = null;
        private DcMotor liftArmMotor = null;
        private DcMotor liftMotor = null;
        private Servo linearServo = null;
        final double K = 0.5f;
        final double drivePower = 0.75;


        @Override
        public void init() {
            telemetry.addData("Status", "Initialized");

            leftFrontMotor = hardwareMap.get(DcMotor.class, "frontLeft");
            rightFrontMotor = hardwareMap.get(DcMotor.class, "frontRight");
            leftBackMotor = hardwareMap.get(DcMotor.class, "backLeft");
            rightBackMotor = hardwareMap.get(DcMotor.class, "backRight");
            intakeMotor = hardwareMap.get(DcMotor.class, "intake");
            armMotor = hardwareMap.get(DcMotor.class, "intakeArm");
            liftMotor = hardwareMap.get(DcMotor.class, "lift");
            liftArmMotor = hardwareMap.get(DcMotor.class, "liftArm");
            linearServo = hardwareMap.get(Servo.class, "linearServo");

            leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
            leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

            // Tell the driver that initialization is complete.
            telemetry.addData("Status", "Initialized");
        }

        @Override
        public void start() {
            runtime.reset();
        }

        @Override
        public void loop()
        {


            if(gamepad2.a)
                intakeMotor.setPower(.75);

            else
                intakeMotor.setPower(0);


                armMotor.setPower(gamepad2.left_stick_y / 2);

            liftArmMotor.setPower(gamepad2.right_stick_y / 2);

            if(gamepad2.dpad_up)
                liftMotor.setPower(.75);
            if(gamepad2.dpad_down)
                liftMotor.setPower(-.75);
            if(gamepad2.right_trigger > 0.1)
            linearServo.setPosition(linearServo.getPosition() + gamepad2.right_trigger / 10);
            else if(gamepad2.left_trigger > 0.1)
                linearServo.setPosition(linearServo.getPosition() - gamepad2.left_trigger / 10);
            else
                linearServo.setPosition(linearServo.getPosition());



            Drive();


    }

        public void Drive()
        {
            double forward = -gamepad1.left_stick_y; // push joystick1 forward to go forward
            double right = gamepad1.left_stick_x; // push joystick1 to the right to strafe right
            double clockwise = gamepad1.right_stick_x; // push joystick2 to the right to rotate clockwise


            double front_left = forward  + right + K*clockwise;
            double front_right = forward - right - K*clockwise;
            double rear_left = forward - right + K*clockwise;
            double rear_right = forward + right - K*clockwise;

            double max = Math.abs(front_left);
            if (Math.abs(front_right)>max) max = Math.abs(front_right);
            if (Math.abs(rear_left)>max) max = Math.abs(rear_left);
            if (Math.abs(rear_right)>max) max = Math.abs(rear_right);
            if (max>1)
            {front_left/=max; front_right/=max; rear_left/=max; rear_right/=max;}

            leftFrontMotor.setPower(front_left * drivePower);
            rightFrontMotor.setPower(front_right * drivePower);
            rightBackMotor.setPower( rear_right* drivePower);
            leftBackMotor.setPower(rear_left * drivePower);
        }
}
