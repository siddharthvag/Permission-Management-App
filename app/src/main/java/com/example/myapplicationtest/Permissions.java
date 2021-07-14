package com.example.myapplicationtest;

public class Permissions {
    private String council;
    private String description;
    private String date;
    private String created;
    private String docid;
    private String status;
    private String HOD;
    private String room;
    private String title;
    private String time;
    private String hod_name;

    public String getHod_name() {
        return hod_name;
    }

    public void setHod_name(String hod_name) {
        this.hod_name = hod_name;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getHOD() {
        return HOD;
    }

    public void setHOD(String HOD) {
        this.HOD = HOD;
    }


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    public Permissions() {
        //required for Firebase
    }

    public Permissions(String council, String description, String title, String room) {
        this.council = council;
        this.description = description;
        this.room = room;
        this.title = title;
    }

    public String getCouncil() {
        return council;
    }

    public void setCouncil(String council) {
        this.council = council;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
