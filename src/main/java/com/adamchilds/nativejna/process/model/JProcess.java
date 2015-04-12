package com.adamchilds.nativejna.process.model;

import com.sun.jna.Pointer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a single native OS process.
 *
 * @author Adam Childs
 */
public class JProcess {

    private int id;
    private String name;
    private Pointer pointer;

    public JProcess(int id) {
        this.id = id;
    }

    public JProcess(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pointer getPointer() {
        return pointer;
    }

    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}