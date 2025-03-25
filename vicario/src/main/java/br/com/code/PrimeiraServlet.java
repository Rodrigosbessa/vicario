package br.com.code;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PrimeiraServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String first_name = request.getParameter("primeiro-nome");
        String last_name = request.getParameter("ultimo-nome");
        String contact = request.getParameter("contact");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");

        String url = "jdbc:mariadb://mysql-vicario.alwaysdata.net:3306/vicario_data";
        String user = "vicario";
        String password = "MarceloMoura123";

        System.out.println(username);
        System.out.println(first_name);
        System.out.println(last_name);
        System.out.println(contact);
        System.out.println(pass);
        System.out.println(email);

        try (Connection connect = DriverManager.getConnection(url, user, password)){
            Class.forName("org.mariadb.jdbc.Driver");

            // 3️⃣ Inserir dados no banco
            String sql = "INSERT INTO utilizadores (nome_utilizador, prim_nome, ult_nome, contacto, palavra_passe, email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, first_name);
            stmt.setString(3, last_name);
            stmt.setString(4, contact);
            stmt.setString(5, pass);
            stmt.setString(6, email);
            stmt.executeUpdate();

            // 4️⃣ Responder ao usuário
            PrintWriter out = response.getWriter();
            out.println("<h2>Utilizador cadastrado com sucesso!</h2>");

            // Fechar conexões
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

