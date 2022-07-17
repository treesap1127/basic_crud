package kr.ac.kopo.basic.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class One {
	private int one;
	@Size(min=2,max=20,message="2에서 20의 자릿수를 입력")// 타입 크기 기준
	private String name;
	@NotBlank(message = "제목은 필수 입력 값입니다.")
	private String title;
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String passwd;

	//@AssertTrue//true만
	//@DecimalMax//이하
	//@Digits(integer=,fraction=)//지정 자리 수
	//@Positive//양수만 (Positiveorzero)0또는 // Negative(음수)
	//@Email//이메일
	
	public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	

}
