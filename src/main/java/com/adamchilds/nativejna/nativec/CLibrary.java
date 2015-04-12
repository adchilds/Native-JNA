package com.adamchilds.nativejna.nativec;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * Native C library.
 *
 * @author Adam Childs
 */
public interface CLibrary extends Library {
    CLibrary INSTANCE = (CLibrary) Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);

    void printf(String format, Object... args);

}