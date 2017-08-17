package sc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.*;

import javax.naming.*;

import sc.service.db.DSService;
import sc.utils.AppPropertiesConfig;
/**
 * Servlet implementation class DSConnnectionTest
 */
@WebServlet("/DSConnnectionTest")
public class DSConnnectionTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DSConnnectionTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.ConnectDataSource(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.ConnectDataSource(request, response);
	}

	private void ConnectDataSource(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String DSName = request.getParameter("DSName");
		String DBType = request.getParameter("DBType");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DSService dsService;
		StringBuffer respContent = new StringBuffer();
		if(DBType != null && DBType.length() != 0){
			if(DSName == null || DSName.length() == 0){
				
				DSName = AppPropertiesConfig.getDataSourceJNDIName();
				
			}


			
			try {
				
				dsService = new DSService(DBType, DSName);
				//String[][] result = dsService.getDBTables();
				String[][] result = dsService.getDBTablesPS();
				respContent.append("Successfull Connected to Data Source: "+ DSName+"<br>");
				respContent.append("Executed SQL: "+ dsService.getSQLPS()+"<br>");
				int rows = result.length;
				int columns = result[0].length;
				respContent.append("<br>");
				respContent.append("<table>");
				for(int i=0; i<rows; i++){
					respContent.append("<tr>");
					for(int j=0; j<columns; j++){
						respContent.append("<td>");
						respContent.append(result[i][j]);
						respContent.append("</td>");
					}
					respContent.append("</tr>");
				}
				respContent.append("</table>");
				respContent.append("<br>");
				out.println(respContent);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				out.println("Can't connect to "+DBType+" Data Source: "+ DSName+"<br>"+"<br>"+ e.toString()+"<br>");
				e.printStackTrace(out);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				out.println("Can't connect to "+DBType+" Data Source: "+ DSName+"<br>"+"<br>"+ e.toString()+"<br>");
				e.printStackTrace(out);
			}
		}
	}
	
}
