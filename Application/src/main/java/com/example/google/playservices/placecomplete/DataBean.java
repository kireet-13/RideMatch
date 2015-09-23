package com.example.google.playservices.placecomplete;

/**
 * Created by Dr.Abhay on 08-06-2015.
 */
public class DataBean {
    String age;
    String username;
    String gender;
    String outofway;
    String source;
    String dest;

    public String getRideid() {
        return rideid;
    }

    public void setRideid(String rideid) {
        this.rideid = rideid;
    }

    String rideid;



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOutofway() {
        return outofway;
    }

    public void setOutofway(String outofway) {
        this.outofway = outofway;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }




    public DataBean(String a, String b, String c, String d, String e, String f, String g) {
        age=a;
        username=b;
        gender=c;
        outofway=d;
        source=e;
        dest=f;
        rideid=g;
    }


}
