package controller;

import com.google.gson.Gson;
import model.User;
import org.json.simple.JSONObject;
import utility.MockData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    Gson gson=null;
    MockData mockData=null;

    @Override
    public void init() throws ServletException {

        gson = new Gson();
        mockData = new MockData();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String taskJson = request.getParameter("params");

        User user = gson.fromJson(taskJson, User.class);
        mockData.addUser(user);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(gson.toJson(mockData.getUsers()));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        System.out.println(gson.toJson(mockData.getUsers()));
        out.write(gson.toJson(mockData.getUsers()));
    }
}
