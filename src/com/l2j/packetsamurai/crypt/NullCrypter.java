package com.l2j.packetsamurai.crypt;


import com.l2j.packetsamurai.protocol.Protocol;
import com.l2j.packetsamurai.protocol.protocoltree.PacketFamilly.PacketDirection;

/**
 * 
 * @author Gilles Duboscq
 *
 */
public class NullCrypter implements ProtocolCrypter
{

	public boolean decrypt(byte[] raw, PacketDirection dir)
	{
		return true;
	}

    public void setProtocol(Protocol protocol)
    {
        
    }
}