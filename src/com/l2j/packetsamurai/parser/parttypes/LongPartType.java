package com.l2j.packetsamurai.parser.parttypes;


import com.l2j.packetsamurai.parser.PartType;
import com.l2j.packetsamurai.parser.datatree.DataTreeNodeContainer;
import com.l2j.packetsamurai.parser.datatree.LongValuePart;
import com.l2j.packetsamurai.parser.datatree.ValuePart;
import com.l2j.packetsamurai.parser.formattree.Part;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public class LongPartType extends PartType
{
    public LongPartType(String name)
    {
        super(name);
    }

    @Override
    public ValuePart getValuePart(DataTreeNodeContainer parent, Part part)
    {
        return new LongValuePart(parent, part);
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
