package com.adamchilds.nativejna.process;

import com.adamchilds.nativejna.process.service.ProcessService;
import com.adamchilds.nativejna.process.service.impl.AbstractProcessServiceImpl;
import com.adamchilds.nativejna.process.service.impl.LinuxProcessServiceImpl;
import com.adamchilds.nativejna.process.service.impl.MacProcessServiceImpl;
import com.adamchilds.nativejna.process.service.impl.WindowsProcessServiceImpl;
import com.sun.jna.Platform;

/**
 *
 */
public class ProcessUtil {

    /**
     * Holds constants related to the Windows operating system.
     */
    public static class WINDOWS {
        public static final int PROCESS_QUERY_INFORMATION = 0x0400;
        public static final int PROCESS_VM_READ = 0x0010;
        public static final int PROCESS_VM_WRITE = 0x0020;
        public static final int PROCESS_VM_OPERATION = 0x0008;
        public static final int PROCESS_VM_ALL = 0x0010|0x0020|0x0008;
        public static final int PROCESS_VM_READ_WRITE = 0x0010|0x0020;
    }

    /**
     * Finds the correct {@link ProcessService} to use based on the current OS type.
     *
     * @return the correct {@link ProcessService} to use based on the OS
     */
    public static AbstractProcessServiceImpl getProcessService() {
        if (Platform.isWindows()) {
            return new WindowsProcessServiceImpl();
        } else if (Platform.isMac()) {
            return new MacProcessServiceImpl();
        } else if (Platform.isLinux()) {
            return new LinuxProcessServiceImpl();
        }

        return new WindowsProcessServiceImpl();
    }

}