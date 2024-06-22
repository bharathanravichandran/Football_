import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/footballDB";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "9445617174";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                out.println("<html><body><h2>Registration successful</h2></body></html>");
            }

            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }
}
