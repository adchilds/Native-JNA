package com.adamchilds.nativejna.nativec.common;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * The standard library for the C programming language, as specified in the ANSI C standard.
 *
 * See: https://msdn.microsoft.com/en-us/library/abx4dbyh.aspx or http://www.cplusplus.com/reference/clibrary/
 *
 * @author Adam Childs
 * @since 1.0
 */
public interface CLibrary extends Library {
    CLibrary INSTANCE = (CLibrary) Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);

    /**
     * Writes the C string pointed by format to the standard output (stdout). If format includes format specifiers
     * (subsequences beginning with %), the additional arguments following format are formatted and inserted in the
     * resulting string replacing their respective specifiers.
     *
     * @param format C string that contains the text to be written to stdout. It can optionally contain embedded format
     *               specifiers that are replaced by the values specified in subsequent additional arguments and
     *               formatted as requested.
     * @param args Depending on the format string, the function may expect a sequence of additional arguments, each
     *             containing a value to be used to replace a format specifier in the format string (or a pointer to a
     *             storage location, for n). There should be at least as many of these arguments as the number of values
     *             specified in the format specifiers. Additional arguments are ignored by the function.
     */
    void printf(String format, Object... args);

}