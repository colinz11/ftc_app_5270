package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;



@TeleOp(name="mecanumWheelsTest", group="Iterative Opmode")
    public class mecanumWheelsTest extends OpMode {


        // Declare OpMode members.
        private ElapsedTime runtime = new ElapsedTime();
        private DcMotor leftFrontMotor = null;
        private DcMotor rightFrontMotor = null;
        private DcMotor leftBackMotor = null;
        private DcMotor rightBackMotor = null;
        private CRServo intakeServo = null;
        final double K = 0.5f;
        final double drivePower = 0.75;
        /*
         * Code to run ONCE when the driver hits INIT
         */
        @Override
        public void init() {
            telemetry.addData("Status", "Initialized");

            // Initialize the hardware variables. Note that the strings used here as parameters
            // to 'get' must correspond to the names assigned during the robot configuration
            // step (using the FTC Robot Controller app on the phone).
            leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
            rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
            leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
            rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
            intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
            // Most robots need the motor on one side to be reversed to drive forward
            // Reverse the motor that runs backwards when connected directly to the battery

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
            {
               intakeServo.setPower(1);
            }
            else
            {
                intakeServo.setPower(0);
            }
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
