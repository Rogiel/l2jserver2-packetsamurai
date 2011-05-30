package com.l2j.packetsamurai.filter.value.string;

import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public class ConstantStringValue extends StringValue
{
    public String _value;
    
    public ConstantStringValue(String val)
    {
        _value = val;
    }

    @Override
    public String getStringValue(DataStructure dp)
    {
        return _value;
    }
    
}