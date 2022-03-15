import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatThread extends Thread {
	private final Socket socket;
	private final ChatService service;

	public ChatThread(Socket socket, ChatService service) {
		this.socket = socket;
		this.service = service;
	}

	public void run() {
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			String command;

			while (true) {

				command = dis.readUTF();

				if (command.equals("/ENTER")) {
					// 닉네임 읽어주기
					String nickname = dis.readUTF();

					boolean result = service.enter(nickname, dos);
					// 되는지 안되는지를 클라이언트에도 알려줘야함
					dos.writeBoolean(result);
					dos.flush();
				} else if (command.equals("/CHAT")) {

					String message = (dis.readUTF());
					// 쳇 보내고 채팅 메세지도 보내는 것
					service.chat(message);
				} else if (command.equals("/QUIT")) {
					service.quit(dis.readUTF());
					break;

				} else if (command.equals("/ONECHAT")) {
					String message = dis.readUTF();
					service.privatechat(message);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
