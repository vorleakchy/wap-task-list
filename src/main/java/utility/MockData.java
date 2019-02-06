package utility;

import db.UserDAO;
import model.*;
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

    public ArrayList<User> userList = new ArrayList<>();


    public ArrayList<User> retrieveUserList() {
        /*
        userList.add(new User(1,"Vorleak",1));
        userList.add(new User(1,"Steven",1));
        userList.add(new User(1,"Edwin",1));
        userList.add(new User(1,"Chy",2));
        userList.add(new User(1,"Bernal",5));
        userList.add(new User(1,"Duran",5));
        return userList;
        */
        DAO daoUser= new UserDAO();
        return (ArrayList<User>) daoUser.read();// must chek if correct arraylist or list
    }

    public ArrayList<Task> taskList = new ArrayList<>();
    DAO taskDAO = new TaskDAO();
    DAO teamDAO = new TeamDAO();

    public List<Task> retrieveTaskList() {

        return (List<Task>) taskDAO.read();
    }

    public void addTask(Task task) {

        taskDAO.insert(task);

    }

    public List<Team> retrieveTeams() {

        return (List<Team>) teamDAO.read();

    }

}


