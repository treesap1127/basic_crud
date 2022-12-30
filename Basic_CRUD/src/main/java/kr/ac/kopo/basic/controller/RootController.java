package kr.ac.kopo.basic.controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import kr.ac.kopo.basic.model.NaverData;
import kr.ac.kopo.basic.util.NaverLoginBO;


@Controller
public class RootController {
	
	  /* NaverLoginBO */
    private NaverLoginBO naverLoginBO;
    
	// 카카오 로그인 정보
    private final static String NAVER_CLIENT_ID = "sRdRkJBnaogCeiOabSDU";
    private final static String NAVER_CLIENT_SECRET = "k31f5lTcHv";
    private final static String NAVER_REDIRECT_URI = "http://localhost:8282/";
    private final static String SESSION_STATE = "oauth_state";
    /* 조회 API URL */
    private final static String NAVER_TOKEN_API_URL = "https://nid.naver.com/oauth2.0/token";
    private final static String NAVER_PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
    
	@RequestMapping("/")
	public String index(
			@RequestParam(required = false) String code, 
    		@RequestParam(required = false) String state, 
    		HttpSession session,
    		Model model) throws IOException {
			if(session.getAttribute("naverlogin")==null&&state!=null) {
				NaverData naverlogin = new NaverData();
		        String access_token = getNaverAccessToken(code,session,state);
		        //로그인 사용자 정보를 읽어온다.
		        HashMap<String,Object> apiResult = getUserProfile(access_token);
		        System.out.println(apiResult);
		        naverlogin.setId(String.valueOf(apiResult.get("id")));
//		        naverlogin.setAge(String.valueOf(apiResult.get("age")));
//		        naverlogin.setBirthday(String.valueOf(apiResult.get("birthday")));
//		        naverlogin.setBirthyear(String.valueOf(apiResult.get("birthyear")));
//		        naverlogin.setEmail(String.valueOf(apiResult.get("email")));
//		        naverlogin.setGender(String.valueOf(apiResult.get("gender")));
		        naverlogin.setMobile(String.valueOf(apiResult.get("mobile")));
		        naverlogin.setName(String.valueOf(apiResult.get("name")));
//		        naverlogin.setNickname(String.valueOf(apiResult.get("nickname")));
//		        naverlogin.setProfile_image(String.valueOf(apiResult.get("profile_image")));
		        session.setAttribute("naverlogin", naverlogin);
			}
		return "index";
	}
	@RequestMapping("login/getNaverAuthUrl")
	public @ResponseBody String getNaverAuthUrl(
			HttpSession session
			) {
		/* 세션 유효성 검증을 위하여 난수를 생성 */
        String state = UUID.randomUUID().toString();
        /* 생성한 난수 값을 session에 저장 */
        session.setAttribute("oauth_state", state);
        
		String reqUrl = 
				"https://nid.naver.com/oauth2.0/authorize"
				+ "?client_id="+NAVER_CLIENT_ID
				+ "&client_secret="+NAVER_CLIENT_SECRET  //본인이 발급받은 key
				+ "&redirect_uri="+NAVER_REDIRECT_URI
				+ "&state="+state
				+ "&response_type=code";
		return reqUrl;
	}
	
	private HashMap<String, Object> getUserProfile(String access_token) {
		HashMap<String, Object> jsonData=new HashMap<String, Object>();
		try {
            URL url = new URL(NAVER_PROFILE_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + access_token); //전송할 header 작성, access_token전송

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
            
            ObjectMapper mapper = new ObjectMapper();
            jsonData =mapper.readValue(result,HashMap.class);
            
            System.out.println(jsonData);
            br.close();
        } catch (IOException e) {
             e.printStackTrace();
        }
		return jsonData;
	}

	private String getNaverAccessToken(String code,HttpSession session,String state) {
		String access_Token = "";
		 try {
			 URL url = new URL(NAVER_TOKEN_API_URL);
	            
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	            
				/* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
		        String sessionState = (String) session.getAttribute("oauth_state");
		        if (state.equals(sessionState)){
		        	System.out.println("f");
		        }
		        if(StringUtils.pathEquals(sessionState, state)){
					conn.setRequestMethod("POST");
					conn.setDoOutput(true);
					// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
		            
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
					StringBuilder sb = new StringBuilder();
					sb.append("grant_type=authorization_code");
		            
		            sb.append("&client_id="+NAVER_CLIENT_ID);  //본인이 발급받은 key
		            sb.append("&client_secret="+NAVER_CLIENT_SECRET);  //본인이 발급받은 key
		            sb.append("&redirect_uri="+NAVER_REDIRECT_URI);     // 본인이 설정해 놓은 경로
					sb.append("&code=" + code);
					bw.write(sb.toString());
					bw.flush();
		            
					// 결과 코드가 200이라면 성공
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
	
		            System.out.println("access_token : " + access_Token);
	
		            br.close();
		            bw.close();
		        }
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return access_Token;
	}
	
    @RequestMapping("/naverlogout")
    public String naverlogout(HttpSession session) {
    	session.removeAttribute("naverlogin");
    	return "redirect:/";
    }

   
	
//    /**
//     * Tiles를 사용하지 않은 일반적인 형태
//     */    
//    @RequestMapping("/test.do")
//    public String test() {
//        return "tiles/template";
//    }    
//    
//    /**
//     * Tiles를 사용(header, left, footer 포함)
//     */        
//    @RequestMapping("/testPage.do")
//    public String testPage() {
//        return "tiles/template.page";
//    }
//    
//    /**
//     * Tiles를 사용(header, left, footer 제외)
//     */    
//    @RequestMapping("/testPart.do")
//    public String testPart() {
//        return "tiles/template.part";
//    }
 }
