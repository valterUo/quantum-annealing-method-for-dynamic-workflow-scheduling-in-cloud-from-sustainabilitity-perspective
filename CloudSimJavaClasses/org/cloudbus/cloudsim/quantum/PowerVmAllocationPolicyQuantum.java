/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cloudbus.cloudsim.quantum;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.ResCloudlet;
import org.cloudbus.cloudsim.Vm;
import static org.cloudbus.cloudsim.examples.power.Constants.SCHEDULING_INTERVAL;
import org.cloudbus.cloudsim.power.PowerVmAllocationPolicyAbstract;
import org.cloudbus.cloudsim.util.ExecutionTimeMeasurer;

/**
 *
 * @author Valter
 */
public class PowerVmAllocationPolicyQuantum extends
        PowerVmAllocationPolicyAbstract {

    public PowerVmAllocationPolicyQuantum(List<? extends Host> list) {
        super(list);
    }

    @Override
    public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
        ExecutionTimeMeasurer.start("optimizeAllocationTotal");
        //System.out.println(getVmTable());
        List<Map<String, Object>> migrationMap = null;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("http://127.0.0.1:1234/");
            Gson gson = new Gson();
            Map<String, Host> vmT = getVmTable();
            Map<String, Object> jsonmap = new HashMap<>();
            Map<Integer, HostJSON> hostmap = new HashMap<>();
            Map<Integer, VMJSON> vmmap = new HashMap<>();
            Map<Integer, CloudletJSON> cloudletmap = new HashMap<>();

            for (Map.Entry<String, Host> entry : vmT.entrySet()) {
                //System.out.println("Key = " + entry.getKey() + ", 
                //Value = " + entry.getValue());

                HostJSON hostjson = new HostJSON(
                        entry.getValue().getId(),
                        entry.getValue().getStorage(),
                        entry.getValue().getRam(),
                        entry.getValue().getTotalMips());
                hostmap.put(entry.getValue().getId(), hostjson);

            }

            for (Vm entry : vmList) {
                VMJSON vmjson = new VMJSON(
                        entry.getId(),
                        entry.getUserId(),
                        entry.getUid(),
                        entry.getSize(),
                        entry.getMips(),
                        entry.getNumberOfPes(),
                        entry.getRam(),
                        entry.getBw(),
                        entry.getHost().getId()
                );
                vmmap.put(entry.getId(), vmjson);
            }

            for (Vm entry : vmList) {
                for (ResCloudlet task : entry.getCloudletScheduler().getCloudletExecList()) {
                    Cloudlet cloudlet = task.getCloudlet();
                    CloudletJSON cloudletjson = new CloudletJSON(
                            cloudlet.getCloudletId(),
                            cloudlet.getUserId(),
                            cloudlet.getCloudletLength(),
                            cloudlet.getCloudletFileSize(),
                            cloudlet.getCloudletOutputSize(),
                            cloudlet.getNumberOfPes(),
                            cloudlet.getExecStartTime(),
                            cloudlet.getFinishTime(),
                            cloudlet.getVmId(),
                            cloudlet.getCostPerSec(),
                            cloudlet.getCloudletTotalLength()
                    );
                    cloudletmap.put(cloudlet.getCloudletId(), cloudletjson);
                }
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
                Map<String, String> retMap = new Gson().fromJson(responseBody, new TypeToken<HashMap<String, Object>>() {}.getType());
                migrationMap = constructMigrationMap(retMap, vmList, getHostList());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //ExecutionTimeMeasurer.start("optimizeAllocationHostSelection");
        //List<PowerHostUtilizationHistory> overUtilizedHosts = getOverUtilizedHosts();
        //getExecutionTimeHistoryHostSelection().add(
        //                ExecutionTimeMeasurer.end("optimizeAllocationHostSelection"));
        //printOverUtilizedHosts(overUtilizedHosts);
        //saveAllocation();
        //ExecutionTimeMeasurer.start("optimizeAllocationVmSelection");
        //List<? extends Vm> vmsToMigrate = getVmsToMigrateFromHosts(overUtilizedHosts);
        //getExecutionTimeHistoryVmSelection().add(ExecutionTimeMeasurer.end("optimizeAllocationVmSelection"));
        //Log.printLine("Reallocation of VMs from the over-utilized hosts:");
        //ExecutionTimeMeasurer.start("optimizeAllocationVmReallocation");
        //List<Map<String, Object>> migrationMap = getNewVmPlacement(vmsToMigrate, new HashSet<Host>(
        //        overUtilizedHosts));
        //getExecutionTimeHistoryVmReallocation().add(
        //        ExecutionTimeMeasurer.end("optimizeAllocationVmReallocation"));
        //Log.printLine();
        //
        //migrationMap.addAll(getMigrationMapFromUnderUtilizedHosts(overUtilizedHosts));
        //
        //restoreAllocation();
        //
        //getExecutionTimeHistoryTotal().add(ExecutionTimeMeasurer.end("optimizeAllocationTotal"));
        //return migrationMap;
        return migrationMap;
    }
    
    public List<Map<String, Object>> constructMigrationMap(
            Map<String, String> response,
            List<? extends Vm> vmList, 
            List<? extends Host> hostList) {
        List<Map<String, Object>> migrationMap = new ArrayList<>();
        for (Map.Entry<String,String> entry : response.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Map<String, Object> map = new HashMap<>();
            for (Vm v : vmList) {
                if(v.getId() == Integer. parseInt(key)) {
                    map.put("vm", v);
                }
            }
            for (Host h : hostList) {
                if(h.getId() == Integer. parseInt(value)) {
                    map.put("host", h);
                }
            }
            migrationMap.add(map);
        }
        return migrationMap;
        
    }

}