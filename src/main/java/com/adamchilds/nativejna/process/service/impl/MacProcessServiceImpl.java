package com.adamchilds.nativejna.process.service.impl;

import com.adamchilds.nativejna.process.model.JProcess;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * {@inheritDoc}
 */
public class MacProcessServiceImpl extends AbstractProcessServiceImpl {

    /**
     * {@inheritDoc}
     */
    public JProcess findProcessById(int pid) {
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    public JProcess findProcessByExecutableName(String name) {
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    public Pointer openProcess(int permissions, int pid) {
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    public long findDynamicAddress(Pointer process, int[] offsets, long baseAddress) {
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    public Memory readMemory(Pointer process, long address, int bytesToRead) {
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    public boolean writeMemory(Pointer process, long address, byte[] data) {
        throw new NotImplementedException();
    }

}