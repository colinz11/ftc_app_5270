package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;


@TeleOp(name="mecanumWheels", group="Iterative Opmode")
public class mecanumWheelsTest extends OpMode {
    //Variables for Mecanum Wheel Drive
    final double K = 1f;
    final double drivePower = 1;


    //call DcMotors into play
        private DcMotor frontLeft = null;
        private DcMotor frontRight = null;
        private DcMotor backLeft = null;
        private DcMotor backRight = null;
        private DcMotor lift = null;
        private DcMotor armExtension = null;
        private DcMotor intake = null;
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
            intake = hardwareMap.get(DcMotor.class, "intake");
            intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
            intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            //Set on side of the robot's motors to reverse
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            //Display Initilized
            telemetry.addData("Status", "Initialized");

        }


        public void loop() {
            //run the intake forwards / backwards based on button pressed
            if(gamepad2.a)
                intake.setPower(.75);
            else if(gamepad2.b)
                intake.setPower(-.75);
            else
                intake.setPower(0);
            //run the lift
            if (gamepad1.left_trigger > .1)
                lift.setPower(1);
            else if(gamepad1.right_trigger > .1)
                lift.setPower(-1);
            else
                lift.setPower(0);
            //extend the arm (currently cant de-extend)
            if(gamepad2.right_trigger > .1)
                armExtension.setPower(.5);
            else if(gamepad2.left_trigger > .1)
                 armExtension.setPower(-.5);
            else
                armExtension.setPower(0);


            //move the intake arm
            if(gamepad2.right_stick_y > .1) {
                intakeArm.setPower(.2); //Set motor power to .2
                intakeArm.setTargetPosition(intakeArm.getCurrentPosition() - 10);//set the target position to 10 less
            }
            else if(gamepad2.right_stick_y < -.1) {
                intakeArm.setPower(.2);
                intakeArm.setTargetPosition(intakeArm.getCurrentPosition() + 10);//set the target position to 10 more
            }
         //   else //if no input, set target to current position (PROBABLY DOESN'T WORK AS POS IS CONSTANTLY UPDATE SO IT MAY DO NOTHING)
         //       intakeArm.setTargetPosition(intakeArm.getCurrentPosition());


            Drive();

        }
        //Mecanum wheel drive
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

