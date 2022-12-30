package kr.ac.kopo.basic.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import kr.ac.kopo.basic.model.KakaoData;
@Service
public class MemberServiceImpl implements MemberService {

	@Override
	public KakaoData getUserInfo(String access_Token) {
		KakaoData data =new KakaoData();
        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + access_Token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("id").getAsString();
//            String profile_nickname = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("nickname").getAsString();
//            String profile_image = element.getAsJsonObject().get("properties").getAsJsonObject().getAsJsonObject().get("profile_image").getAsString();
//            String thumbnail_image = element.getAsJsonObject().get("properties").getAsJsonObject().getAsJsonObject().get("thumbnail_image").getAsString();

//            String account_email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("email").getAsString();
//            String age_range = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("age_range").getAsString();
            String birthday = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("birthday").getAsString();
//            String gender = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("gender").getAsString();
            String name = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("name").getAsString();
            String phone_number = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("phone_number").getAsString();
            
            data.setId(id);
//            data.setAccount_email(account_email);
//            data.setAge_range(age_range);
            data.setBirthday(birthday);
//            data.setGender(gender);
//            data.setProfile_image(profile_image);
//            data.setProfile_nickname(profile_nickname);
//            data.setThumbnail_image(thumbnail_image);
            data.setName(name);
            data.setPhone_number(phone_number);
            
            
            br.close();

            } catch (IOException e) {
                 e.printStackTrace();
            }

        return data;
	}

	@Override
	public String getAccessToken(String code) {
		 String access_Token = "";
	        String refresh_Token = "";
	        String reqURL = "https://kauth.kakao.com/oauth/token";

	        try {
	            URL url = new URL(reqURL);

	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            //  URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);

	            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=authorization_code");
	            sb.append("&client_id=c943c591f936aa2e7f4b5a2cd94914cb");  //본인이 발급받은 key
	            sb.append("&redirect_uri=http://localhost:8282/login/oauth_kakao");     // 본인이 설정해 놓은 경로
	            sb.append("&code=" + code);
	            bw.write(sb.toString());
	            bw.flush();

	            //    결과 코드가 200이라면 성공
	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode);

	            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";

	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("response body : " + result);

	            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            JsonParser parser = new JsonParser();
	            JsonElement element = parser.parse(result);

	            access_Token = element.getAsJsonObject().get("access_token").getAsString();
	            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

	            System.out.println("access_token : " + access_Token);
	            System.out.println("refresh_token : " + refresh_Token);

	            br.close();
	            bw.close();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return access_Token;
	}

}
