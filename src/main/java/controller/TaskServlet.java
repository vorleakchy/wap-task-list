package controller;

import com.google.gson.Gson;
import model.Task;
import utility.MockData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {

    MockData mockData = null;

    @Override
    public void init() throws ServletException {

        mockData = new MockData();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String taskJson = request.getParameter("task");
        Gson g = new Gson();
        Task task = g.fromJson(taskJson, Task.class);

        mockData.addTask(task);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(g.toJson(task));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String JSONtasks;
        List<Task> taskList = mockData.retrieveTaskList();

        JSONtasks = new Gson().toJson(taskList);

        System.out.println(JSONtasks);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(JSONtasks);


    }
}
