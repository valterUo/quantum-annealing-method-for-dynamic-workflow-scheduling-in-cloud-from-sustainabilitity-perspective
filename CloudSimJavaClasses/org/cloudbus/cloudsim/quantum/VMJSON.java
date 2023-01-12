package org.cloudbus.cloudsim.quantum;

/**
 *
 * @author Valter
 */
public class VMJSON {

    private int id;
    private int userId;
    private String uid;
    private long size;
    private double mips;
    private int numberOfPes;
    private int ram;
    private long bw;
    private int hostid;

    public VMJSON(
            int id,
            int userId,
            String uid,
            long size,
            double mips,
            int numberOfPes,
            int ram,
            long bw,
            int hostid
    ) {
        setId(id);
        setUserId(userId);
        setUid(uid);
        setSize(size);
        setMips(mips);
        setNumberOfPes(numberOfPes);
        setRam(ram);
        setHostid(hostid);
    }

    public VMJSON(
            int id,
            int userId,
            String uid,
            long size,
            double mips,
            int numberOfPes,
            int ram,
            long bw
    ) {
        setId(id);
        setUserId(userId);
        setUid(uid);
        setSize(size);
        setMips(mips);
        setNumberOfPes(numberOfPes);
        setRam(ram);
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setUserId(int id) {
        this.userId = id;
    }

    protected void setUid(String uid) {
        this.uid = uid;
    }

    protected void setSize(long size) {
        this.size = size;
    }

    protected void setMips(double mips) {
        this.mips = mips;
    }

    protected void setNumberOfPes(int numberOfPes) {
        this.numberOfPes = numberOfPes;
    }

    protected void setRam(int ram) {
        this.ram = ram;
    }

    protected void setBw(long bw) {
        this.bw = bw;
    }

    protected void setHostid(int hostid) {
        this.hostid = hostid;
    }

}
