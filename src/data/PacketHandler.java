package data;

import java.net.DatagramPacket;

public abstract class PacketHandler {

	public abstract void process(Packet packet);

}
