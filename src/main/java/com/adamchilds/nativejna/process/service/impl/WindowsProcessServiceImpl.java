package com.adamchilds.nativejna.process.service.impl;

import com.adamchilds.nativejna.nativec.Kernel32;
import com.adamchilds.nativejna.process.ProcessUtil;
import com.adamchilds.nativejna.process.model.JProcess;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

/**
 * {@inheritDoc}
 */
public class WindowsProcessServiceImpl extends AbstractProcessServiceImpl {

    Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);

    /**
     * {@inheritDoc}
     */
    public JProcess findProcessById(int pid) {
        Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();
        WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));

        try  {
            while (kernel32.Process32Next(snapshot, processEntry)) {
                if (pid == processEntry.th32ProcessID.intValue()) {
                    JProcess process = new JProcess(processEntry.th32ProcessID.intValue(), Native.toString(processEntry.szExeFile));
                    process.setPointer(openProcess(ProcessUtil.WINDOWS.PROCESS_VM_ALL, process.getId()));

                    return process;
                }
            }
        } finally {
            kernel32.CloseHandle(snapshot);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public JProcess findProcessByExecutableName(String name) {
        Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();
        WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));

        try  {
            while (kernel32.Process32Next(snapshot, processEntry)) {
                if (name.equals(Native.toString(processEntry.szExeFile))) {
                    JProcess process = new JProcess(processEntry.th32ProcessID.intValue(), name);
                    process.setPointer(openProcess(ProcessUtil.WINDOWS.PROCESS_VM_ALL, process.getId()));

                    return process;
                }
            }
        } finally {
            kernel32.CloseHandle(snapshot);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Pointer openProcess(int permissions, int pid) {
        return kernel32.OpenProcess(permissions, true, pid).getPointer();
    }

    /**
     * {@inheritDoc}
     */
    public long findDynamicAddress(Pointer process, int[] offsets, long baseAddress) {
        int size = 4;
        Memory pTemp = new Memory(size);
        long pointerAddress = 0;

        for (int i = 0; i < offsets.length; i++) {
            if (i == 0) {
                kernel32.ReadProcessMemory(new WinNT.HANDLE(process), baseAddress, pTemp, size, null);
            }

            pointerAddress = pTemp.getInt(0) + offsets[i];

            if (i != (offsets.length - 1)) {
                kernel32.ReadProcessMemory(new WinNT.HANDLE(process), pointerAddress, pTemp, size, null);
            }
        }

        return pointerAddress;
    }

    /**
     * {@inheritDoc}
     */
    public Memory readMemory(Pointer process, long address, int bytesToRead) {
        IntByReference read = new IntByReference(0);
        Memory output = new Memory(bytesToRead);

        kernel32.ReadProcessMemory(new WinNT.HANDLE(process), address, output, bytesToRead, read);

        return output;
    }

    /**
     * {@inheritDoc}
     */
    public boolean writeMemory(Pointer process, long address, byte[] data) {
        int size = data.length;
        Memory toWrite = new Memory(size);

        for(int i = 0; i < size; i++) {
            toWrite.setByte(i, data[i]);
        }

        return kernel32.WriteProcessMemory(new WinNT.HANDLE(process), address, toWrite, size, null);
    }

}