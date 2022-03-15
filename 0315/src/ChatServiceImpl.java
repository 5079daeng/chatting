import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ChatServiceImpl implements ChatService {
	// private List<DataOutputStream> list; // 사용자가 접속했을 때 기억하려고
	private HashMap<String, DataOutputStream> list;
	private List list2;

	public ChatServiceImpl() {
		list = new HashMap<>();
		list2 = new ArrayList<>();
	}

	@Override
	// 입장하는 만큼 값을 변경하기 때문에 동기화 문제가 발생할 수 있다
	public synchronized boolean enter(String nickname, DataOutputStream outputStream) {
		System.out.println(nickname + "사용자가 입장을 요청함");

		if (!list.containsKey(nickname)) {
			list.put(nickname, outputStream);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public synchronized List chat(String message) {

		try {

			Set key = list.keySet();

			for (Iterator iterator = key.iterator(); iterator.hasNext();) {
				String keyvalue = (String) iterator.next();
				DataOutputStream value = list.get(keyvalue);

				if (message.equals(keyvalue + ":" + "사용자보기")) {
					message = key.toString();
				}

				value.writeUTF(message);
				value.flush();

			}
			return list2;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list2;

	}

	@Override
	public synchronized void quit(String nickname) {
		list.remove(nickname);
	}

	@Override
	public DataOutputStream privatechat(String message) {
		String[] st = message.split(":");
		DataOutputStream dos = list.get(st[1]);

		try {
			dos.writeUTF("[귓속말]" + ":" + st[1] + ":" + st[2] + ":" + st[3]);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dos;
	}

}
