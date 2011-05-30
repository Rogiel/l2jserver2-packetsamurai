/**
 * 
 */
package com.l2j.packetsamurai.gui;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import javolution.util.FastTable;

import com.l2j.packetsamurai.gui.PacketTableRenderer.TooltipTable;
import com.l2j.packetsamurai.gui.images.IconsTable;
import com.l2j.packetsamurai.protocol.protocoltree.PacketFamilly.PacketDirection;
import com.l2j.packetsamurai.session.DataPacket;

/**
 * @author Ulysses R. Ribeiro
 * 
 */
@SuppressWarnings("serial")
class PacketTableModel extends AbstractTableModel implements TooltipTable {
	private static final String[] columnNames = { "S/C", "Opcode", "Time",
			"Length", "Name" };

	private FastTable<Object[]> _currentTable;

	public PacketTableModel() {
	}

	public void reinit(int size) {
		_currentTable = new FastTable<Object[]>(size);
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return (_currentTable == null ? 0 : _currentTable.size());
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		Object[] tableRow = _currentTable.get(row);
		if (tableRow != null)
			return tableRow[col];
		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return ImageIcon.class;
		return super.getColumnClass(columnIndex);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void addRow(DataPacket packet, long startTime) {
		ImageIcon icon = null;
		if (packet.getDirection() == PacketDirection.clientPacket) {
			if (packet.hasError()) {
				icon = IconsTable.ICON_FROM_CLIENT_ERROR;
			} else if (packet.hasWarning()) {
				icon = IconsTable.ICON_FROM_CLIENT_WARNING;
			} else {
				icon = IconsTable.ICON_FROM_CLIENT;
			}
		} else {
			if (packet.hasError()) {
				icon = IconsTable.ICON_FROM_SERVER_ERROR;
			} else if (packet.hasWarning()) {
				icon = IconsTable.ICON_FROM_SERVER_WARNING;
			} else {
				icon = IconsTable.ICON_FROM_SERVER;
			}
		}
		String opcode = null;
		if (packet.getPacketFormat() != null) {
			opcode = packet.getPacketFormat().getOpcodeStr();
		} else {
			opcode = "-";
		}

		String time = "+" + (packet.getTimeStamp() - startTime) + " ms";
		String toolTip = null;
		if (packet.hasError() || packet.hasWarning()) {
			String color = (packet.hasError() ? "red" : "gray");
			toolTip = "<br><font color=\"" + color + "\">"
					+ packet.getErrorMessage() + "</font></html>";
		}

		Object[] temp = { icon, opcode, time,
				String.valueOf(packet.getSize()), packet.getName(), toolTip,
				false };
		_currentTable.add(temp);
	}

	public String getToolTip(int row, int col) {
		String toolTip = "<html>Packet: " + row;
		Object msg = _currentTable.get(row)[5];
		if (msg != null) {
			toolTip += msg;
		}
		return toolTip;
	}

	public void setIsMarked(int row, boolean val) {
		_currentTable.get(row)[6] = val;
	}

	public boolean getIsMarked(int row) {
		return (Boolean) _currentTable.get(row)[6];
	}
}
