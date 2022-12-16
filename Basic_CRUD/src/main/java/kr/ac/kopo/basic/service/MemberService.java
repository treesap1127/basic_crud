package kr.ac.kopo.basic.service;

import kr.ac.kopo.basic.model.KakaoData;

public interface MemberService {

	KakaoData getUserInfo(String access_Token);

	String getAccessToken(String code);

}
