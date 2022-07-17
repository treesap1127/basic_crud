package kr.ac.kopo.basic.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.kopo.basic.model.One;
import kr.ac.kopo.basic.validator.OneValidator;

@Controller
@RequestMapping("basic1")
public class Basic1 {
	@GetMapping("/list")
	public String list() {
		return "basic1/list";
	}
	@GetMapping("/add")
	public String add() {
		return "basic1/add";
	}
	@PostMapping("/add")
	public String add(@Valid One item, BindingResult result) {
		System.out.println(item.getName());
		System.out.println(item.getTitle());
		System.out.println("BindingResult :" + result);
		if(result.hasErrors()){
			for(ObjectError obj : result.getAllErrors()) {
				System.out.println("valid 타입 :"+obj.getCode());
				System.out.println("메세지:"+obj.getDefaultMessage());
				System.out.println("객체명:"+obj.getObjectName());
			}
			return "basic1/add";
		}
		return "redirect:list";
	}
	@GetMapping("/update/{one}")
	public String update() {
		return "basic1/update";
	}
	@GetMapping("/delete/{one}")
	public String delete() {
		return "redirect:list";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		OneValidator validator = new OneValidator();
		binder.addValidators(validator);
	}
}
