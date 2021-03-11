import java.util.HashMap;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class SendTextMessage {
  public void messageGo(String carNum, String phoneNumber, int parkingCode){
    String api_key = ""; //원래 내용있어야함
    String api_secret = ""; //위와 동일
    Message coolsms = new Message(api_key, api_secret);
String message = "차량번호 " + carNum+ "님의 주차 코드는 " +parkingCode + "입니다."; 
    // 4 params(to, from, type, text) are mandatory. must be filled
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("to", phoneNumber);
    params.put("from", "번호"); //무조건 자기번호 (인증)
    params.put("type", "SMS");
    params.put("text", message);
    params.put("app_version", "test app 1.2"); // application name and version

    try {
    	//send() 는 메시지를 보내는 함수  
      JSONObject obj = (JSONObject) coolsms.send(params);
      System.out.println(obj.toString());
    } catch (CoolsmsException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getCode());
    }
  }
}
