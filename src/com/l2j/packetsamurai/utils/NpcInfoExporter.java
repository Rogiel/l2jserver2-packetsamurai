package com.l2j.packetsamurai.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javolution.util.FastList;

import com.l2j.packetsamurai.PacketSamurai;
import com.l2j.packetsamurai.parser.datatree.ValuePart;
import com.l2j.packetsamurai.session.DataPacket;

/**
 * @author ATracer
 */
public class NpcInfoExporter {
	private List<DataPacket> packets;
	private String sessionName;
	private FastList<NpcInfo> npcInfoList = new FastList<NpcInfo>();

	public NpcInfoExporter(List<DataPacket> packets, String sessionName) {
		super();
		this.packets = packets;
		this.sessionName = sessionName;
	}

	public void parse() {
		String fileName = "npcinfo_" + sessionName + ".txt";

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

			for (DataPacket packet : packets) {
				String name = packet.getName();
				if ("SM_NPC_INFO".equals(name)) {
					List<ValuePart> valuePartList = packet.getValuePartList();
					NpcInfo npc = new NpcInfo();

					for (ValuePart valuePart : valuePartList) {
						String partName = valuePart.getModelPart().getName();
						if ("npcId".equals(partName)) {
							npc.npcId = Integer.parseInt(valuePart.readValue());
						} else if ("titleId".equals(partName)) {
							npc.titleId = Integer.parseInt(valuePart.readValue());
						} else if ("nameId".equals(partName)) {
							npc.nameId = Integer.parseInt(valuePart.readValue());
						} else if ("npcMaxHP".equals(partName)) {
							npc.npcMaxHp = Integer.parseInt(valuePart.readValue());
						} else if ("npclevel".equals(partName)) {
							npc.npcLevel = Byte.parseByte(valuePart.readValue());
						} else if ("npcTemplateHeight".equals(partName)) {
							npc.npcTemplateHeight = Float.parseFloat(valuePart.readValue());
						}
					}
					npcInfoList.add(npc);
				}

			}

			out.write("npcId\ttitleId\tnameId\tnpcMaxHp\tnpcLevel\tnpcTemplateHeight\n");

			for(NpcInfo npc : npcInfoList) {
				StringBuilder sb = new StringBuilder();
				sb.append(npc.npcId);
				sb.append("\t");
				sb.append(npc.titleId);
				sb.append("\t");
				sb.append(npc.nameId);
				sb.append("\t");
				sb.append(npc.npcMaxHp);
				sb.append("\t");
				sb.append(npc.npcLevel);
				sb.append("\t");
				sb.append(npc.npcTemplateHeight);
				sb.append("\t");
				sb.append("\n");
				out.write(sb.toString());
			}

			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PacketSamurai.getUserInterface().log("The npc infos have been written");
	}

	class NpcInfo {
		int npcId;
		int titleId;
		int nameId;
		int npcMaxHp;
		byte npcLevel;
		float npcTemplateHeight;
	}
}
