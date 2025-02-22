// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.systems.Drivetrain;
import com.studica.frc.AHRS;
import frc.robot.systems.Ballgrabber;
import edu.wpi.first.wpilibj.Timer;
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
  private AHRS gyro;
  private boolean intakeSwitch;
  private final Compressor compressor;
  private static final String kDefaultAuto = "Auto1";
  private static final String kCustomAuto = "Auto2";
  private String autoSelected;
  private final SendableChooser<String> autoChooser = new SendableChooser<>();

  public Robot() {
    frontLeft = new TalonFX(4);
    frontRight = new TalonFX(3);
    backLeft = new TalonFX(1);
    backRight = new TalonFX(2);
    stick = new Joystick(1);
    ballGrabber = new Ballgrabber(5);//,6,7
    intakeSwitch = false;
    compressor = new Compressor(PneumaticsModuleType.REVPH);
    compressor.enableDigital();
    autoChooser.setDefaultOption("Auto1", kDefaultAuto);
    autoChooser.addOption("Auto2", kCustomAuto);
    SmartDashboard.putData("Auto choices", autoChooser);


  private Timer timer;
  private boolean autoEnd;
  gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);
  drivetrain = new Drivetrain(frontLeft, frontRight, backLeft, backRight,gyro,0.1 );
  timer = new Timer();
}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    
    


  }

  @Override
  public void autonomousPeriodic() {

    autoSelected = autoChooser.getSelected();
    System.out.println("Auto selected: " + autoSelected);
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    drivetrain.fieldOrientedDrive(stick.getX(), -stick.getY(), 0.5*stick.getZ());
    //drivetrain.arcadeDrive(stick.getX(), -stick.getY(), 0.5*stick.getZ());
    if (stick.getTriggerPressed() == true){
      drivetrain.resetGyro();
    }

     if (stick.getRawButtonPressed(5)){
        intakeSwitch = true;
     }
     if (stick.getRawButtonPressed(3)){
      ballGrabber.stopGrabber();
      intakeSwitch = false;
     }


     if (stick.getRawButton(2)){
     double fixedSpeed = speedFix(stick.getRawAxis(3));
      ballGrabber.startGrabber(-fixedSpeed);
     } 
     if (intakeSwitch){
      ballGrabber.startGrabber(speedFix(stick.getRawAxis(3)));
     }

   /*   
   if (stick.getRawButton(4)){

      ballGrabber.pneumaticsExtend();
     }
    
    if (stick.getRawButton(6)){

     ballGrabber.pneumaticsRetract();
    }
 */ }

  public double speedFix(double oldSpeed)  {
    double speedNew = (oldSpeed + 1) / 2;
    return speedNew;
  }

  @Override
  public void disabledInit() {}
//mogus
  @Override
  public void disabledPeriodic() {}

 
}
