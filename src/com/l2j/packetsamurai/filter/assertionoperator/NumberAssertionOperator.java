package com.l2j.packetsamurai.filter.assertionoperator;


import com.l2j.packetsamurai.filter.value.number.NumberValue;
import com.l2j.packetsamurai.parser.DataStructure;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public interface NumberAssertionOperator extends AssertionOperator
{
    public boolean execute(NumberValue value1, NumberValue value2, DataStructure dp);
}