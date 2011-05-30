package com.l2j.packetsamurai.parser.parttypes;


import com.l2j.packetsamurai.parser.PartType;
import com.l2j.packetsamurai.parser.datatree.DataTreeNodeContainer;
import com.l2j.packetsamurai.parser.datatree.DoubleValuePart;
import com.l2j.packetsamurai.parser.datatree.ValuePart;
import com.l2j.packetsamurai.parser.formattree.Part;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public class DoublePartType extends PartType
{

    public DoublePartType(String name)
    {
        super(name);
    }

    @Override
    public ValuePart getValuePart(DataTreeNodeContainer parent, Part part)
    {
        return new DoubleValuePart(parent, part);
    }

    @Override
    public boolean isBlockType()
    {
        return false;
    }

    @Override
    public boolean isReadableType()
    {
        return true;
    }

    @Override
    public int getTypeByteNumber()
    {
        return 8;
    }
    
}