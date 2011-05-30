package com.l2j.packetsamurai.filter.value.string;


import com.l2j.packetsamurai.filter.value.Value;
import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public abstract class StringValue extends Value
{
    public abstract String getStringValue(DataStructure dp);
}