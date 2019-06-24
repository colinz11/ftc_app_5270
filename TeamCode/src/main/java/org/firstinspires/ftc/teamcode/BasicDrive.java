package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
public class BasicDrive extends OpMode{
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    double leftPower;
    double rightPower;
@Override
public void init()
{
    frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
    frontRight = hardwareMap.get(DcMotor.class, "frontRight");
    backLeft = hardwareMap.get(DcMotor.class, "backLeft");
    backRight  = hardwareMap.get(DcMotor.class, "backRight");
    frontRight.setDirection(DcMotor.Direction.REVERSE);
    backRight.setDirection(DcMotor.Direction.REVERSE);
}
 @Override
 public void loop()
 {
     leftPower = gamepad1.left_stick_y;
     rightPower = gamepad1.right_stick_y;
     frontLeft.setPower(leftPower);
     backLeft.setPower(leftPower);
     frontRight.setPower(rightPower);
     backRight.setPower(rightPower);
 }
}
