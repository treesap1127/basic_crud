package kr.ac.kopo.basic.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.model.OAuth2AccessToken;

import kr.ac.kopo.basic.model.KakaoData;
import kr.ac.kopo.basic.model.NaverData;
import kr.ac.kopo.basic.service.MemberService;
import kr.ac.kopo.basic.util.NaverLoginBO;


@Controller
public class RootController {
	@Autowired
	MemberService memberService;
	
	  /* NaverLoginBO */
    private NaverLoginBO naverLoginBO;
    
    @Autowired
    private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
        this.naverLoginBO = naverLoginBO;
    }
    
	@RequestMapping("/")
	public String index(
			@RequestParam(required = false) String code, 
    		@RequestParam(required = false) String state, 
    		HttpSession session,
    		Model model) throws IOException {
			if(session.getAttribute("naverlogin")==null&&state!=null) {
				NaverData naverlogin = new NaverData();
		        OAuth2AccessToken oauthToken;
		        oauthToken = naverLoginBO.getAccessToken(session, code, state);
		        //로그인 사용자 정보를 읽어온다.
		        HashMap<String,Object> apiResult = naverLoginBO.getUserProfile(oauthToken);
		        System.out.println(apiResult);
		        naverlogin.setId(String.valueOf(apiResult.get("id")));
		        naverlogin.setAge(String.valueOf(apiResult.get("age")));
		        naverlogin.setBirthday(String.valueOf(apiResult.get("birthday")));
		        naverlogin.setBirthyear(String.valueOf(apiResult.get("birthyear")));
		        naverlogin.setEmail(String.valueOf(apiResult.get("email")));
		        naverlogin.setGender(String.valueOf(apiResult.get("gender")));
		        naverlogin.setMobile(String.valueOf(apiResult.get("mobile")));
		        naverlogin.setName(String.valueOf(apiResult.get("name")));
		        naverlogin.setNickname(String.valueOf(apiResult.get("nickname")));
		        naverlogin.setProfile_image(String.valueOf(apiResult.get("profile_image")));
		        session.setAttribute("naverlogin", naverlogin);
			}
		return "index";
	}
	/**
     * Tiles를 사용하지 않은 일반적인 형태
     */    
    @RequestMapping("/test.do")
    public String test() {
        return "tiles/template";
    }    
    
    /**
     * Tiles를 사용(header, left, footer 포함)
     */        
    @RequestMapping("/testPage.do")
    public String testPage() {
        return "tiles/template.page";
    }
    
    /**
     * Tiles를 사용(header, left, footer 제외)
     */    
    @RequestMapping("/testPart.do")
    public String testPart() {
        return "tiles/template.part";
    }
    
    @RequestMapping(value = "login/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(
			HttpServletRequest request) throws Exception {
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize"
				+ "?client_id=81ba215b9501055d6450419572e7abae"
				+ "&redirect_uri=http://localhost:8282/login/oauth_kakao"
				+ "&response_type=code";
		
		return reqUrl;
	}
    
    
	// 카카오 연동정보 조회
	@RequestMapping("login/oauth_kakao")
	public String oauthKakao(
			@RequestParam(value = "code", required = false) String code, 
			Model model,
			HttpSession session
			) throws Exception {
		System.out.println("#########" + code);
        String access_Token = memberService.getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);
        
        KakaoData kakaoInfo = memberService.getUserInfo(access_Token);
        if(!access_Token.isEmpty()) {
        	session.setAttribute("kakaoInfo", kakaoInfo);
        	session.setAttribute("access_Token", access_Token);
        }
        
        model.addAttribute("code", code);
        return "index"; //본인 원하는 경로 설정
	}
	
    @RequestMapping("/kakaologout")
	public String kakaologout(
			HttpSession session,
			@RequestParam(value = "code", required = false) String code) {
    	String access_Token = (String)session.getAttribute("access_Token");
    	String reqURL = "https://kapi.kakao.com/v1/user/logout";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String result = "";
	        String line = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println(result);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		session.removeAttribute("kakaoInfo");
		session.removeAttribute("access_Token");
		return "redirect:/";
	}
    @RequestMapping("login/getNaverAuthUrl")
	public @ResponseBody String getNaverAuthUrl(
			HttpSession session
			) {
    	 /* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
        
        return naverAuthUrl;
	}
    @RequestMapping("/naverlogout")
    public String naverlogout(HttpSession session) {
    	session.removeAttribute("naverlogin");
    	return "redirect:/";
    }

 }
