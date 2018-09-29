/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5705.robot;

import org.usfirst.frc.team5705.robot.subsystems.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * La VM está configurada para ejecutar automáticamente esta clase y para llamar
 * a las funciones correspondientes a cada modo, como se describe en la
 * documentación de TimedRobot. Si cambia el nombre de esta clase o el paquete
 * después de crear este proyecto, también debe actualizar el archivo
 * build.properties en el proyecto.
 */
public class Robot extends TimedRobot {
	public static double chassisSpeed;
	public static Drivetrain drivetrain;
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * Esta función se ejecuta cuando el robot se inicia por primera vez y debe ser
	 * utilizado para cualquier código de inicialización.
	 */
	public void robotInit() {
		chassisSpeed = 0.8;
		drivetrain = new Drivetrain();

		oi = new OI();

		chooser.addDefault("Default Auto", null);
		chooser.addObject("My Auto", null);
		chooser.addObject("La Buena dijo el Frank", null);
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * Esta función se llama una vez cada vez que el robot ingresa al modo
	 * Deshabilitado. Puede usarlo para restablecer cualquier información del
	 * subsistema que desee borrar cuando el robot esté deshabilitado.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * Este modo autónomo (junto con el código de selección anterior) muestra cómo
	 * seleccionar entre diferentes modos autónomos utilizando el panel de control.
	 * El código de selección enviable funciona con Java SmartDashboard. Si prefiere
	 * el Tablero de LabVIEW, elimine todo el código del selector y elimine el
	 * comentario del código getString para obtener el nombre automático del cuadro
	 * de texto debajo de Gyro
	 *
	 * <p>
	 * Puede agregar modos automáticos adicionales agregando comandos adicionales al
	 * código de selección anterior (como el ejemplo comentado) o comparaciones
	 * adicionales a la estructura del conmutador a continuación con cadenas y
	 * comandos adicionales.
	 */
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// programar el comando autónomo (ejemplo)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * Esta función se llama periódicamente durante el autónomo.
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// Esto asegura que la autonomía se detiene cuando se inicia la ejecución de
		// teleop. Si desea que la autonomía continúe hasta que sea interrumpida por
		// otro comando, elimine esta línea o coméntela.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * Esta función se llama periódicamente durante el control del operador.
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * Esta función se llama periódicamente durante el modo de prueba.
	 */
	public void testPeriodic() {
	}
}
