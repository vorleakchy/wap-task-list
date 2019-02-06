import com.google.gson.Gson;
import model.Task;
import org.junit.jupiter.api.Test;
import static junit.framework.Assert.assertEquals;

public class GsonTest {
    @Test
    public void testToJson() {
        Gson gson = new Gson();
       // Task task = new Task(101, "first task", "2017-11-19", "Personal", 1,2);

     //   assertEquals(gson.toJson(task), "{\"id\":101,\"task\":\"first task\",\"dueDate\":\"2017-11-19\",\"category\":\"Personal\",\"idUser\":2,\"priority\":1}");
    }

    @Test
    public void testFromJson() {
        Gson gson = new Gson();
        String taskJson = "{\"id\":,\"task\":\"first task\",\"dueDate\":\"2017-11-19\",\"category\":\"Personal\",\"idUser\":2,\"priority\":1}";
        Task task = gson.fromJson(taskJson, Task.class);
        task.setId(1011);

        assertEquals(gson.toJson(task), taskJson);
    }
}
