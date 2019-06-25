package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Basic Drive", group = "Iterative Opmode")
public class BasicDrive extends OpMode {
    private DcMotor rightDrive = null;
    private DcMotor leftDrive = null;
    private DcMotor arm = null;
    private Servo leftServo = null;
    private Servo rightServo = null;
    double forwardPower;
    double turnPower;

    @Override
    public void init() {
        leftDrive = hardwareMap.get(DcMotor.class, "rigtDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        arm = hardwareMap.get(DcMotor.class, "arm");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");


        rightDrive.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {

        forwardPower = gamepad1.left_stick_y; //Get Forward power based on y value of left joystick
        turnPower = gamepad1.right_stick_x; //Get Turn power based on x value of left joystick

        if (turnPower > .1 || turnPower < -.1) { //Check if the left stick is far enough to the right or left
            leftDrive.setPower(-turnPower); //Turn based on turn power
            rightDrive.setPower(turnPower);
        } else { //if not turning, drive
            leftDrive.setPower(forwardPower);
            rightDrive.setPower(forwardPower);
        }

        arm.setPower(gamepad1.right_stick_y); // move the arm based on the y position of the right joystick

        if (gamepad1.a) { //if the a button is pressed, clamp the servos, otherwise, go back to a rest position
            leftServo.setPosition(0);
            rightServo.setPosition(180);
        } else {
            leftServo.setPosition(160);
            rightServo.setPosition(20);
        }
    }
}
