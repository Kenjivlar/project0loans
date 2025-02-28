package org.example.Model;

public class Loan {

    public int id;
    public int id_user;
    public String title;
    public boolean completed;
    public String status;

    public Loan(){

    }

    public Loan(int id, int is_user, String title, boolean completed, String status) {
        this.id = id;
        this.id_user = is_user;
        this.title = title;
        this.completed = completed;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                ", status='" + status + '\'' +
                '}';
    }
}
