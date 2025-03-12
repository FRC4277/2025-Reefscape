// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.systems.Drivetrain;
import com.studica.frc.AHRS;
import frc.robot.systems.Ballgrabber;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

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
  private boolean outtakeSwitch;
  private boolean armUpSwitch;
  private boolean armDownSwitch;
  private static final String AutoLeft = "AutoLeft";
  private static final String AutoCenter = "AutoCenter";
  private static final String AutoRight = "AutoRight";
  private static final String noneSelected = "Default";
  private String m_autoSelected;
  private final SendableChooser<String> autoChooser = new SendableChooser<>();  

  private Timer timer;
  private boolean autoEnd;
  public SparkMax angleMotor;
  public SparkFlex motor;
  public DigitalInput stopperTop;
  public DigitalInput stopperBottom;
 
  public Robot() {
    frontLeft = new TalonFX(4);
    frontRight = new TalonFX(3);
    backLeft = new TalonFX(1);
    backRight = new TalonFX(2);
    stick = new Joystick(1);
    gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);
    drivetrain = new Drivetrain(frontLeft, frontRight, backLeft, backRight,gyro, 0.1 );

    motor = new SparkFlex(5, MotorType.kBrushless);
    angleMotor = new SparkMax(6, MotorType.kBrushless);
   
    stopperTop = new DigitalInput(0);
    stopperBottom = new DigitalInput(1);
    ballGrabber = new Ballgrabber(motor, angleMotor, stopperTop, stopperBottom);
  
    intakeSwitch = false;
    outtakeSwitch = false;
    armUpSwitch = false;
    armDownSwitch = false;

    timer = new Timer();
    autoChooser.setDefaultOption("Default", noneSelected);
    autoChooser.addOption("AutoLeft", AutoLeft);
    autoChooser.addOption("AutoCenter", AutoCenter);
    autoChooser.addOption("AutoRight", AutoRight);
    SmartDashboard.putData("Auto choices", autoChooser);


  
}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    timer.start();
    drivetrain.resetGyro();
    m_autoSelected = autoChooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);

  }

  @Override
  public void autonomousPeriodic() {
      /** This function is called periodically during autonomous. */
  
    switch (m_autoSelected) {
      case AutoLeft:
        // Put custom auto code here
        drivetrain.timedDrive(15, timer, 0, 0.25, 0);
        break;
        case AutoCenter:
        // Put custom auto code here
        drivetrain.timedDrive(15, timer, 0, 0.25, 0);
        break;
        case AutoRight:
        // Put custom auto code here
        drivetrain.timedDrive(15, timer, 0, 0.25, 0);
        break;
        
      case noneSelected:
      default:
        // Put default auto code here
        drivetrain.timedDrive(15, timer, 0, 0.25, 0);
        break;
    }
  

    
    drivetrain.timedDrive(15, timer, 0, 0.25, 0);
    timer.delay(1);
    drivetrain.turnToRotation(45,10);
    drivetrain.timedDrive(15, timer, 0.25, 0, 0);
    
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
     if (stick.getRawButtonPressed(12)){
      outtakeSwitch = true;
     }
     else if (stick.getRawButtonPressed(3)){
      ballGrabber.stopGrabber();
      intakeSwitch = false;
      outtakeSwitch = false;
     }

     if (stick.getRawButtonPressed(4)){
      armUpSwitch = true;
      armDownSwitch = false;
    }
      
    
    if (stick.getRawButtonPressed(6)){
      armDownSwitch = true;
      armUpSwitch = false;
    }


     /*if (stick.getRawButton(2)) {
     double fixedSpeed = speedFix(stick.getRawAxis(3));

      ballGrabber.startGrabber(-fixedSpeed);
    }*/

     if (intakeSwitch){
      ballGrabber.startGrabber(speedFix(stick.getRawAxis(3)));
     }
     if (outtakeSwitch){
      ballGrabber.startGrabber(-speedFix(stick.getRawAxis(3)));
     }
     
     if (armUpSwitch){
      armDownSwitch = false;
      if(!ballGrabber.angleChangePos()){
        armUpSwitch = false;
      }
     }
     if (armDownSwitch){
      armUpSwitch = false;
      if(!ballGrabber.angleChangeNeg()){
        armDownSwitch = false;
      }
     }
    

  }
    

  public double speedFix(double oldSpeed)  {
    double speedNew = (oldSpeed + 1) / 2;
    return speedNew;
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

 
}