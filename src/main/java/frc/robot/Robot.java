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
import edu.wpi.first.wpilibj.XboxController;

import com.studica.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import  edu.wpi.first.wpilibj.Relay;

import frc.robot.systems.Drivetrain;
import frc.robot.systems.Ballgrabber;
import frc.robot.systems.Corallauncher;
import frc.robot.systems.autonomousRoutines;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;


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
  Thread m_visionThread;
  private TalonFX frontLeft;
  private TalonFX frontRight;
  private TalonFX backLeft;
  private TalonFX backRight;
  private Joystick stick;
  private XboxController controller;
  private Drivetrain drivetrain;
  private Ballgrabber ballGrabber;
  private AHRS gyro;
  private Corallauncher coralLauncher;
  private boolean intakeSwitch;
  private boolean outtakeSwitch;
  private boolean armUpSwitch;
  private boolean armDownSwitch;
  private boolean coralIntake;
  private boolean coralLaunch;
  private boolean autoIntakeSwitch;
  private autonomousRoutines autonomous;
  private Relay relay;
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
  public SparkFlex launcher;
  public DigitalInput stopperTop;
  public DigitalInput stopperBottom;
  public DigitalInput stopGrabber;
  public DigitalInput stopGrab;
 
  public Robot() {
    
    frontLeft = new TalonFX(4);
    frontRight = new TalonFX(3);
    backLeft = new TalonFX(1);
    backRight = new TalonFX(2);
    stick = new Joystick(1);
    controller = new XboxController(2);
    launcher = new SparkFlex(7,MotorType.kBrushless);
    gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);
    drivetrain = new Drivetrain(frontLeft, frontRight, backLeft, backRight,gyro, 0.1 );
    relay = new Relay(0); 
    motor = new SparkFlex(5, MotorType.kBrushless);
    angleMotor = new SparkMax(6, MotorType.kBrushless);
    

  
    stopperTop = new DigitalInput(0);
    stopperBottom = new DigitalInput(1);
    stopGrabber = new DigitalInput(3);
    stopGrab = new DigitalInput(2);
    ballGrabber = new Ballgrabber(motor, angleMotor, stopperTop, stopperBottom,stopGrab,relay);
    coralLauncher = new Corallauncher(launcher,stopGrabber);
    timer = new Timer();

    autonomous = new autonomousRoutines(drivetrain,ballGrabber,coralLauncher,timer);
    coralIntake = false;
    coralLaunch = false;
    intakeSwitch = false;
    outtakeSwitch = false;
    armUpSwitch = false;
    armDownSwitch = false;
    autoIntakeSwitch = false;
    
    autoChooser.setDefaultOption("Default", noneSelected);
    autoChooser.addOption("AutoLeft", AutoLeft);
    autoChooser.addOption("AutoCenter", AutoCenter);
    autoChooser.addOption("AutoRight", AutoRight);
    SmartDashboard.putData("Auto choices", autoChooser);


  
}
@Override
  public void robotInit() {
    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();
  }
  
@Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    timer.start();
    drivetrain.resetGyro();
    m_autoSelected = autoChooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
    drivetrain.motorBrakeMode();
  }

  @Override
  public void autonomousPeriodic() {
      /** This function is called periodically during autonomous. */
  
    switch (m_autoSelected) {
      case AutoLeft:
        // Put custom auto code here
        autonomous.positionLeft();
        break;
        case AutoCenter:
        // Put custom auto code here
       autonomous.positionMiddle();
        break;
        case AutoRight:
        // Put custom auto code here
       autonomous.positionRight();
        break;
        
      case noneSelected:
      default:
        // Put default auto code here
        
        break;
    }
  

    

    
}

  @Override
  public void teleopInit() {
    drivetrain.motorCoastMode();
  }
  
  @Override
  public void teleopPeriodic() {
    drivetrain.fieldOrientedDrive(stick.getX(), -stick.getY(), 0.5*stick.getZ());
    //drivetrain.arcadeDrive(stick.getX(), -stick.getY(), 0.5*stick.getZ());
    
    
    if (stick.getTriggerPressed() == true){
      drivetrain.resetGyro();
    }

     if (controller.getPOV() == 270){
        intakeSwitch = true;
     }
     if (controller.getPOV() == 45){
      outtakeSwitch = true;
     }
     else if (controller.getLeftStickButtonPressed() == true){
      ballGrabber.stopGrabber();
      intakeSwitch = false;
      outtakeSwitch = false;
      coralIntake = false;
      coralLaunch = false;
      coralLauncher.stopLauncher();
     }
     if (controller.getAButtonPressed() == true){
      autoIntakeSwitch = true;
      /*intakeSwitch = false;
      outtakeSwitch = false;
      coralIntake = false;
      coralLaunch = false;*/
     }
     else if (controller.getYButtonPressed() == true){
      intakeSwitch = false;
      outtakeSwitch = false;
      armDownSwitch = false;
      autoIntakeSwitch = false;
      armUpSwitch = true;
     }
     else if (controller.getXButtonPressed() == true){
      intakeSwitch = false;
      autoIntakeSwitch = false;
      outtakeSwitch = true;
      timer.delay(2);
      outtakeSwitch = false;
     }
     else if (controller.getPOV() == 0){
      armUpSwitch = true;
      armDownSwitch = false;
    }
      
    else if (controller.getPOV() == 180){
      armDownSwitch = true;
      armUpSwitch = false;
    }
    else if (controller.getPOV() == 270){
      outtakeSwitch = false;
      intakeSwitch = true;
    }
    else if (controller.getLeftBumperButtonPressed() == true){
      coralLauncher.intakeCoral(0.3);
    }
    
    else if (controller.getRightBumperButtonPressed() == true){
      coralLauncher.launchCoral(0.3);
    }


     /*if (stick.getRawButton(2)) {
     double fixedSpeed = speedFix(stick.getRawAxis(3));

      ballGrabber.startGrabber(-fixedSpeed);
    }*/

     if (intakeSwitch){
      ballGrabber.startGrabber(0.3);
     }
     if (outtakeSwitch){
      ballGrabber.startGrabber(-0.3);
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
    if (coralIntake){
      coralLaunch = false;
      coralLauncher.intakeCoral(0.3);
    }
    if (coralLaunch){
      coralIntake = false;
      coralLauncher.launchCoral(0.3);
    }
    if (autoIntakeSwitch == true){
      System.out.println("here");
      if(ballGrabber.autoGrab(0.3)){
        autoIntakeSwitch = false;
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