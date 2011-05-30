package com.l2j.packetsamurai.filter.assertionoperator;


import com.l2j.packetsamurai.filter.value.string.StringValue;
import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public class StringDifferantOperator implements StringAssertionOperator
{

    public boolean execute(StringValue value1, StringValue value2, DataStructure dp)
    {
        if(value1.getStringValue(dp) == null)
            return (value2.getStringValue(dp) != null);
        return !value1.getStringValue(dp).equals(value2.getStringValue(dp));
    }
}