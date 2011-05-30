package com.l2j.packetsamurai.crypt;


import com.l2j.packetsamurai.protocol.Protocol;
import com.l2j.packetsamurai.protocol.protocoltree.PacketFamilly.PacketDirection;

/**
 * This interface is used to interface with all the different protocol encryptions
 * @author Gilles Duboscq
 *
 */
public interface ProtocolCrypter
{
	/**
	 * this decrypts the packet, the data provided must be only one full packet
	 * @param raw
	 * @return
	 */
	public boolean decrypt(byte[] raw, PacketDirection dir);
    
    public void setProtocol(Protocol protocol);
}