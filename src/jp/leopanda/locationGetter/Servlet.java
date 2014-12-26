package jp.leopanda.locationGetter;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Servlet extends HttpServlet {

	 	@Override
		public void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {
	 		String callBack = req.getParameter("callback");  // get request parameter for JSONP

	 		String JSON  = "application/json;charset=utf-8";
	 		String JSONP = "text/javascript;charset=utf-8";
	 		
	 		BloggerService blogService = new BloggerService();
			String responseBody = blogService.getLocationJson();
			
			if(callBack != null){
				resp.setContentType(JSONP);
				resp.getWriter().print(callBack + "(" + responseBody + ");");
			}else{
				resp.setContentType(JSON);
				resp.getWriter().print(responseBody);				
			}
		}
	 	
		public void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws IOException {
		}
}
