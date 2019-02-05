package utility;

import model.*;
import db.DAO;

import java.util.ArrayList;

 *  @author kl */
import java.util.List;

/**
 * utility class to return mock data for testing
public class MockData {

    public ArrayList<User> userList = new ArrayList<>();


    public ArrayList<User> retrieveUserList() {
        userList.add(new User(1,"Vorleak",1));
        userList.add(new User(1,"Steven",1));
        userList.add(new User(1,"Edwin",1));
        userList.add(new User(1,"Chy",2));
        userList.add(new User(1,"Bernal",3));
        userList.add(new User(1,"Duran",3));
        return userList;
    }

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


