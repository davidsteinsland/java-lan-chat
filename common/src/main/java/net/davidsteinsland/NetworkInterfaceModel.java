package net.davidsteinsland;

import javax.swing.DefaultComboBoxModel;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.xml.bind.DatatypeConverter;

public class NetworkInterfaceModel extends DefaultComboBoxModel<String> {

	private List<NetworkInterface> interfaces = null;

	public NetworkInterfaceModel() {
		super();
		try {
			interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
		} catch (SocketException e) {
			interfaces = new ArrayList<>();
		}
	}

	@Override
	public int getSize() {
		return interfaces.size();
	}
	@Override

	public String getElementAt(int i) {
		NetworkInterface netint = interfaces.get(i);

		StringBuilder sb = new StringBuilder();
		sb.append(netint.getDisplayName());

		try {
			byte[] macAddr = netint.getHardwareAddress();

	        if (macAddr != null) {
	        	sb.append(" - ");
	        	sb.append(DatatypeConverter.printHexBinary(macAddr));
	        	// for (byte b : macAddr) {
		        // 	System.out.printf("%02x", b);
		        // }
		        // System.out.println();
		    }
    	} catch (SocketException e) {
    		System.err.println(e.getMessage());
    	}

	    return sb.toString();
	}

	public void refreshList() {

	}
}
