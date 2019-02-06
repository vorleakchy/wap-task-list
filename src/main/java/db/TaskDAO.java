package db;

import model.Task;
import model.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements DAO {

    final File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "/db/task.json");

    private List<Task> tasks;

    @Override
    public void insert(Object obj) {

        Task task = (Task) obj;

        if (!taskExists(task)) {

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (getLastTask()!=null)?getLastTask().getId()+1:0+1);
                jsonObject.put("name", task.getTask());
                jsonObject.put("dueDate", task.getDueDate());
                jsonObject.put("category", task.getCategory());
                jsonObject.put("priority", task.getPriority());

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

    @Override
    public void delete(int id) {

    }

    @Override
    public Object read() {

        tasks=new ArrayList<>();

        if (getJSONArray() != null) {

            getJSONArray().forEach((jobj) -> {

                JSONObject teamObject = (JSONObject) jobj;

                int id = Integer.parseInt(teamObject.get("id").toString());
                String name = String.valueOf(teamObject.get("name").toString());
                String date = String.valueOf(teamObject.get("dueDate").toString());
                String category = String.valueOf(teamObject.get("category").toString());
                int priority = Integer.parseInt(teamObject.get("priority").toString());

                Task task = new Task(id, name, date, category, priority);

                tasks.add(task);

            });
        }

        return tasks;
    }

    @Override
    public int size() {
        if (!isEmpty()) return (int) getJSONArray().stream().count();

        return 0;
    }

    /*Checks whether DAO is empty*/
    public boolean isEmpty() {

        return file.length() <= 0 ? true : false;
    }

    /*Checks whether Task passed in the parameter args exists*/
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

    private Task getLastTask(){

        if (getJSONArray() != null) {

            return ((List<Task>) read()).get(tasks.size() - 1);

        }

        return null;
    }

}
