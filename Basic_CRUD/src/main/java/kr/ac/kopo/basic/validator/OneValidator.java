package kr.ac.kopo.basic.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import kr.ac.kopo.basic.model.One;

public class OneValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {//유효성 검사가 가능한지
		return One.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "errorname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "errortitle");
	}

}
