package com.adamchilds.nativejna.process.service.impl;

import com.adamchilds.nativejna.exception.ProcessNotFoundException;
import com.adamchilds.nativejna.nativec.windows.Psapi;
import com.adamchilds.nativejna.process.model.JProcess;
import com.adamchilds.nativejna.process.service.ProcessService;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.util.List;

/**
 * {@inheritDoc}
 */
public abstract class AbstractProcessServiceImpl implements ProcessService {

    /**
     * Finds a running process with the given process ID.
     *
     * @param pid the ID of the process to find
     * @return a new {@link JProcess} that represents the process with the given ID
     */
    public abstract JProcess findProcessById(int pid) throws ProcessNotFoundException;

    /**
     * Finds a running process with the given name.
     *
     * @param name the name of the process to find
     * @return a new {@link JProcess} that represents the process with the given name
     */
    public abstract JProcess findProcessByExecutableName(String name) throws ProcessNotFoundException;

    /**
     * Opens the process with the given {@code pid} with the given permissions.
     *
     * @param permissions the permissions to open the process with
     * @param pid the ID of the process to open
     * @return a {@link Pointer} to the opened process
     */
    public abstract Pointer openProcess(int permissions, int pid) throws ProcessNotFoundException;

    /**
     * Finds the dynamic address for the given process with the given base address and offsets.
     *
     * @param process the process to find the dynamic address for
     * @param offsets the offsets for the memory location
     * @param baseAddress the base address for the process
     * @return a new long that represents a dynamic address for the given process
     */
    public abstract long findDynamicAddress(Pointer process, int[] offsets, long baseAddress);

    /**
     * Reads the memory from the given pointer's address and the number of bytes to read.
     *
     * @param process the process to read memory from
     * @param address the address of the to begin reading memory from
     * @param bytesToRead the total number of bytes to read
     * @return a {@link Memory} object that holds the bytes read
     */
    public abstract Memory readMemory(Pointer process, long address, int bytesToRead);

    /**
     * Writes the given {@code data} to the given address for the given process.
     *
     * @param process the process to write data to
     * @param address the address to write data to
     * @param data the data to write
     * @return true if data write was successful; false otherwise
     */
    public abstract boolean writeMemory(Pointer process, long address, byte[] data);

    /**
     * {@inheritDoc}
     */
    public List<Integer> getAllProcessIds() {
        int[] processArray = new int[1024];

        // Populate the array with a list of IDs for all running processes
        Psapi.INSTANCE.EnumProcesses(processArray, 1024, new IntByReference(1024));

        // Convert the array to a List
        List<Integer> processList = Lists.newArrayList(Ints.asList(processArray));

        // Anything after the first index with an ID of 0 is not valid; filter them out
        Iterable<Integer> processListShortened = Iterables.skip(processList, 1);
        Iterables.removeIf(processListShortened, new Predicate<Integer>() {
            public boolean apply(Integer input) {
                return input == 0;
            }
        });

        return processList;
    }

}