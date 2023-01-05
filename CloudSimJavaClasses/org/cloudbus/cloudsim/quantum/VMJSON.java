/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cloudbus.cloudsim.quantum;

/**
 *
 * @author Valter
 */
final class VMJSON {

    /**
     * The VM unique id.
     */
    private int id;

    /**
     * The user id.
     */
    private int userId;

    /**
     * A Unique Identifier (UID) for the VM, that is compounded by the user id and VM id.
     */
    private String uid;

    /**
     * The size the VM image size (the amount of storage it will use, at least initially).
     */
    private long size;

    /**
     * The MIPS capacity of each VM's PE.
     */
    private double mips;

    /**
     * The number of PEs required by the VM.
     */
    private int numberOfPes;

    /**
     * The required ram.
     */
    private int ram;

    /**
     * The required bw.
     */
    private long bw;

    /**
     * The PM that hosts the VM.
     */
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

    }

    /**
     * Sets the VM id
     *
     * @param id the new VM id
     */
    protected void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the user id.
     *
     * @param id the new user id
     */
    protected void setUserId(int id) {
        this.userId = id;
    }

    /**
     * Sets the host id
     *
     * @param id the new host id
     */
    protected void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Sets the size
     *
     * @param id the new size
     */
    protected void setSize(long size) {
        this.size = size;
    }

    /**
     * Sets the MIPS (Million Instructions Per Second)
     *
     * @param id the new MIPS
     */
    protected void setMips(double mips) {
        this.mips = mips;
    }

    /**
     * Sets the number of PES
     *
     * @param id the new number of PES
     */
    protected void setNumberOfPes(int numberOfPes) {
        this.numberOfPes = numberOfPes;
    }

    /**
     * Sets the RAM
     *
     * @param id the new RAM
     */
    protected void setRam(int ram) {
        this.ram = ram;
    }

    /**
     * Sets the bandwidth
     *
     * @param id the new bandwidth
     */
    protected void setBw(long bw) {
        this.bw = bw;
    }

    /**
     * Sets the host id for the Vm
     *
     * @param id the new host id for the Vm
     */
    protected void setHostid(int hostid) {
        this.hostid = hostid;
    }

}
