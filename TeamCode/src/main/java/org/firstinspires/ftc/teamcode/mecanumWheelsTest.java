package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

    @TeleOp(name="mecanumWheelsTest", group="Iterative Opmode")
    public class mecanumWheelsTest extends OpMode
    {
        // Declare OpMode members.
        private ElapsedTime runtime = new ElapsedTime();
        private DcMotor frontLeft = null;
        private DcMotor frontRight = null;
        private DcMotor backLeft = null;
        private DcMotor backRight = null;
        private DcMotor lift = null;
        private DcMotor liftArm = null;
        private DcMotor intake = null;
        private DcMotor intakeArm = null;

       final float K = 0.5f;
       final double drivePower = 1;
        /*
         * Code to run ONCE when the driver hits INIT
         */
        @Override
        public void init() {
            telemetry.addData("Status", "Initialized");

            // Initialize the hardware variables. Note that the strings used here as parameters
            // to 'get' must correspond to the names assigned during the robot configuration
            // step (using the FTC Robot Controller app on the phone).
            frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            backRight  = hardwareMap.get(DcMotor.class, "backRight");
            lift = hardwareMap.get(DcMotor.class, "lift");
            liftArm = hardwareMap.get(DcMotor.class, "liftArm");
            intake = hardwareMap.get(DcMotor.class, "intake");
            intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");

            // Most robots need the motor on one side to be reversed to drive forward
            // Reverse the motor that runs backwards when connected directly to the battery

            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);

            // Tell the driver that initialization is complete.
            telemetry.addData("Status", "Initialized");
        }

        @Override
        public void start() {
            runtime.reset();
        }

        @Override
        public void loop() {

            if(gamepad2.a)
                intake.setPower(.75);
            else
                intake.setPower(0);

            if (gamepad2.left_stick_y > .1)
                lift.setPower(1);
            else if(gamepad2.left_stick_y < -.1)
                lift.setPower(-1);
            else
                lift.setPower(0);

            if(gamepad2.right_trigger > .1)
                liftArm.setPower(1);
            else if(gamepad2.left_trigger > .1)
                 liftArm.setPower(-1);
            else
                liftArm.setPower(0);


            if(gamepad2.right_stick_y > .1)
                intakeArm.setPower(1);
            else if(gamepad2.right_stick_y < -.1)
                intakeArm.setPower(-.5);
            else
                intakeArm.setPower(0);




            Drive();

        }
       private void Drive()
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

            frontLeft.setPower(front_left * drivePower);
            frontRight.setPower(front_right * drivePower);
            backRight.setPower( rear_right* drivePower);
            backLeft.setPower(rear_left * drivePower);
        }
}
