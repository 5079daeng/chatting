import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Set;

public interface ChatService {

	// 입장
	boolean enter(String nickname, DataOutputStream outputStream);

	// 채팅
	List chat(String message);

	// 귓속말
	DataOutputStream privatechat(String message);

	// 퇴장
	void quit(String nickname);
}
