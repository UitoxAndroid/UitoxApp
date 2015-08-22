package com.uitox.asapapp;

/**
 * Created by babyandy on 2015/8/15.
 */

public class TopMenu {
    private String id;
    private String name;
    private int order;
    private String classname;

    public TopMenu() {

    }

    public TopMenu(String id, String name, int order, String classname) {
        super();
        this.id = id;
        this.name = name;
        this.order = order;
        this.classname = classname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}

