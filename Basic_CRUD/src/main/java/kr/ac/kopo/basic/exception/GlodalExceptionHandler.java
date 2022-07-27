package kr.ac.kopo.basic.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlodalExceptionHandler extends RuntimeException {
	@ExceptionHandler(java.lang.NullPointerException.class)
	public String exception2() {
		return "error";
	}// servlet-context에 context:component-scan 필요
}

/*프로젝트 중에 절대 변하지 않을값 properties
http://propedit.sourceforge.jp/eclipse/updates
설치!-> 마켓 같은느낌 ㅇㅇ properties에디터만
@propertySource("링크")
컨트롤러 아래 선언해주고

@Value(${프로퍼티명})
private String ~ 이면 프로퍼티 이름을 사용 가능하다!

-> jsp 에서 사용하려면 메세지로 등록해야함
-> 다국어 처리 가능
-> <beans:bean: class="MessageSource"등록
beans: property로 name value넣어서 셋팅
->and beans: bean id='MessageAccessor'등록
    consturctor까지 등록

위 메세지는 모델이고 가 아니라 스프링에서 가져오네
컨트롤러에선  MessageSource를 주입받아서 겟메세지{"","",""} 해서 가져와서 사용
jsp태그라이브러리에 spring 태그를 가져와야함
spring:message code로 출력 ㄷㄷ

properties는겟 메세지 뒤에 두개는
하나는 중간에 들어갈 문자열 넣어줄 수 있음
jsp에선 argment

3번째는 Local local 로 다국어 처리가능

[이태성] [오후 8:48] 인터셉터 exclude 미사용 확인
Valid 사용
[이태성] [오후 8:51] 에러페이지 처리
[이태성] [오후 9:07] restapi같은 경우엔 ResponseEntity 로 리스트로 묶은 모델을 보내네 ㄷㄷ
[이태성] [오후 11:23] 부분 jsp로딩 jsp화면 일부 띄우기
<c:import url="/WEB-INF/views/include/~.jsp"

[이태성] [오후 11:29] 메인 같은 경우
c:set var='root' value="${pageContext.request.contextPath}
쓰면 컨텍스트패스 완성
[이태성] [오후 11:41] xml 메세지 등록
<beans:bean class='org.springframework.context.support.ReloadableResourceBundleMessageSource' id='messageSource'>
<beans:property name="mes">
<bean:list>
<beans:value>/WEB-INF/properties/error_message

xml 메세지 접근자 등록
<beans:bean class='org.springframework.context.support.MessageSourceAccessor' >
beans:constructor-arg ref='messageSource'

multpart 필터도 있네.

[이태성] [오후 8:48] 인터셉터 exclude 미사용 확인
Valid 사용
[이태성] [오후 8:51] 에러페이지 처리
++ 복호화?
 * */
