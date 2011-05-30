/**
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2j.packetsamurai.crypt;

import java.util.Arrays;

import com.l2j.packetsamurai.PacketSamurai;
import com.l2j.packetsamurai.parser.datatree.ValuePart;
import com.l2j.packetsamurai.protocol.Protocol;
import com.l2j.packetsamurai.protocol.protocoltree.PacketFamilly.PacketDirection;
import com.l2j.packetsamurai.session.DataPacket;

/**
 * @author -Nemesiss-
 * @updated Sarynth
 */
public class L2Crypter implements ProtocolCrypter {
	private Protocol protocol;
	byte[] clientPacketkey;
	byte[] serverPacketkey;

	public boolean decrypt(byte[] raw, PacketDirection dir) {
		if (dir == PacketDirection.clientPacket) {
			if (clientPacketkey == null)
				return false;
			decode(raw, clientPacketkey);
		} else {
			if (clientPacketkey == null) {
				decodeOpcodec(raw);
				return searchKey(Arrays.copyOf(raw, raw.length), dir);
			}
			decode(raw, serverPacketkey);
			decodeOpcodec(raw);
		}
		return true;
	}

	private boolean searchKey(byte[] raw, PacketDirection dir) {
		DataPacket packet = new DataPacket(raw, dir, 0, protocol);

		if (dir == PacketDirection.serverPacket
				&& "SM_KEY".equals(packet.getName())) {
			ValuePart part = (ValuePart) packet.getRootNode().getPartByName(
					"key");
			if (part == null) {
				PacketSamurai
						.getUserInterface()
						.log("Check your protocol there is no part called 'key' which is required in packet 0x49 SM_KEY of the GS protocol.");
				return false;
			}
			clientPacketkey = Arrays.copyOf(part.getBytes(), 16);
			// the last 8 bytes are static
			clientPacketkey[8] = (byte) 0xc8;
			clientPacketkey[9] = (byte) 0x27;
			clientPacketkey[10] = (byte) 0x93;
			clientPacketkey[11] = (byte) 0x01;
			clientPacketkey[12] = (byte) 0xa1;
			clientPacketkey[13] = (byte) 0x6c;
			clientPacketkey[14] = (byte) 0x31;
			clientPacketkey[15] = (byte) 0x97;

			serverPacketkey = Arrays.copyOf(clientPacketkey,
					clientPacketkey.length);
			return true;
		}
		PacketSamurai.getUserInterface().log("No key found...");
		return false;
	}

	public void decode(byte[] raw, byte[] key) {
		System.out.println("Key: " + key.length);
		System.out.println("Raw: " + raw.length);
		int temp = 0;
		for (int i = 0; i < raw.length; i++) {
			int temp2 = raw[i] & 0xFF;
			raw[i] = (byte) (temp2 ^ key[i & 15] ^ temp);
			temp = temp2;
		}

		int old = key[8] & 0xff;
		old |= key[9] << 8 & 0xff00;
		old |= key[10] << 0x10 & 0xff0000;
		old |= key[11] << 0x18 & 0xff000000;

		old += raw.length;

		key[8] = (byte) (old & 0xff);
		key[9] = (byte) (old >> 0x08 & 0xff);
		key[10] = (byte) (old >> 0x10 & 0xff);
		key[11] = (byte) (old >> 0x18 & 0xff);
	}

	private final boolean validatePacket(byte[] raw, byte code) {
		return true;
	}

	private final void decodeOpcodec(byte[] raw) {
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
}
