import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	private static final int PORT = 7116;

	public static void main(String[] args) {
		ChatService chatService = new ChatServiceImpl();
// 인스턴스 생성 
		try (ServerSocket server = new ServerSocket(PORT)) {
			// 여러명이 서버에 접근하게 하려면
			while (true) {
				Socket socket = server.accept(); // 서버가 호출을 기다릴 수 있게 accept 호출

				// 클라이언트와 1대1로 대응되는 스레드 생성 필요
				Thread t = new ChatThread(socket, chatService);
				t.start();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
