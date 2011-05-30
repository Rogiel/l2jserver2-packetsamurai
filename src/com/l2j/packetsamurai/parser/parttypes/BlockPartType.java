package com.l2j.packetsamurai.parser.parttypes;


import com.l2j.packetsamurai.parser.PartType;
import com.l2j.packetsamurai.parser.datatree.DataTreeNodeContainer;
import com.l2j.packetsamurai.parser.datatree.ValuePart;
import com.l2j.packetsamurai.parser.formattree.Part;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public class BlockPartType extends PartType
{
    public enum blockType
    {
        forblock,
        switchblock,
        block
    }

    private blockType _type;

    public BlockPartType(String name, blockType type)
    {
        super(name);
        _type = type;
    }

    @Override
    public ValuePart getValuePart(DataTreeNodeContainer parent, Part part)
    {
        return null;
    }

    @Override
    public boolean isBlockType()
    {
        return true;
    }

    @Override
    public boolean isReadableType()
    {
        return false;
    }
    
    public blockType getType()
    {
        return _type;
    }

    @Override
    public int getTypeByteNumber()
    {
        return -1;
    }

}
