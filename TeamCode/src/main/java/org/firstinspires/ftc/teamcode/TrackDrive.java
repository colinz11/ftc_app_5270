package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Track Drive", group = "Iterative Opmode")
public class TrackDrive extends OpMode {
    final double K = 1f;
    double drivePower = 1;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    @Override
    public void init() {
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {

        Drive();
    }
    private void Drive()
    {
        double forward = -gamepad1.left_stick_y; // push joystick1 forward to go forward
        double right = gamepad1.left_stick_x; // push joystick1 to the right to strafe right
        double clockwise = gamepad1.right_stick_x; // push joystick2 to the right to rotate clockwise

//meese update
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
