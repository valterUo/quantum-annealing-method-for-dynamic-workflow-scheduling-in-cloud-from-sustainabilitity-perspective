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
		String inputFolder = Quantum.class.getClassLoader().getResource("workload/planetlab").getPath();
		String outputFolder = "output";
		String workload = "quantum"; // PlanetLab workload

            PlanetLabRunnerQuantum runner = new PlanetLabRunnerQuantum(
                    enableOutput,
                    outputToFile,
                    inputFolder,
                    outputFolder,
                    workload);
            
            runner.init(inputFolder + "/" + workload);
            
            System.out.println(runner.getHosts().size());
            System.out.println(runner.getVMs().size());
            System.out.println(runner.getCloudlets().size());
            
            runner.start(workload, outputFolder);
	}
    
}
