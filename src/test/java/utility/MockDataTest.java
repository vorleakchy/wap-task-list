package utility;

import model.Team;
import model.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MockDataTest {

    MockData mockData = new MockData();

    @Test
    void retrieveTasks() {

        assertEquals(new ArrayList<>(), mockData.retrieveTaskList());

    }

    @Test
    void getUsers() {

       // assertEquals(new ArrayList<User>(), mockData.getUsers());

    }

    @Test
    void getTeams() {

        //assertEquals(new ArrayList<Team>(), mockData.getTeams());

    }
    @Test
    void addTeam(){

        mockData.addTeam(new Team(1,"Green"));

    }


    @Test
    void insertUser(){

        mockData.addUser(new User(0,"Steven",new Team(1,"Green")));

    }

    @Test
    void getTeamSize(){

        assertEquals(1,mockData.getTeams().size());
    }

    @Test
    void getUsersSize(){

        assertEquals(1,mockData.getUsers().size());
    }
}