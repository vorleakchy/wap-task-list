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

    final File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "/db/team.json");

    private List<Team> teams = null;

    public TeamDAO() {


    }

    /*add a Team to the TeamDAO*/
    @Override
    public void insert(Object obj) {

        Team team = (Team) obj;

        if (!teamExists(team)) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", team.getId());
            jsonObject.put("name", team.getName());

            JSONArray jsonArray = getJSONArray();

            if (jsonArray == null) {

                jsonArray = new JSONArray();
            }

            jsonArray.add(jsonObject);

            writeToDBFile(file, jsonArray.toJSONString());

        }

    }

    /*Get Teams from the DAO*/
    @Override
    public Object read() {

        teams = new ArrayList<>();

        if (getJSONArray() != null) {

            getJSONArray().forEach((obj) -> {

                JSONObject teamObject = (JSONObject) obj;

                int id = Integer.parseInt(teamObject.get("id").toString());
                String name = String.valueOf(teamObject.get("name").toString());

                Team team = new Team(id, name);

                teams.add(team);

            });
        }

        return teams;
    }


    /*Update a Team from the DAO*/
    @Override
    public void update(int id) {

        /**
         * To be implemented in the future
         */
    }

    /*Delete a Team from the DAO*/
    @Override
    public void delete(int id) {
        /**
         * To be implemented in the future
         */
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

    private void writeToDBFile(File file, String data) {

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write(data);

        pw.flush();
        pw.close();
    }

    public Team getTeam(int id) {

        return ((List<Team>) read()).stream().filter(team -> team.getId() == id).findFirst().get();

    }

}