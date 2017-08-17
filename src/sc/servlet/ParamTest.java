package sc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ParamTest
 */
@WebServlet("/ParamTest")
public class ParamTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParamTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) {
		String contentType = request.getContentType();
		System.out.println("Reuqest Content Type: "+contentType);
		String requestEncoding = request.getCharacterEncoding();
		System.out.println("Reuqest Encoding: "+requestEncoding);

		if(contentType.indexOf("json")>=0){
			processJSON(request, response);
		}
		else{
			processParm( request, response);
		}
	}

	private void processParm(HttpServletRequest request, HttpServletResponse response){

		PrintWriter out;
		try {
			String postParamValue = request.getParameter("postParamValue");
			System.out.println("postParamValue: "+postParamValue);
			out = response.getWriter();
			out.println(postParamValue);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void processJSON(HttpServletRequest request, HttpServletResponse response){
		String acceptjson = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(
			request.getInputStream(), request.getCharacterEncoding()));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			acceptjson = sb.toString().trim();
			System.out.println(acceptjson);
			PrintWriter out = response.getWriter();
			out.println(acceptjson);
			out.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}
