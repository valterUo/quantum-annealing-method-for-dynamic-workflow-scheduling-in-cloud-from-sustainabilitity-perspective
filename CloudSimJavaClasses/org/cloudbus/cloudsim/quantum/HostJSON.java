package org.cloudbus.cloudsim.quantum;

/**
 *
 * @author Valter
 */
public class HostJSON {

    private int id;
    private long storage;
    private int ram;
    private int availableRam;
    private int mips;
    private double power;
    private long availableBw;

    public HostJSON(int id, long storage, int ram, int mips, double power, long bw) {
        setId(id);
        setStorage(storage);
        setRam(ram);
        setMips(mips);
        setPower(power);
        setavailableBW(bw);
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setStorage(long storage) {
        this.storage = storage;
    }

    protected void setRam(int ram) {
        this.ram = ram;
    }

    public void setMips(int mips) {
        this.mips = mips;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setavailableBW(long availableBw) {
        this.availableBw = availableBw;
    }
}
