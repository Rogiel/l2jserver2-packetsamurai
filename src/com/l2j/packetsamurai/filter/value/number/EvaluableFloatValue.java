package com.l2j.packetsamurai.filter.value.number;


import com.l2j.packetsamurai.filter.value.Value;
import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public class EvaluableFloatValue extends FloatNumberValue
{
    private String _accessStr;
    public EvaluableFloatValue(String str)
    {
        _accessStr = str;
    }

    @Override
    public double getFloatValue(DataStructure dp)
    {
        Object obj = Value.getObjectFromAccessString(_accessStr);
        if(!(obj instanceof Float))
            throw new IllegalStateException("Malformed filter, the expression doesnt return a float for an EvaluableFloatValue.");
        return (Float) obj;
    }
    
}