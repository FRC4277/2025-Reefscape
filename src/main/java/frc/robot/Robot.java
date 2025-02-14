// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.systems.Drivetrain;
import com.ctre.phoenix6.hardware.Pigeon2;
import frc.robot.systems.Ballgrabber;
/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  private TalonFX frontLeft;
  private TalonFX frontRight;
  private TalonFX backLeft;
  private TalonFX backRight;
  private Joystick stick;
  private Drivetrain drivetrain;
  private Ballgrabber ballGrabber;
  private Pigeon2 pigeon;
  public Robot() {
  frontLeft = new TalonFX(4);
  frontRight = new TalonFX(3);
  backLeft = new TalonFX(1);
  backRight = new TalonFX(2);
  stick = new Joystick(1);
  drivetrain = new Drivetrain(frontLeft, frontRight, backLeft, backRight, 0.1 );
  pigeon =  new Pigeon2(0);
  pigeon.reset();
  ballGrabber = new Ballgrabber(5);
}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    drivetrain.fieldOrientedDrive(stick.getX(), -stick.getY(), stick.getZ(),-pigeon.getRotation2d().getRadians());
    //drivetrain.arcadeDrive(stick.getX(), -stick.getY(), stick.getZ());
    if (stick.getTriggerPressed() == true){
        pigeon.reset();
    }
     System.out.println(pigeon.getRotation2d());

     if (stick.getRawButtonPressed(5)){
      ballGrabber.startGrabber(stick.getThrottle());
     }
     if (stick.getRawButtonPressed(3)){
      ballGrabber.stopGrabber();
     }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

 
}
