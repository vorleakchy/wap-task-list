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

    File file = new File("src/main/java/db/user.json");

    private List<User> users = new ArrayList<>();

    public UserDAO() {
    }

    /*add a User to the UserDAO*/
    @Override
    public void insert(Object obj) {

        User user = (User) obj;

        if (!userExists(user)) {

            try {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", user.getId());
                jsonObject.put("name", user.getName());
                jsonObject.put("idTeam", user.getIdTeam());

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

    /*Update a User from the DAO*/
    @Override
    public void update() {

    }

    /*Delete a User from the DAO*/
    @Override
    public void delete(int id) {


    }

    /*Get Users from the DAO*/
    @Override
    public Object read() {

        if (getJSONArray() != null) {

            getJSONArray().forEach((jobj) -> {

                JSONObject userObject = (JSONObject) jobj;

                int id = Integer.parseInt(userObject.get("id").toString());
                String name = String.valueOf(userObject.get("name").toString());
                int idteam = Integer.parseInt(userObject.get("idTeam").toString());

                User user = new User(id, name,idteam);

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
    public boolean userExists(User userChek) {

        if (getJSONArray() != null) {

            return ((List<User>) read()).stream().anyMatch((user) -> user.equals(userChek));
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
