package com.adamchilds.nativejna.process.service;

import java.util.List;

/**
 * A simple service for interfacing with native OS processes.
 *
 * @author Adam Childs
 * @since 1.0
 */
public interface ProcessService {

    /**
     * Returns a {@link List} of {@link Integer}s that represent the IDs to all running processes on the system.
     *
     * @return a {@link List} of integers representing the ID's for all running processes on the current system.
     */
    List<Integer> getAllProcessIds();

}