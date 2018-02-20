import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPassword("a1330A1330");
        dataSource.setUrl("jdbc:mysql://127.0.0.1/cv_dinner");
        dataSource.setUsername("root");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Student> students = jdbcTemplate.query("SELECT name, cost FROM meals", new RowMapper<Student>() {
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                Student student = new Student();
                student.name = resultSet.getString("name");
                student.age = resultSet.getInt("cost");
                return student;
            }
        });

        /*jdbcTemplate.update("UPDATE meals SET name = ? WHERE id = 2","borsch with meat 1");  DELETE & INSERT done via .update function. Just with necessary query code */

        Gson json = new Gson();

        String jsonResult = json.toJson(students);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(jsonResult);
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
