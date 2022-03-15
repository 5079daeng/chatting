import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatClient {
	private static final int PORT = 7116;

	public static void main(String[] args) {

		ChatService chatService = new ChatServiceImpl();
		try (Socket socket = new Socket("127.0.0.1", PORT)) {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			Scanner scan = new Scanner(System.in);
			boolean result = false;
			while (!result) {
				System.out.println("닉네임을 입력하세요");
				String nickname = scan.nextLine();
				dos.writeUTF("/ENTER");
				dos.writeUTF(nickname);
				dos.flush();
				result = dis.readBoolean();
			}
			// 서버에서 보낸 메세지
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						while (true) {
							String fromServer;
							fromServer = dis.readUTF();
							System.out.println(fromServer);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			t.setDaemon(true);
			t.start();

			// 콘솔입력메세지
			while (true) {

				Scanner scanner = new Scanner(System.in);
				String fromConsole = scanner.nextLine();

				if (fromConsole.equals("종료할래요")) {
					break;
				}
				if (fromConsole.contains("[귓속말]")) {
					dos.writeUTF("/ONECHAT");
					dos.writeUTF(fromConsole);
					dos.flush();
				} else {
					dos.writeUTF("/CHAT");
					dos.writeUTF( ""+":" + fromConsole);
					dos.flush();

				}

			}

			dos.writeUTF("/QUIT");
			dos.writeUTF("");

			dos.flush();

		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}