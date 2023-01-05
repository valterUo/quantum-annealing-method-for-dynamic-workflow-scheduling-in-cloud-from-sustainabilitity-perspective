/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cloudbus.cloudsim.quantum;

/**
 *
 * @author Valter
 */
final class CloudletJSON {

    /**
     * The cloudlet ID.
     */
    private int cloudletId;

    /**
     * The User or Broker ID. It is advisable that broker set this ID with its own ID, so that CloudResource returns to it after the execution.
     *
     */
    private int userId;

    /**
     * The execution length of this Cloudlet (Unit: in Million Instructions (MI)). According to this length and the power of the processor (in Million Instruction Per Second - MIPS) where the cloudlet will be run, the cloudlet will take a given time to finish processing. For instance, for a cloudlet of 10000 MI running on a processor of 2000 MIPS, the cloudlet will spend 5 seconds using the processor in order to be completed (that may be uninterrupted or not, depending on the scheduling policy).
     *
     * @see #setNumberOfPes(int)
     *
     */
    private long cloudletLength;

    /**
     * The input file size of this Cloudlet before execution (unit: in byte). This size has to be considered the program + input data sizes.
     */
    private long cloudletFileSize;

    /**
     * The output file size of this Cloudlet after execution (unit: in byte).
     *
     * @todo See
     * <a href="https://groups.google.com/forum/#!topic/cloudsim/MyZ7OnrXuuI">this discussion</a>
     */
    private long cloudletOutputSize;

    /**
     * The number of Processing Elements (Pe) required to execute this cloudlet (job).
     *
     * @see #setNumberOfPes(int)
     */
    private int numberOfPes;

    /**
     * The execution start time of this Cloudlet. With new functionalities, such as CANCEL, PAUSED and RESUMED, this attribute only stores the latest execution time. Previous execution time are ignored.
     */
    private double execStartTime;

    /**
     * The time where this Cloudlet completes.
     */
    private double finishTime;

    /**
     * The id of the vm that is planned to execute the cloudlet.
     */
    protected int vmId;

    /**
     * The cost of each byte of bandwidth (bw) consumed.
     */
    protected double costPerBw;
    
    protected long totalLength;

    /**
     * The total bandwidth (bw) cost for transferring the cloudlet by the network, according to the {@link #cloudletFileSize}.
     */
    //protected double accumulatedBwCost;

    public CloudletJSON(
            final int cloudletId,
            final int userId,
            final long cloudletLength,
            final long cloudletFileSize,
            final long cloudletOutputSize,
            final int numberOfPes,
            final double execStartTime,
            final double finishTime,
            final int vmId,
            final double costPerBw,
            final long totalLength) {
        setUserId(userId);
        setCloudletLength(cloudletLength);
        setCloudletId(cloudletId);
        setNumberOfPes(numberOfPes);
        setCloudletFileSize(cloudletFileSize);
        setCloudletOutputSize(cloudletOutputSize);
        setVmId(vmId);
        setTotalLength(totalLength);
        setCostPerBw(costPerBw);
        setExecStartTime(execStartTime);
        setFinishTime(finishTime);
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public void setCloudletFileSize(long cloudletFileSize) {
        this.cloudletFileSize = cloudletFileSize;
    }

    public void setCloudletOutputSize(long cloudletOutputSize) {
        this.cloudletOutputSize = cloudletOutputSize;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCloudletId(int cloudletId) {
        this.cloudletId = cloudletId;
    }

    public void setCloudletLength(long cloudletLength) {
        this.cloudletLength = cloudletLength;
    }

    public void setNumberOfPes(int numberOfPes) {
        this.numberOfPes = numberOfPes;
    }

    public void setExecStartTime(double execStartTime) {
        this.execStartTime = execStartTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public void setCostPerBw(double costPerBw) {
        this.costPerBw = costPerBw;
    }

}
