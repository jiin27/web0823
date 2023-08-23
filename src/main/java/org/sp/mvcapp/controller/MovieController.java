package org.sp.mvcapp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sp.mvcapp.model.movie.MovieManager;

//영화에 대한 조언 요청을 받는 컨트롤러
public class MovieController extends HttpServlet{

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 받기
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//파라미터 받기
		String movie = request.getParameter("movie");
		
		//컨트롤러에서는 가능해도 로직을 작석하지 말자. 
		//모델(로직)과 뷰를 분리시켜놓아야 다른 프로젝트에서도 재사용 가능하기 때문에.
		//로직을 작성해버리면, MVC 중 모델과 컨트롤러를 동시에 수행하기 때문에 진행이 매끄럽지 않다.
		MovieManager manager=new MovieManager();
		String msg=manager.getAdvice(movie);
		
		//결과 페이지와 컨트롤러가 분리되어 있으므로 msg와 같은 결과가 담겨진 지역변수가 유지되려면
		//어딘가에 저장해 놓아야 한다. 
		// → 현재로써는 session에 저장한다.
		//HttpSession session = request.getSession(); //request를 통해 세션을 얻어와야 한다!
		//session.setAttribute("msg", msg);
		
		//만일 요청을 끊지 않고, 결과 페이지인 result.jsp로 포워딩 하는 방법만 있다면, 우리는 session까지 사용할 필요가 없다.
		request.setAttribute("msg", msg); //session보다 생명력의 차이가 있을 뿐.
		
		//서버의 view 중 어떤 view로 포워딩할지를 결정하는 객체
		RequestDispatcher dis=request.getRequestDispatcher("/movie/result.jsp"); //request포워딩 객체
		dis.forward(request, response); //포워딩할 때 요청,응답 객체를 가지고 가는 메서드
		
		//PrintWriter out=response.getWriter();
		//서블릿이 디자인 결과를 표현할 수는 있지만, MVC로 분리시키지 않으면
		//디자인 코드는 디자이너 퍼블리셔와 협업의 대상이므로 java 코드에 두어서는 안된다.
		//디자인 즉 view를 담당하는 기술로 표현해야 한다.
		
		//response.sendRedirect("/movie/result.jsp"); //응답객체에 재접속 정보를 채운것 -> <script>location.href="/movie/result.jsp";</script>
		
	}
}
