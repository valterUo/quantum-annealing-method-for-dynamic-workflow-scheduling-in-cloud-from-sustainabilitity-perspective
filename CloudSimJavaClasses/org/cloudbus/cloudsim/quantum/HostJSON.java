/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cloudbus.cloudsim.quantum;

/**
 *
 * @author Valter
 */
public class HostJSON {

    /**
     * The id of the host.
     */
    private int id;

    /**
     * The storage capacity.
     */
    private long storage;

    /**
     * The total ram capacity from the host that the provisioner can allocate to VMs.
     */
    private int ram;

    /**
     * The available ram.
     */
    private int availableRam;

    /**
     * The total mips capacity of the PE that the provisioner can allocate to VMs.
     */
    private int mips;

    /**
     * Instantiates a new host.
     *
     * @param id the host id
     * @param ramProvisioner the ram provisioner
     * @param bwProvisioner the bw provisioner
     * @param storage the storage capacity
     * @param peList the host's PEs list
     * @param vmScheduler the vm scheduler
     */
    public HostJSON(int id, long storage, int ram, int mips) {
        setId(id);
        setStorage(storage);
        setRam(ram);
        setMips(mips);
    }

    /**
     * Sets the host id.
     *
     * @param id the new host id
     */
    protected void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the storage.
     *
     * @param storage the new storage
     */
    protected void setStorage(long storage) {
        this.storage = storage;
    }

    /**
     * Sets the ram capacity.
     *
     * @param ram the ram to set
     */
    protected void setRam(int ram) {
        this.ram = ram;
    }

    /**
     * Sets the MIPS.
     *
     * @param mips the MIPS to set
     */
    public void setMips(int mips) {
        this.mips = mips;
    }
}
