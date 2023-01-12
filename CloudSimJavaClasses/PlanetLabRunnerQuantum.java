package org.cloudbus.cloudsim.examples.power.planetlab;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.cloudbus.cloudsim.Cloudlet;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelNull;
import org.cloudbus.cloudsim.UtilizationModelPlanetLabInMemory;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.examples.power.Constants;
import static org.cloudbus.cloudsim.examples.power.Constants.SCHEDULING_INTERVAL;
import org.cloudbus.cloudsim.examples.power.Helper;
import org.cloudbus.cloudsim.examples.power.RunnerAbstractQuantum;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerVmAllocationPolicyAbstract;
import org.cloudbus.cloudsim.quantum.CloudletJSON;
import org.cloudbus.cloudsim.quantum.HostJSON;
import org.cloudbus.cloudsim.quantum.PowerVmAllocationPolicyQuantum;
import org.cloudbus.cloudsim.quantum.VMJSON;

/**
 * The example runner for the PlanetLab workload with a quantum annealer in the backend.
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
     */
    public PlanetLabRunnerQuantum(
            boolean enableOutput,
            boolean outputToFile,
            String inputFolder,
            String outputFolder,
            String workload) {
        super(
                enableOutput,
                outputToFile,
                inputFolder,
                outputFolder,
                workload);
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

            File inputFolderFile = new File(inputFolder);
            File[] files = inputFolderFile.listFiles();

            vmList = Helper.createVmList(brokerId, files.length);
            hostList = Helper.createHostList(20);
            cloudletList = createCloudletListPlanetLab(brokerId, inputFolder);

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

    public void setCloudlets(List<Cloudlet> cloudlets) {
        this.cloudletList = cloudlets;
    }

    public void start(String workload,
            String outputFolder) {

        String name = getExperimentName(workload);
        PowerVmAllocationPolicyAbstract policy = new PowerVmAllocationPolicyQuantum(hostList);
        start(name, outputFolder, policy);
    }

    /**
     * Creates the cloudlet list planet lab.
     *
     * @param brokerId the broker id
     * @param inputFolderName the input folder name
     * @return the list
     * @throws FileNotFoundException the file not found exception
     */
    public List<Cloudlet> createCloudletListPlanetLab(int brokerId, String inputFolderName)
            throws FileNotFoundException {
        List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();

        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModelNull = new UtilizationModelNull();

        File inputFolder = new File(inputFolderName);
        File[] files = inputFolder.listFiles();

        for (int i = 0; i < files.length; i++) {
            Cloudlet cloudlet = null;
            try {
                cloudlet = new Cloudlet(
                        i,
                        Constants.CLOUDLET_LENGTH,
                        Constants.CLOUDLET_PES,
                        fileSize,
                        outputSize,
                        new UtilizationModelPlanetLabInMemory(
                                files[i].getAbsolutePath(),
                                Constants.SCHEDULING_INTERVAL), utilizationModelNull, utilizationModelNull);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
            cloudlet.setUserId(brokerId);
            cloudletList.add(cloudlet);
        }

        System.out.println("Initial task allocation to virtual machines...");
        Map<Integer, Integer> allocation = optimizeInitialCloudletAllocation(cloudletList);

        System.out.println(allocation);

        for (Cloudlet cloudlet : cloudletList) {
            cloudlet.setVmId(allocation.get(cloudlet.getCloudletId()));
        }

        return cloudletList;
    }

    public Map<Integer, Integer> optimizeInitialCloudletAllocation(List<Cloudlet> cloudletList) {

        Map<Integer, Integer> taskAllocationMap = null;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("http://127.0.0.1:1234/");
            Gson gson = new Gson();
            Map<String, Object> jsonmap = new HashMap<>();
            Map<Integer, HostJSON> hostmap = new HashMap<>();
            Map<Integer, VMJSON> vmmap = new HashMap<>();
            Map<Integer, CloudletJSON> cloudletmap = new HashMap<>();

            for (PowerHost host : getHosts()) {
                HostJSON hostjson = new HostJSON(
                        host.getId(),
                        host.getStorage(),
                        host.getRam(),
                        host.getTotalMips(),
                        host.getMaxPower(),
                        host.getBwProvisioner().getAvailableBw()
                );
                hostmap.put(host.getId(), hostjson);
            }

            for (Vm entry : getVMs()) {
                VMJSON vmjson = new VMJSON(
                        entry.getId(),
                        entry.getUserId(),
                        entry.getUid(),
                        entry.getSize(),
                        entry.getMips(),
                        entry.getNumberOfPes(),
                        entry.getRam(),
                        entry.getBw()
                );
                vmmap.put(entry.getId(), vmjson);
            }

            for (Cloudlet cloudlet : cloudletList) {
                CloudletJSON cloudletjson = new CloudletJSON(
                        cloudlet.getCloudletId(),
                        cloudlet.getUserId(),
                        cloudlet.getCloudletLength(),
                        cloudlet.getCloudletFileSize(),
                        cloudlet.getCloudletOutputSize(),
                        cloudlet.getNumberOfPes()
                );
                cloudletmap.put(cloudlet.getCloudletId(), cloudletjson);
            }

            jsonmap.put("hosts", hostmap);
            jsonmap.put("vms", vmmap);
            jsonmap.put("cloudlets", cloudletmap);
            double time = vmList.get(0).getCloudletScheduler().getPreviousTime();
            jsonmap.put("time", time);
            jsonmap.put("interval", SCHEDULING_INTERVAL);

            String converted = gson.toJson(jsonmap);
            //System.out.println(converted);
            StringEntity params = new StringEntity(converted);
            request.addHeader("Content-type", "application/json");
            request.setEntity(params);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                StatusLine statusLine = response.getStatusLine();
                System.out.println(statusLine.getStatusCode() + " " + statusLine.getReasonPhrase());
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                System.out.println("Response body: " + responseBody);
                Map<String, String> retMap = new Gson().fromJson(responseBody, new TypeToken<HashMap<String, Object>>() {
                }.getType());
                taskAllocationMap = constructTaskAllocationMap(retMap);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return taskAllocationMap;
    }

    private static Map<Integer, Integer> constructTaskAllocationMap(Map<String, String> retMap) {
        Map<Integer, Integer> allocation = new HashMap<>();
        for (String entry : retMap.keySet()) {
            allocation.put(Integer.parseInt(entry), Integer.parseInt(retMap.get(entry)));
        }
        return allocation;
    }

}
