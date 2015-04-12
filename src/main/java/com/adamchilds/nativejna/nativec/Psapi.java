package com.adamchilds.nativejna.nativec;

import com.google.common.collect.Lists;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

import java.util.List;

/**
 * The process status application programming interface (PSAPI) is a helper library that makes it easier for you to
 * obtain information about processes and device drivers.
 *
 * <p>See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms684884(v=vs.85).aspx</p>
 */
public interface Psapi extends StdCallLibrary {
    Psapi INSTANCE = (Psapi) Native.loadLibrary("Psapi", Psapi.class);

    /**
     * Retrieves the process identifier for each process object in the system.
     *
     * <p>See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms682629(v=vs.85).aspx</p>
     *
     * @param pProcessIds A pointer to an array that receives the list of process identifiers.
     * @param cb The size of the pProcessIds array, in bytes.
     * @param pBytesReturned The number of bytes returned in the pProcessIds array.
     * @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero.
     */
    boolean EnumProcesses(int[] pProcessIds, int cb, IntByReference pBytesReturned);

    /**
     * Retrieves a handle for each module in the specified process.
     *
     * <p>See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms682631(v=vs.85).aspx</p>
     *
     * @param hProcess A handle to the process.
     * @param lphModule An array that receives the list of module handles.
     * @param cb The size of the lphModule array, in bytes.
     * @param lpcbNeededs The number of bytes required to store all module handles in the lphModule array.
     * @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero.
     */
    boolean EnumProcessModules(Pointer hProcess, Pointer[] lphModule, int cb, IntByReference lpcbNeededs);

    /**
     *
     * @param hProcess
     * @param hModule
     * @param lpBaseName
     * @param nSize
     * @return
     */
    int GetModuleBaseNameW(Pointer hProcess, Pointer hModule, byte[] lpBaseName, int nSize);

    /**
     *
     * @param hProcess
     * @param hModule
     * @param lpImageFileName
     * @param nSize
     * @return
     */
    int GetModuleFileNameExA(Pointer hProcess, Pointer hModule, byte[] lpImageFileName, int nSize);

    /**
     *
     * @param hProcess
     * @param hModule
     * @param lpmodinfo
     * @param cb
     * @return
     */
    boolean GetModuleInformation(Pointer hProcess, Pointer hModule, LPMODULEINFO lpmodinfo, int cb);

    /**
     *
     * @param hProcess
     * @param lpImageFileName
     * @param nSize
     * @return
     */
    int GetProcessImageFileNameA(Pointer hProcess, byte[] lpImageFileName, int nSize);

    /**
     *
     * @param Process
     * @param ppsmemCounters
     * @param cb
     * @return
     */
    boolean GetProcessMemoryInfo(Pointer Process, PPROCESS_MEMORY_COUNTERS ppsmemCounters, int cb);

    /**
     *
     */
    class LPMODULEINFO extends Structure {
        public int lpBaseOfDll; //FIXME Pointer
        public int  SizeOfImage;
        public Pointer EntryPoint;

        protected List getFieldOrder() {
            return Lists.newArrayList("lpBaseOfDll", "SizeOfImage", "EntryPoint");
        }
    }

    /**
     *
     */
    class PPROCESS_MEMORY_COUNTERS extends Structure {
        public int cb;
        public int PageFaultCount;
        public int PeakWorkingSetSize;
        public int WorkingSetSize;
        public int QuotaPeakPagedPoolUsage;
        public int QuotaPagedPoolUsage;
        public int QuotaPeakNonPagedPoolUsage;
        public int QuotaNonPagedPoolUsage;
        public int PagefileUsage;
        public int PeakPagefileUsage;

        protected List getFieldOrder() {
            return Lists.newArrayList("cb", "PageFaultCount", "PeakWorkingSetSize", "WorkingSetSize",
                    "QuotaPeakPagedPoolUsage", "QuotaPagedPoolUsage", "QuotaPeakNonPagedPoolUsage",
                    "QuotaNonPagedPoolUsage", "PagefileUsage", "PeakPagefileUsage");
        }
    }

}