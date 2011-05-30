package com.l2j.packetsamurai.utils;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.l2j.packetsamurai.PacketSamurai;
import com.l2j.packetsamurai.parser.datatree.ValuePart;
import com.l2j.packetsamurai.session.DataPacket;

public class NpcWalkExporter {
	private FastList<DataPacket> packets;
	private FastMap<Long, FastList<Move>> moves;
	private FastMap<Long, Integer> objectList;
	private String sessionName;

	public NpcWalkExporter(List<DataPacket> packets, String sessionName) {
		this.packets = new FastList<DataPacket>(packets);
		this.sessionName = sessionName;

		this.moves = new FastMap<Long, FastList<Move>>();
		this.objectList = new FastMap<Long, Integer>();
	}

	public void parse() {
		String filename = "npcwalker_" + sessionName + ".xml";
		Long start = System.currentTimeMillis();

		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();
			Element root = doc.createElement("spawns");
			doc.appendChild(root);

			// We need all SM_MOVE packets and objectid<->npcid translation table
			// for convenience
			for (DataPacket packet : packets) {
				String packetName = packet.getName();
				if ("SM_NPC_INFO".equals(packetName)) {
					objectList.put(Long.parseLong(packet.getValuePartList().get(4).readValue()), Integer.parseInt(packet
							.getValuePartList().get(5).readValue()));
				} else if ("SM_MOVE".equals(packetName)) {
					Move movement = new Move();
					long objectId = -1;
					FastList<ValuePart> valuePartList = new FastList<ValuePart>(packet.getValuePartList());
					for (ValuePart valuePart : valuePartList) {
						String partName = valuePart.getModelPart().getName();
						if ("x".equals(partName))
							movement.x = Float.parseFloat(valuePart.readValue());
						else if ("y".equals(partName))
							movement.y = Float.parseFloat(valuePart.readValue());
						else if ("objectId".equals(partName))
							objectId = Long.parseLong(valuePart.readValue());
					}
					if (!moves.containsKey(objectId))
						moves.put(objectId, new FastList<Move>());
					moves.get(objectId).add(movement);
				}

				// Now we need to generalize the movements
				FastList<Move> generalized = new FastList<Move>();
				for (FastMap.Entry<Long, FastList<Move>> e = moves.head(), end = moves.tail(); (e = e.getNext()) != end;) {
					int lastDet = 0;
					for (int i = 1; i <= e.getValue().size(); i++) {
						Move lst = e.getValue().get(i - 1);
						Move cur = e.getValue().get(i);
						int det = (int) (lst.x * cur.y - cur.x * lst.y);
						if (lastDet == 0 || lastDet > det - 2 || lastDet < det + 2)
							generalized.add(lst);
					}
				}

				// TODO: Write XML
			}

			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
			serializer.transform(new DOMSource(doc), new StreamResult(new File(filename)));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		PacketSamurai.getUserInterface().log(
				"The npc walkdata has been written in " + ((float) (System.currentTimeMillis() - start) / 1000) + "s");
	}

	class Move {
		float x;
		float y;
	}
}