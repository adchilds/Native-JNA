package com.adamchilds.nativejna.nativec.windows;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * Interface into Window's memory management, input/output operations and interrupts.
 *
 * @author Adam Childs
 * @since 1.0
 */
public interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {

    /**
     * Reads data from an area of memory in a specified process. The entire area to be read must be accessible or the
     * operation fails.
     *
     * <p>See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms680553(v=vs.85).aspx</p>
     *
     * @param hProcess A handle to the process with memory that is being read. The handle must have PROCESS_VM_READ
     *                 access to the process.
     * @param lpBaseAddress A pointer to the base address in the specified process from which to read. Before any data
     *                      transfer occurs, the system verifies that all data in the base address and memory of the
     *                      specified size is accessible for read access, and if it is not accessible the function
     *                      fails.
     * @param lpBuffer A pointer to a buffer that receives the contents from the address space of the specified process.
     * @param nSize The number of bytes to be read from the specified process.
     * @param lpNumberOfBytesRead A pointer to a variable that receives the number of bytes transferred into the
     *                            specified buffer. If lpNumberOfBytesRead is <b>NULL</b>, the parameter is ignored.
     * @return If the function succeeds, the return value is nonzero. If the function fails, the return value is 0
     *         (zero). The function fails if the requested read operation crosses into an area of the process that is
     *         inaccessible.
     */
    boolean ReadProcessMemory(HANDLE hProcess, long lpBaseAddress, Pointer lpBuffer, int nSize,
                              IntByReference lpNumberOfBytesRead);

    /**
     * Writes data to an area of memory in a specified process. The entire area to be written to must be accessible or
     * the operation fails.
     *
     * <p>See: https://msdn.microsoft.com/en-us/library/windows/desktop/ms681674(v=vs.85).aspx</p>
     *
     * @param hProcess A handle to the process memory to be modified. The handle must have PROCESS_VM_WRITE and
     *                 PROCESS_VM_OPERATION access to the process.
     * @param lpBaseAddress A pointer to the base address in the specified process to which data is written. Before
     *                      data transfer occurs, the system verifies that all data in the base address and memory of
     *                      the specified size is accessible for write access, and if it is not accessible, the
     *                      function fails.
     * @param lpBuffer A pointer to the buffer that contains data to be written in the address space of the specified
     *                 process.
     * @param nSize The number of bytes to be written to the specified process.
     * @param lpNumberOfBytesWritten A pointer to a variable that receives the number of bytes transferred into the
     *                               specified process. This parameter is optional. If lpNumberOfBytesWritten is
     *                               <b>NULL</b>, the parameter is ignored.
     * @return If the function succeeds, the return value is nonzero. If the function fails, the return value is 0
     *         (zero). The function fails if the requested write operation crosses into an area of the process that is
     *         inaccessible.
     */
    boolean WriteProcessMemory(HANDLE hProcess, long lpBaseAddress, Pointer lpBuffer, int nSize,
                               IntByReference lpNumberOfBytesWritten);

}