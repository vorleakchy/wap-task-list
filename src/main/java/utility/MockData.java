package utility;

import db.DAO;
import db.TaskDAO;
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

    public List<Task> retrieveTaskList() {

        dao = new TaskDAO();

        return (List<Task>) dao.read();
    }


    public List<Team> retrieveTeams() {

        dao = new TeamDAO();

        return (List<Team>) dao.read();

    }

}


