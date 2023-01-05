/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cloudbus.cloudsim.examples.power.planetlab;

import java.io.IOException;

/**
 *
 * @author Valter
 */
public class Quantum {
    
    	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		boolean enableOutput = true;
		boolean outputToFile = true;
		String inputFolder = Dvfs.class.getClassLoader().getResource("workload/planetlab").getPath();
		String outputFolder = "output";
		String workload = "quantum"; // PlanetLab workload
		String vmAllocationPolicy = "dvfs"; // DVFS policy without VM migrations
		String vmSelectionPolicy = "";
		String parameter = "";

            PlanetLabRunnerQuantum runner = new PlanetLabRunnerQuantum(
                    enableOutput,
                    outputToFile,
                    inputFolder,
                    outputFolder,
                    workload,
                    vmAllocationPolicy,
                    vmSelectionPolicy,
                    parameter);
            
            runner.init(inputFolder + "/" + workload);
            
            System.out.println(runner.getHosts().size());
            System.out.println(runner.getVMs().size());
            System.out.println(runner.getCloudlets().size());
            
            runner.start(workload,
                        outputFolder,
                        vmAllocationPolicy, 
                        vmSelectionPolicy, 
                        parameter);
	}
    
}
