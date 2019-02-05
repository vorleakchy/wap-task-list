package utility;

import db.DAO;
import db.TeamDAO;
import model.Task;
import model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * utility class to return mock data for testing
 *
 * @author kl
 * @since 11/19/2017
 */
public class MockData {

    public ArrayList<Task> taskList = new ArrayList<>();
    DAO dao;

    public ArrayList<Task> retrieveTaskList() {

        taskList.add(new Task(101, "first task", "2017-11-19", "Personal"));
        taskList.add(new Task(102, "second task", "2017-11-23", "Work"));
        taskList.add(new Task(103, "third task", "2017-12-19", "Work"));

        return taskList;
    }

    public List<Task> retrieveTasks() {

        return null;

    }

    public List<Team> retrieveTeams() {

        dao = new TeamDAO();

        return (List<Team>) dao.read();

    }

}


