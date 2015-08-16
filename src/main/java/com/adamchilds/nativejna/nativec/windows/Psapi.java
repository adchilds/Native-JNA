package com.adamchilds.nativejna.nativec.windows;

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
 *
 * @author Adam Childs
 * @since 1.0
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
     * Retrieves the base name of the specified module.
     *
     * <p>See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms683196(v=vs.85).aspx</p>
     *
     * @param hProcess A handle to the process that contains the module. The handle must have the
     *                 PROCESS_QUERY_INFORMATION and PROCESS_VM_READ access rights.
     * @param hModule A handle to the module. If this parameter is NULL, this function returns the name of the file
     *                used to create the calling process.
     * @param lpBaseName A pointer to the buffer that receives the base name of the module. If the base name is longer
     *                   than maximum number of characters specified by the nSize parameter, the base name is truncated.
     * @param nSize The size of the lpBaseName buffer, in characters.
     * @return If the function succeeds, the return value specifies the length of the string copied to the buffer, in
     *         characters. If the function fails, the return value is zero.
     */
    int GetModuleBaseNameW(Pointer hProcess, Pointer hModule, byte[] lpBaseName, int nSize);

    /**
     * Retrieves the fully qualified path for the file containing the specified module.
     *
     * <p>See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms683198(v=vs.85).aspx</p>
     *
     * @param hProcess A handle to the process that contains the module. The handle must have the
     *                 PROCESS_QUERY_INFORMATION and PROCESS_VM_READ access rights. The GetModuleFileNameEx function
     *                 does not retrieve the path for modules that were loaded using the LOAD_LIBRARY_AS_DATAFILE flag.
     * @param hModule A handle to the module. If this parameter is NULL, GetModuleFileNameEx returns the path of the
     *                executable file of the process specified in hProcess.
     * @param lpImageFileName A pointer to a buffer that receives the fully qualified path to the module. If the size
     *                        of the file name is larger than the value of the nSize parameter, the function succeeds
     *                        but the file name is truncated and null-terminated.
     * @param nSize The size of the lpFilename buffer, in characters.
     * @return If the function succeeds, the return value specifies the length of the string copied to the buffer. If
     *         the function fails, the return value is zero.
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
     * Retrieves information about the memory usage of the specified process.
     *
     * <p>See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms683219(v=vs.85).aspx</p>
     *
     * @param Process A handle to the process. The handle must have the PROCESS_QUERY_INFORMATION or
     *                PROCESS_QUERY_LIMITED_INFORMATION access right and the PROCESS_VM_READ access right.
     * @param ppsmemCounters A pointer to the <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/ms684877(v=vs.85).aspx">PROCESS_MEMORY_COUNTERS</a>
     *                       or <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/ms684874(v=vs.85).aspx">PROCESS_MEMORY_COUNTERS_EX</a>
     *                       structure that receives information about the memory usage of the process.
     * @param cb The size of the ppsmemCounters structure, in bytes.
     * @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero.
     */
    boolean GetProcessMemoryInfo(Pointer Process, PPROCESS_MEMORY_COUNTERS ppsmemCounters, int cb);

    /**
     * Contains the module load address, size, and entry point.
     *
     * See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms684229(v=vs.85).aspx
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
     * Contains the memory statistics for a process.
     *
     * See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms684874(v=vs.85).aspx
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