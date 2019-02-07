package db;

import model.Task;
import model.Team;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements DAO {

    final File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "/db/task.json");

    private List<Task> tasks;

    /**
     * inserts task into the data store
     * @param obj
     */
    @Override
    public void insert(Object obj) {

        Task task = (Task) obj;

        if (!taskExists(task)) {

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (getLastTask() != null) ? getLastTask().getId() + 1 : 0 + 1);
                jsonObject.put("name", task.getTask());
                jsonObject.put("dueDate", task.getDueDate());
                jsonObject.put("category", task.getCategory());
                jsonObject.put("priority", task.getPriority());

                User user = task.getUser();

                JSONObject userObject = new JSONObject();
                userObject.put("id", user.getId());
                userObject.put("name", user.getName());

                Team team = task.getUser().getIdTeam();
                JSONObject teamObject = new JSONObject();

                teamObject.put("id", team.getId());
                teamObject.put("name", team.getName());

                userObject.put("team", teamObject);


                jsonObject.put("user", userObject);

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

    /**
     * updates task in the store that is
     * specified by the id passed in the argument
     * @param id
     */
    @Override
    public void update(int id) {

        // To be implemented later

    }


    /**
     * Deletes task from the store specified by the id
     * passed int the arguments
     * @param id
     */
    @Override
    public void delete(int id) {
        // To be implemented later

    }


    /**
     * Clears all data from the datastore
     */
    public void clear() {

        if (!isEmpty()) {
            try {

                FileWriter fileWriter = new FileWriter(file);

                fileWriter.write("");


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * Reads tasks from the store and returns them in an ArrayList.
     * @return ArrayList
     */
    @Override
    public Object read() {

        tasks = new ArrayList<>();

        if (getJSONArray() != null) {

            getJSONArray().forEach((jobj) -> {

                JSONObject taskObject = (JSONObject) jobj;

                int id = Integer.parseInt(taskObject.get("id").toString());
                String name = String.valueOf(taskObject.get("name").toString());
                String date = String.valueOf(taskObject.get("dueDate").toString());
                String category = String.valueOf(taskObject.get("category").toString());
                int priority = Integer.parseInt(taskObject.get("priority").toString());

                User user = new User();
                JSONObject userObject = (JSONObject) taskObject.get("user");

                user.setId(Integer.parseInt(userObject.get("id").toString()));
                user.setName(userObject.get("name").toString());

                JSONObject teamObject = (JSONObject) userObject.get("team");
                Team team = new Team(Integer.parseInt(teamObject.get("id").toString()), teamObject.get("name").toString());

                user.setTeam(team);

                Task task = new Task(id, name, date, category, priority, user);

                tasks.add(task);

            });
        }

        return tasks;
    }

    /**
     * gets the number of tasks in the data store.
     * @return int
     */
    @Override
    public int size() {
        if (!isEmpty()) return (int) getJSONArray().stream().count();

        return 0;
    }

    /**
     * Checks to see whether datastore is empty or not
     * @return boolean
     */
    /*Checks whether DAO is empty*/
    public boolean isEmpty() {

        return file.length() <= 0 ? true : false;
    }

    /**
     * Checks whether Task passed in the parameter args exists
     */
    public boolean taskExists(Task tsk) {

        if (getJSONArray() != null) {

            return ((List<Task>) read()).stream().anyMatch((task) -> task.getTask().equals(tsk.getTask()) ? true : false);
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
     * Gets the last task from the datastore
     * @return Task
     */
    private Task getLastTask() {
        if (getJSONArray() != null) {
            return ((List<Task>) read()).get(tasks.size() - 1);
        }
        return null;
    }

}
