package com.l2j.packetsamurai.protocol.protocoltree;

import java.util.List;


import com.l2j.packetsamurai.Util;
import com.l2j.packetsamurai.parser.PartType;
import com.l2j.packetsamurai.parser.PartTypeManager;
import com.l2j.packetsamurai.parser.formattree.Format;
import com.l2j.packetsamurai.parser.formattree.Part;
import com.l2j.packetsamurai.protocol.Protocol;

import javolution.text.TextBuilder;
import javolution.util.FastList;

/**
 * This class represents a packet format in the protocol definition tree.
 * It is a container for {@link PacketParts PacketParts}
 * @author Gilles Duboscq
 * TODO: find a way to ensure part IDs unicity
 */
public class PacketFormat extends ProtocolNode
{
    private Format _format;
	private String _name;
	private String _opStr;
	private String _partStr;
    private Protocol _containingProtocol;
	
	public PacketFormat(int id, String name)
	{
		super(id);
		_name = name;
        _format = new Format(this);
	}
	
	public PacketFormat()
	{
        _format = new Format(this);
	}
    
    public Format getDataFormat()
    {
        return _format;
    }
    
    public void setContainingProtocol(Protocol p)
    {
        _containingProtocol = p;
    }
    
    public Protocol getContainingProtocol()
    {
        return _containingProtocol;
    }
	
	public String getName()
	{
		return _name;
	}

	public String getOpcodeStr()
	{
		if(_opStr == null)
		{
			TextBuilder tb = new TextBuilder();
			boolean first = true;
			for(int id : super.getIDs())
			{
				if(!first)
				{
					tb.append(":");
				}
				tb.append(Util.zeropad(Integer.toHexString(id), 2).toUpperCase());
				first = false;
			}
			_opStr = tb.toString();
		}
		return _opStr;
	}
	
	public String getPartsStr()
	{
		if (_partStr == null)
		{
			_partStr = new String();
			TextBuilder tb = new TextBuilder();
			tb.append("(");
			tb.append(Util.makeFormatStringPartType(super.getIdParts()));
			tb.append(") ");
			tb.append(Util.makeFormatStringPart(this.getDataFormat().getMainBlock().getParts()));
			_partStr = tb.toString();
		}
		return _partStr;
	}

	public String toString()
	{
		return getOpcodeStr()+" "+_name;
	}
	
	/**
	 * format : (c) chdfQSsbx[ddd]
	 *           ^id        ^for
	 * id is mandatory, spaces are allowed
	 * @param str
	 * @param ids
	 * @return
	 */
	public static PacketFormat generateFromString(String str, List<Integer> ids)
	{
		PacketFormat pf = new PacketFormat();
		str = str.trim();
		int i = 0;
		if(str.charAt(i) == '(')
		{
			List<PartType> idparts = new FastList<PartType>();
			while(str.charAt(i) != ')')
			{
				if(str.charAt(i) == ' ')
				{
					i++;
					if(i >= str.length())
						return null;
				}
				PartType type = PartTypeManager.getInstance().getType(str.substring(i, i+1));
				if(type != null)
					idparts.add(type);
				else
					return null;
				i++;
				if(i >= str.length())
					return null;
			}
			pf.addIdPartsAtBegining(idparts, ids);
		}
		else
			return null;
		while(i < str.length())
		{
			if(str.charAt(i) != '[')
			{
				i++;
				for(Part part : parseForString(str.substring(i, str.substring(i).indexOf(']'))))
				{
					pf.getDataFormat().getMainBlock().addPart(part);
					while(str.charAt(i) == ' ')
					{
						i++;
						if(i >= str.length())
							return null;
					}
					i++;
					if(i >= str.length())
						return null;
				}
			}
			else if(str.charAt(i) == ' ')
			{
				i++;
				if(i >= str.length())
					return null;
			}
			else
			{
			    PartType type = PartTypeManager.getInstance().getType(str.substring(i, i+1));
				if(type != null)
					pf.getDataFormat().getMainBlock().addPart(new Part(type,-1,"","",""));
				else
					return null;
				i++;
				if(i >= str.length())
					return null;
			}
		}
		return pf;
	}
	
	private static List<Part> parseForString(String str)
	{
		int i = 0;
		List<Part> parts = new FastList<Part>();
		if(str.charAt(i) != '[')
		{
			i++;
			for(Part part : parseForString(str.substring(i, str.substring(i).indexOf(']'))))
			{
				parts.add(part);
				while(str.charAt(i) == ' ')
				{
					i++;
					if(i >= str.length())
						return null;
				}
				i++;
				if(i >= str.length())
					return null;
			}
		}
		else if(str.charAt(i) == ' ')
		{
			i++;
			if(i >= str.length())
				return null;
		}
		else
		{
		    PartType type = PartTypeManager.getInstance().getType(str.substring(i, i+1));
			if(type != null)
				parts.add(new Part(type,-1,"","",""));
			else
				return null;
			i++;
			if(i >= str.length())
				return null;
		}
		return parts;
	}
	
	public static int countIdPartsInString(String str)
	{
		str = str.trim();
		int i = 0;
		int count = 0;
		if(str.charAt(i) == '(')
		{
			i++;
			while(str.charAt(i) != ')')
			{
				if(str.charAt(i) != ' ')
				{
					if(PartTypeManager.getInstance().getType(str.substring(i, i+1)) == null)
					{
						return 0;
					}
					count++;
				}
				i++;
			}
		}
		return count;
	}

	public static List<PartType> getIdPartsInString(String str)
	{
		FastList<PartType> list = new FastList<PartType>();
		str = str.trim();
		int i = 0;
		int count = 0;
		if(str.charAt(i) == '(')
		{
			i++;
			while(str.charAt(i) != ')')
			{
				if(str.charAt(i) != ' ')
				{
                    PartType type = PartTypeManager.getInstance().getType(str.substring(i, i+1));
					if(type == null)
					{
						return null;
					}
                    list.add(type);
					count++;
				}
				i++;
			}
		}
		return list;
	}
}