package kr.ac.kopo.basic.controller;


import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
	@RequestMapping("/")
	public String index() {
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
}
