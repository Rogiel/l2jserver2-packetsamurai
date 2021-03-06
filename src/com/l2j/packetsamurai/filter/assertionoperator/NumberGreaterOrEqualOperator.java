package com.l2j.packetsamurai.filter.assertionoperator;


import com.l2j.packetsamurai.filter.value.number.FloatNumberValue;
import com.l2j.packetsamurai.filter.value.number.IntegerNumberValue;
import com.l2j.packetsamurai.filter.value.number.NumberValue;
import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public class NumberGreaterOrEqualOperator implements NumberAssertionOperator
{

    public boolean execute(NumberValue value1, NumberValue value2, DataStructure dp)
    {
        if(value1 instanceof IntegerNumberValue && value2 instanceof IntegerNumberValue)
        {
            return ((IntegerNumberValue)value1).getIntegerValue(dp) >= ((IntegerNumberValue)value2).getIntegerValue(dp);
        }
        if(value1 instanceof FloatNumberValue && value2 instanceof IntegerNumberValue)
        {
            return ((FloatNumberValue)value1).getFloatValue(dp) >= ((IntegerNumberValue)value2).getIntegerValue(dp);
        }
        if(value1 instanceof IntegerNumberValue && value2 instanceof FloatNumberValue)
        {
            return ((IntegerNumberValue)value1).getIntegerValue(dp) >= ((FloatNumberValue)value2).getFloatValue(dp);
        }
        if(value1 instanceof FloatNumberValue && value2 instanceof FloatNumberValue)
        {
            return ((FloatNumberValue)value1).getFloatValue(dp) >= ((FloatNumberValue)value2).getFloatValue(dp);
        }
        return false;
    }

}