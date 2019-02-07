package db;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO {

    final File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "/db/user.json");

    private List<User> users = null;

    public UserDAO() {
    }

    /*add a User to the UserDAO*/
    @Override
    public void insert(Object obj) {

        User user = (User) obj;

        if (!userExists(user)) {

            try {

                JSONObject userObject = new JSONObject();

                userObject.put("id", getLastUser() != null ? getLastUser().getId() + 1 : 1);
                userObject.put("name", user.getName());
                userObject.put("team", user.getTeam().getId());

                JSONArray jsonArray = getJSONArray();

                if (jsonArray == null) {

                    jsonArray = new JSONArray();
                }

                jsonArray.add(userObject);

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

    /*Update a User from the DAO*/
    @Override
    public void update(int id) {

    }

    /*Delete a User from the DAO*/
    @Override
    public void delete(int id) {


    }

    /*Get Users from the DAO*/
    @Override
    public Object read() {

        users = new ArrayList<>();

        if (getJSONArray() != null) {

            getJSONArray().forEach((jobj) -> {

                JSONObject userObject = (JSONObject) jobj;

                int id = Integer.parseInt(userObject.get("id").toString());
                String name = String.valueOf(userObject.get("name").toString());

                JSONObject teamObject = null;

                int teamId = Integer.parseInt(userObject.get("team").toString());

                Team team = new TeamDAO().getTeam(teamId);

                User user = new User(id, name, team);

                users.add(user);

            });
        }

        return users;
    }

    /*Returns the number of users in the DAO*/
    @Override
    public int size() {

        if (!isEmpty()) return (int) getJSONArray().stream().count();

        return 0;
    }

    /*Checks whether DAO is empty*/
    public boolean isEmpty() {

        return file.length() <= 0 ? true : false;
    }

    /*Checks whether User passed in the parameter args exists*/
    public boolean userExists(User user) {

        if (getJSONArray() != null) {

            return ((List<User>) read()).stream().anyMatch((usr) -> usr.equals(user));
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

    /**
     * Gets the last user from the datastore
     *
     * @return Task
     */
    private User getLastUser() {
        if (getJSONArray() != null) {
            return ((List<User>) read()).get(users.size() - 1);
        }
        return null;
    }

}