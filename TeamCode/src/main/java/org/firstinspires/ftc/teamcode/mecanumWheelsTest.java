package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;


@TeleOp(name="mecanumWheelsTest", group="Iterative Opmode")
public class mecanumWheelsTest extends OpMode {

    final double K = 1f;
    final double drivePower = 1;

    private final double drivePidKp = 1;     // Tuning variable for PID.
    private final double drivePidTi = 1.0;   // Eliminate integral error in 1 sec.
    private final double drivePidTd = 0.1;   // Account for error in 0.1 sec.
    // Protect against integral windup by limiting integral term.
    private final double drivePidIntMax = 1;  // Limit to max speed.
    private final double driveOutMax = 1.0;  // Motor output limited to 100%.
    private final double ticksPerRevolution = 1120;  // Get for your motor and gearing.
    private double prevTime;  // The last time loop() was called.
    private int prevArmEncoderPosition;   // Encoder tick at last call to loop().



// Declare OpMode members.
        private ElapsedTime runtime = new ElapsedTime();
        private DcMotor frontLeft = null;
        private DcMotor frontRight = null;
        private DcMotor backLeft = null;
        private DcMotor backRight = null;
        private DcMotor lift = null;
        private DcMotor armExtension = null;
       // private DcMotor intake = null;
        private CRServo intake = null;
        private DcMotor intakeArm = null;


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
            armExtension = hardwareMap.get(DcMotor.class, "armExtension");
           // intake = hardwareMap.get(DcMotor.class, "intake");
            intake = hardwareMap.get(CRServo.class, "intakes");
            intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
            intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // Most robots need the motor on one side to be reversed to drive forward
            // Reverse the motor that runs backwards when connected directly to the battery


            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            prevTime = 0;
            prevArmEncoderPosition = intakeArm.getCurrentPosition();
            // Tell the driver that initialization is complete.
            telemetry.addData("Status", "Initialized");
        }

        @Override
        public void start() {
            runtime.reset();
        }


        public void loop() {
            double deltaTime = time - prevTime;

            double armSpeed = (intakeArm.getCurrentPosition() - prevArmEncoderPosition) /
                    deltaTime;
            // Track last loop() values.
            prevTime = time;
            prevArmEncoderPosition = intakeArm.getCurrentPosition();
            if(gamepad2.a)
                intake.setPower(.75);
            else if(gamepad2.b)
                intake.setPower(-.75);
            else
                intake.setPower(0);

            if (gamepad1.left_trigger > .1)
                lift.setPower(1);
            else if(gamepad1.right_trigger > .1)
                lift.setPower(-1);
            else
                lift.setPower(0);

            if(gamepad2.right_trigger > .1)
                armExtension.setPower(.4);
            else if(gamepad2.left_trigger > .1)
                 armExtension.setPower(-.4);
            else
                armExtension.setPower(0);


            if(gamepad2.right_stick_y > .1) {
                intakeArm.setPower(-.25);
                int target = 180;
                intakeArm.setTargetPosition(intakeArm.getCurrentPosition() + target);
            }
            else if(gamepad2.right_stick_y < -.1)
                intakeArm.setPower(.5);
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
