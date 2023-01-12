package org.cloudbus.cloudsim.quantum;

/**
 *
 * @author Valter
 */
public class CloudletJSON {

    private int cloudletId;
    private int userId;
    private long cloudletLength;
    private long cloudletFileSize;

    /**
     * The output file size of this Cloudlet after execution (unit: in byte).
     *
     * @todo See
     * <a href="https://groups.google.com/forum/#!topic/cloudsim/MyZ7OnrXuuI">this discussion</a>
     */
    private long cloudletOutputSize;
    private int numberOfPes;
    private double execStartTime;
    private double finishTime;
    protected int vmId;
    protected double costPerBw;
    protected long totalLength;

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

    public CloudletJSON(
            final int cloudletId,
            final int userId,
            final long cloudletLength,
            final long cloudletFileSize,
            final long cloudletOutputSize,
            final int numberOfPes
    ) {
        setUserId(userId);
        setCloudletLength(cloudletLength);
        setCloudletId(cloudletId);
        setNumberOfPes(numberOfPes);
        setCloudletFileSize(cloudletFileSize);
        setCloudletOutputSize(cloudletOutputSize);
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
