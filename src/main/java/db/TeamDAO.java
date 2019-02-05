package db;

import model.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements DAO {

    File file = new File("src/main/java/db/team.json");

    private List<Team> teams = new ArrayList<>();

    public TeamDAO() {

    }

    /*add a Team to the TeamDAO*/
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

    /*Update a Team from the DAO*/
    @Override
    public void update() {

    }

    /*Delete a Team from the DAO*/
    @Override
    public void delete(int id) {


    }

    /*Get Teams from the DAO*/
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

    /*Returns the number of teams in the DAO*/
    @Override
    public int size() {

        if (!isEmpty()) return (int) getJSONArray().stream().count();

        return 0;
    }

    /*Checks whether DAO is empty*/
    public boolean isEmpty() {

        return file.length() <= 0 ? true : false;
    }

    /*Checks whether Team passed in the parameter args exists*/
    public boolean teamExists(Team tm) {

        if (getJSONArray() != null) {

            return ((List<Team>) read()).stream().anyMatch((team) -> team.equals(tm));
        }

        return false;
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


}
