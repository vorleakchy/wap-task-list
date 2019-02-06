package model;

public class Task {

    private int id;
    private String task;
    private String dueDate;
    private String category;
    private int idUser;
    private int priority;

    public Task(int id, String task, String dueDate, String category, int priority) {
        this.id = id;
        this.task = task;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
    }

    /*Edwin Bernal: i am adding a new constructor because the user need it*/
    public Task(int id, String task, String dueDate, String category, int priority, int iduser) {
        this.id = id;
        this.task = task;
        this.dueDate = dueDate;
        this.category = category;
        this.idUser = iduser;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int iduser) {
        this.idUser = iduser;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}