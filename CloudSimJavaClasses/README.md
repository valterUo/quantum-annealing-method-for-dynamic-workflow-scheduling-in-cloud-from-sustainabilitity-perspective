# Modifications for CloudSim to optimize allocations on quantum annealer

1. Download and open CloudSim with examples to IDE.
2. Update the pom.xml file in cloudsim-examples to match with the pom.xml file in this folder.
3. Copy the Java classes in the org.cloudbus.cloudsim.quantum to cloudsim-examples Source Packages folder.
4. Copy Quantum.java and PlanetLabRunnerQuantum.java to org.cloudbus.cloudsim.examples.power.planetlab folder.
5. Copy RunnerAbstractQuantum.java to org.cloudbus.cloudsim.examples.power folder.
4. Build the project
5. Modify the workloads. Create a new workload with smaller number of workload files. For example, consider selecting 20 files from the PlanetLab workload 20110303 located in cloudsim-examples\src\main\resources\workload\planetlab\20110303.
6. Reduce the number of hosts. The constant can be found in PlanetLabRunnerQuantum.java file in the createHostList method: `hostList = Helper.createHostList(numberOfHost);`.
7. Start the Jupyter notebook. This creates a backend point
8. Run the simulation.