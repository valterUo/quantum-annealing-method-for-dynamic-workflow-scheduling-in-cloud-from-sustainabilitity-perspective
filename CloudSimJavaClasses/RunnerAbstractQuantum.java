package org.cloudbus.cloudsim.examples.power;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerDatacenter;
import org.cloudbus.cloudsim.power.PowerHost;

public abstract class RunnerAbstractQuantum {

    /**
     * The enable output.
     */
    private static boolean enableOutput;

    /**
     * The broker.
     */
    protected static DatacenterBroker broker;

    /**
     * The cloudlet list.
     */
    protected static List<Cloudlet> cloudletList;

    /**
     * The vm list.
     */
    protected static List<Vm> vmList;

    /**
     * The host list.
     */
    protected static List<PowerHost> hostList;

    /**
     * Run.
     *
     * @param enableOutput the enable output
     * @param outputToFile the output to file
     * @param inputFolder the input folder
     * @param outputFolder the output folder
     * @param workload the workload
     */
    public RunnerAbstractQuantum(
            boolean enableOutput,
            boolean outputToFile,
            String inputFolder,
            String outputFolder,
            String workload) {
        try {
            initLogOutput(
                    enableOutput,
                    outputToFile,
                    outputFolder,
                    workload);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    /**
     * Inits the log output.
     *
     * @param enableOutput the enable output
     * @param outputToFile the output to file
     * @param outputFolder the output folder
     * @param workload the workload
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws FileNotFoundException the file not found exception
     */
    protected void initLogOutput(
            boolean enableOutput,
            boolean outputToFile,
            String outputFolder,
            String workload) throws IOException, FileNotFoundException {
        setEnableOutput(enableOutput);
        Log.setDisabled(!isEnableOutput());
        if (isEnableOutput() && outputToFile) {
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            File folder2 = new File(outputFolder + "/log");
            if (!folder2.exists()) {
                folder2.mkdir();
            }

            File file = new File(outputFolder + "/log/"
                    + getExperimentName(workload) + ".txt");
            file.createNewFile();
            Log.setOutput(new FileOutputStream(file));
        }
    }

    /**
     * Inits the simulation.
     *
     * @param inputFolder the input folder
     */
    protected abstract void init(String inputFolder);

    /**
     * Starts the simulation.
     *
     * @param experimentName the experiment name
     * @param outputFolder the output folder
     * @param vmAllocationPolicy the vm allocation policy
     */
    protected void start(String experimentName, String outputFolder, VmAllocationPolicy vmAllocationPolicy) {
        System.out.println("Starting " + experimentName);

        try {
            PowerDatacenter datacenter = (PowerDatacenter) Helper.createDatacenter(
                    "Datacenter",
                    PowerDatacenter.class,
                    hostList,
                    vmAllocationPolicy);

            datacenter.setDisableMigrations(false);

            broker.submitVmList(vmList);
            broker.submitCloudletList(cloudletList);

            CloudSim.terminateSimulation(Constants.SIMULATION_LIMIT);
            double lastClock = CloudSim.startSimulation();

            List<Cloudlet> newList = broker.getCloudletReceivedList();
            System.out.println("Received " + newList.size() + " cloudlets");
            Log.printLine("Received " + newList.size() + " cloudlets");

            CloudSim.stopSimulation();

            Helper.printResults(
                    datacenter,
                    vmList,
                    lastClock,
                    experimentName,
                    Constants.OUTPUT_CSV,
                    outputFolder);

        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("The simulation has been terminated due to an unexpected error");
            System.exit(0);
        }

        Log.printLine("Finished " + experimentName);
    }

    /**
     * Gets the experiment name.
     *
     * @param args the args
     * @return the experiment name
     */
    protected String getExperimentName(String... args) {
        StringBuilder experimentName = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (args[i].isEmpty()) {
                continue;
            }
            if (i != 0) {
                experimentName.append("_");
            }
            experimentName.append(args[i]);
        }
        return experimentName.toString();
    }

    /**
     * Sets the enable output.
     *
     * @param enableOutput the new enable output
     */
    public void setEnableOutput(boolean enableOutput) {
        RunnerAbstractQuantum.enableOutput = enableOutput;
    }

    /**
     * Checks if is enable output.
     *
     * @return true, if is enable output
     */
    public boolean isEnableOutput() {
        return enableOutput;
    }

}
