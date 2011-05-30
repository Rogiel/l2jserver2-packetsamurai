package com.l2j.packetsamurai.filter.booleanoperator;

import java.util.List;


import com.l2j.packetsamurai.filter.Condition;
import com.l2j.packetsamurai.parser.DataStructure;


/**
 * 
 * @author Gilles Duboscq
 *
 */
public interface BooleanOperator
{
    public boolean execute(List<Condition> conditions, DataStructure dp);
}