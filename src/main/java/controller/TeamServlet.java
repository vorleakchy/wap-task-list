package controller;

import com.google.gson.Gson;
import model.Team;
import utility.MockData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/TeamServlet")
public class TeamServlet extends HttpServlet {

    MockData mockData = new MockData();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        List<Team> teams = mockData.retrieveTeams();

        String output = new Gson().toJson(teams);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        out.write(output);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
