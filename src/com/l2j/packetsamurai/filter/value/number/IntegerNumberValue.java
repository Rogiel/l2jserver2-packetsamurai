package com.l2j.packetsamurai.filter.value.number;

import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public abstract class IntegerNumberValue extends NumberValue
{
    public abstract long getIntegerValue(DataStructure dp);
}