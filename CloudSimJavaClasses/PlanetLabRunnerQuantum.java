package org.cloudbus.cloudsim.examples.power.planetlab;

import java.util.Calendar;
import java.util.List;
import org.cloudbus.cloudsim.Cloudlet;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.examples.power.Helper;
import org.cloudbus.cloudsim.examples.power.RunnerAbstractQuantum;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerVmAllocationPolicyAbstract;
import org.cloudbus.cloudsim.quantum.PowerVmAllocationPolicyQuantum;

/**
 * The example runner for the PlanetLab workload.
 *
 */
public class PlanetLabRunnerQuantum extends RunnerAbstractQuantum {

    /**
     * Instantiates a new planet lab runner.
     *
     * @param enableOutput the enable output
     * @param outputToFile the output to file
     * @param inputFolder the input folder
     * @param outputFolder the output folder
     * @param workload the workload
     * @param vmAllocationPolicy the vm allocation policy
     * @param vmSelectionPolicy the vm selection policy
     * @param parameter the parameter
     */
    public PlanetLabRunnerQuantum(
            boolean enableOutput,
            boolean outputToFile,
            String inputFolder,
            String outputFolder,
            String workload,
            String vmAllocationPolicy,
            String vmSelectionPolicy,
            String parameter) {
        super(
                enableOutput,
                outputToFile,
                inputFolder,
                outputFolder,
                workload,
                vmAllocationPolicy,
                vmSelectionPolicy,
                parameter);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudbus.cloudsim.examples.power.RunnerAbstract#init(java.lang.String)
     */
    @Override
    protected void init(String inputFolder) {
        try {
            CloudSim.init(1, Calendar.getInstance(), false);

            broker = Helper.createBroker();
            int brokerId = broker.getId();

            cloudletList = PlanetLabHelper.createCloudletListPlanetLab(brokerId, inputFolder);
            vmList = Helper.createVmList(brokerId, cloudletList.size());
            hostList = Helper.createHostList(20);
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("The simulation has been terminated due to an unexpected error");
            System.exit(0);
        }
    }

    public List<PowerHost> getHosts() {
        return hostList;
    }

    public List<Vm> getVMs() {
        return vmList;
    }

    public List<Cloudlet> getCloudlets() {
        return cloudletList;
    }

    public void start(String workload,
            String outputFolder,
            String vmAllocationPolicy,
            String vmSelectionPolicyName,
            String parameterName) {

        String name = getExperimentName(workload, vmAllocationPolicy, vmSelectionPolicyName, parameterName);
        PowerVmAllocationPolicyAbstract policy = new PowerVmAllocationPolicyQuantum(hostList);
        start(name, outputFolder, policy);
    }

}
