package com.l2j.packetsamurai.crypt;

import java.io.IOException;
import java.util.Arrays;

import com.l2j.packetsamurai.PacketSamurai;
import com.l2j.packetsamurai.parser.datatree.ValuePart;
import com.l2j.packetsamurai.protocol.Protocol;
import com.l2j.packetsamurai.protocol.protocoltree.PacketFamilly.PacketDirection;
import com.l2j.packetsamurai.session.DataPacket;

public class L2LoginCrypter implements ProtocolCrypter
{
	private Protocol protocol;
    private static byte[] STATIC_BLOWFISH_KEY = 
    {
        (byte) 0x6b, (byte) 0x60, (byte) 0xcb, (byte) 0x5b,
        (byte) 0x82, (byte) 0xce, (byte) 0x90, (byte) 0xb1,
        (byte) 0xcc, (byte) 0x2b, (byte) 0x6c, (byte) 0x55,
        (byte) 0x6c, (byte) 0x6c, (byte) 0x6c, (byte) 0x6c
    };
    
    private NewCrypt _crypt;
    private NewCrypt _initcrypt = new NewCrypt(STATIC_BLOWFISH_KEY);
	
	public boolean decrypt(byte[] raw, PacketDirection dir)
	{
        try
        {
            if(_crypt == null)
            {
                byte[] potentialInit = Arrays.copyOf(raw, raw.length);
                _initcrypt.decrypt(potentialInit);
                NewCrypt.decXORPass(potentialInit);

                DataPacket packet = new DataPacket(Arrays.copyOf(potentialInit, potentialInit.length), dir, 0, protocol);
                if(dir == PacketDirection.serverPacket && "SM_INIT".equals(packet.getName()))
                {
                    ValuePart part = (ValuePart) packet.getRootNode().getPartByName("Blowfish key");
                    if(part == null)
                    {
                    	PacketSamurai.getUserInterface().log("Check your protocol there is no part called 'Blowfish key' which is required in packet 0x00 of the LS protocol.");
                        return false;
                    }
                    _crypt = new NewCrypt(part.getBytes());
                    System.arraycopy(potentialInit, 0, raw, 0, raw.length);
                    return true; // no checksum here
                }
               	PacketSamurai.getUserInterface().log("No key was ready to read Packet, there should have been an Init packet before");
                return false;
            }
            if(dir == PacketDirection.serverPacket)
            {
                _crypt.decrypt(raw);
                if(!_crypt.checksum(raw))
                	PacketSamurai.getUserInterface().log("LoginCrypter : Wrong checksum (packet id: "+raw[0]+", dir:"+dir+")");
                return true;
            }
            _crypt.decrypt(raw);
            if(!_crypt.checksum(raw))
            	PacketSamurai.getUserInterface().log("LoginCrypter : Wrong checksum (packet id: "+raw[0]+", dir:"+dir+")");
            return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void setProtocol(Protocol protocol)
	{
		this.protocol = protocol;
	}
}
