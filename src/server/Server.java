package server;
import gui.Common;
import gui.Common.ActionList;
import gui.Common.MapAlreadyExistException;
import gui.Common.MapNotExistException;
import gui.Element;
import gui.Element.PARTS;
import gui.MindPolyGraph.LOGIC_TYPES;
import gui.MindPolyGraph.OWNER_TYPES;
import gui.MindPolyGraph;
import gui.ObjectAlreadyAddedException;
import gui.Screen;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


import logic.Map;
import logic.Mind;
import logic.Snake;
import logic.Mind.MindMap;

public class Server {
	// Socket �������
	ServerSocket server = null;
	// Socket �������
	Socket fromclient = null;
	// ����
	int port = 65535;
	// ������ �����/������
	BufferedReader in = null;
	PrintWriter out = null;
	
	// ������ ������ Battle ��� ������� ���������� �����
	Battle battle = null;
	// ������
	Snake[] snakes = null;
	
	public Server() throws IOException{
		// �������������
		battle = new Battle();
		snakes = battle.snake_fill();
		Map map = null;
		
		new Screen();
		try {
			// ������������� �����, �����
			battle.init("battle", snakes);
			map = Common.selectMap("battle");
			map.setBorder(800, 600);
		} catch (MapAlreadyExistException | MapNotExistException | ObjectAlreadyAddedException e1) {
			e1.printStackTrace();
		}
		System.out.println("calc--->");
		List<ActionList> al = battle.battleCalc(snakes);
		System.out.println("<---calc");
		
		// ������ �� ������������� �����
		try {
			server = new ServerSocket(port);
		} catch (IOException ex) {
			System.out.println("Couldn't listen to port " + port);
			System.exit(-1);
		}

		// �������� ����������� �������
		try {
			System.out.println("[Server]Waiting for a client...");
			fromclient = server.accept();
			System.out.println("[Server]Client connected");
		} catch (IOException e) {
			System.out.println("[Server]Can't accept");
			System.exit(-1);
		}
		
		ObjectOutputStream oos = null;
		// ������������ ������������ ������
		try {
			oos = new ObjectOutputStream(fromclient.getOutputStream());
			oos.writeObject(al);
			oos.flush();
			oos.close();
		} catch (Exception ex) {
			System.out.println("Exception during serialization: " + ex);
			System.exit(0);
		}

		fromclient.close();
		server.close();
		System.out.println("[Server]Sockets closed");
	}
}
