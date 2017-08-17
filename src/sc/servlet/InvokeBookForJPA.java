package sc.servlet;

import sc.ejb.entity.Book;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class InvokeBookForJPA
 */
@WebServlet("/InvokeBookForJPA")
public class InvokeBookForJPA extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@PersistenceUnit(unitName="APMTestJavaEEDataSource")
	private EntityManagerFactory entityManagerFactory;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvokeBookForJPA() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String queryStr = request.getParameter("queryStr");
		if (queryStr == null || queryStr.trim().length() == 0) {
			queryStr = "SELECT b From Book b";
		}
		List<Book> bookList = entityManager.createQuery(queryStr).getResultList();
		outputBookList(bookList, request, response);
		entityManager.close();
	}

	private void outputBookList(List<Book> bookList, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Successfully get Book list:<br>");

		if (bookList == null || bookList.size() == 0) {
			out.println("Book list is empty.");
		} else {
			for (int i =0; i< bookList.size(); ++i) {
				Book book = bookList.get(i);
				out.println("Book[" + i + "]: id:" + book.getId() + ", name:" + book.getName() + "<br/>");
			}
		}
	}

}
