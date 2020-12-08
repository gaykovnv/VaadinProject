package com.lessonprojectwithvaadin.demo.connectToDB;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBWork {

    @Getter
    private Connection connection = null;
    private String url = "jdbc:mysql://localhost:3306/vaadindb?serverTimezone=UTC";
    private String username = "root";
    private String password = "1234";
    private String driver = "com.mysql.cj.jdbc.Driver";
    public DBWork(){
        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.out.println("Mistake driver: "+e.getMessage());
        }

        try {
            connection = DriverManager.getConnection(url,username,password);
        }catch (Exception exception){
            System.out.println("Mistake connect: "+exception.getMessage());
        }
    }
}
