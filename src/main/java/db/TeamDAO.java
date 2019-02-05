package db;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import model.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeamDAO implements DAO {

    FileWriter fileWriter;
    File file = new File("C:\\Users\\Steven\\Documents\\COMPRO\\WAP\\Project\\TaskListProject\\src\\main\\java\\db\\team.json");
    private String location;

    private List<Team> teams = new ArrayList<>();

    public TeamDAO(String location) {

        this.location = location;
    }

    public TeamDAO() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void insert(Object obj) {

        Team team = (Team) obj;

        if (!teamExists(team)) {

            try {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", team.getId());
                jsonObject.put("name", team.getName());

                JSONArray jsonArray = getJSONArray();

                if (jsonArray == null) {

                    jsonArray = new JSONArray();
                }

                jsonArray.add(jsonObject);

                System.out.println(jsonArray);

                PrintWriter pw = new PrintWriter(file);
                pw.write(jsonArray.toJSONString());

                pw.flush();
                pw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void update() {

    }

//    @Override
//    public void update(Team team) {
//
////        if(teamExists(team)){
////
////            getJSONArray().stream().filter((tm)->((JSONObject)tm).equals(team)).map((tm)->team);
////
////        }
//
//    }

    @Override
    public void delete(int id) {


    }

    @Override
    public Object read() {

        if (getJSONArray() != null) {

            getJSONArray().forEach((jobj) -> {

                JSONObject teamObject = (JSONObject) jobj;

                int id = Integer.parseInt(teamObject.get("id").toString());
                String name = String.valueOf(teamObject.get("name").toString());

                Team team = new Team(id, name);

                teams.add(team);

            });
        }

        return teams;
    }

    @Override
    public int size() {

        if (!isEmpty()) return (int) getJSONArray().stream().count();

        return 0;
    }

    private JSONArray getJSONArray() {

        JSONArray jsonArray = null;

        if (!isEmpty()) {
            try {

                Object obj = new JSONParser().parse(new FileReader(file));

                jsonArray = (JSONArray) obj;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return jsonArray;


    }

    public boolean isEmpty() {

        return file.length() <= 0 ? true : false;
    }

    public boolean teamExists(Team tm) {

        if (getJSONArray() != null) {

            return ((List<Team>) read()).stream().anyMatch((team) -> team.equals(tm));
        }

        return false;
    }
}
