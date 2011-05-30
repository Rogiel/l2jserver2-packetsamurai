package com.l2j.packetsamurai.parser.parttypes;

import com.l2j.packetsamurai.parser.datatree.DataTreeNodeContainer;
import com.l2j.packetsamurai.parser.datatree.IntBCValuePart;
import com.l2j.packetsamurai.parser.datatree.ValuePart;
import com.l2j.packetsamurai.parser.formattree.Part;

/**
 * @author -Nemesiss-
 *
 */
public class IntBSPartType extends IntPartType
{
	public IntBSPartType(String name, intType type)
	{
		super(name, type);
	}

    @Override
    public ValuePart getValuePart(DataTreeNodeContainer parent, Part part)
    {
        return new IntBCValuePart(parent, part, _type);
    }

}
