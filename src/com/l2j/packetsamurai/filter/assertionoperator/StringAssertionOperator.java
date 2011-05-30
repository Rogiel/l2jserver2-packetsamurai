package com.l2j.packetsamurai.filter.assertionoperator;


import com.l2j.packetsamurai.filter.value.string.StringValue;
import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public interface StringAssertionOperator extends AssertionOperator
{
    public boolean execute(StringValue value1, StringValue value2, DataStructure dp);
}