package org.sp.mvcapp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sp.mvcapp.model.blood.BloodManager;

//MVC 에서 모델과 뷰를 분리시키기 위해 컨트롤러의 관여가 필요하다.
//MVC 개발 방법론을 javaEE 기술로 구현했을 때의 개발 방법을 가리켜 Model2 라고 한다.
//Model - Java(순수 java코드), View - jsp, Controller - Servlet
public class BloodController extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String blood=request.getParameter("blood");
		
		//로직 (재사용 가능한 코드)
		BloodManager manager=new BloodManager();
		String msg=manager.getAdvice(blood);
		
		//msg 변수가 곧(응답시) 소멸되므로, 어딘가에 저장해놓지 않으면 아래의 코드에 의한 재접속시 msg를 사용할 수 없게 된다.
		//session을 이용해보자
		
		//서버에 의해 생성된 세션 가져오기
		HttpSession session=request.getSession();
		
		//session은 맵의 자식이므로 key-value의 쌍으로 데이터를 관리한다
		session.setAttribute("msg", msg);
		
		//결과 보여주기
		response.sendRedirect("/blood/result.jsp"); //msg가 죽기 전에 세션에 담아놓자
	}

}
