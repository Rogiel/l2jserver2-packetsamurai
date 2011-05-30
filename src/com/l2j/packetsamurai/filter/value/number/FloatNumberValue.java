package com.l2j.packetsamurai.filter.value.number;

import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public abstract class FloatNumberValue extends NumberValue
{
    public abstract double getFloatValue(DataStructure dp);
}