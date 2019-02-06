package utility;

import db.UserDAO;
import model.*;
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
    DAO dao;

    public ArrayList<Task> retrieveTaskList() {

        taskList.add(new Task(101, "first task", "2017-11-19", "Personal", 1,2));
        taskList.add(new Task(102, "second task", "2017-11-23", "Work", 2,2));
        taskList.add(new Task(103, "third task", "2017-12-19", "Work", 3,1 ));

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


