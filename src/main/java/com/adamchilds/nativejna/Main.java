package com.adamchilds.nativejna;

import com.adamchilds.nativejna.process.ProcessUtil;
import com.adamchilds.nativejna.process.model.JProcess;
import com.adamchilds.nativejna.process.service.impl.AbstractProcessServiceImpl;
import com.sun.jna.Memory;

/**
 * Main entry point into the program.
 *
 * @author Adam Childs
 */
public class Main {

    private static AbstractProcessServiceImpl processService = ProcessUtil.getProcessService();

    private static final String PROCESS_NAME = "csgo.exe";
    private static final long playerBaseAddress = 0x03FE296C;
    private static final int[] playerOffsets = new int[] {
            0x0070
    };

    private static final long botBaseAddress = 0x74EC46D8;
    private static final int[] botOffsets = new int[] {
            0
    };

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        JProcess process = processService.findProcessByExecutableName(PROCESS_NAME);

        // Exit if we don't find the process
        if (process == null) {
            System.out.println("Process with name=[" + PROCESS_NAME + "] not found.");

            System.exit(0);
        }

        long playerHealthAddress = processService.findDynamicAddress(process.getPointer(), playerOffsets, playerBaseAddress);
        long botHealthAddress = processService.findDynamicAddress(process.getPointer(), botOffsets, botBaseAddress);

        while (true) {
            Memory currentPlayerHealth = processService.readMemory(process.getPointer(), playerHealthAddress, 4);
            Memory currentBotHealth = processService.readMemory(process.getPointer(), botHealthAddress, 4);

            System.out.println("player=[" + currentPlayerHealth.getInt(0) + "]");
            System.out.println("bot=[" + currentBotHealth.getInt(0) + "]");
            System.out.println();

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
/*
        processService.writeMemory(process.getPointer(), dynAddress, new byte[]{0x22,0x22,0x22,0x22});
*/
    }

}