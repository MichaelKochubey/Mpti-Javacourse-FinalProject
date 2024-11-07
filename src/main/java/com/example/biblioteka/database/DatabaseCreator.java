package com.example.biblioteka.database;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseCreator {
    @SneakyThrows
    public static void createDb(Connection connection) {
        Statement st = connection.createStatement();
        st.executeUpdate("DROP TABLE Person IF EXISTS");
        st.executeUpdate("DROP TABLE Book IF EXISTS");
        st.executeUpdate("DROP TABLE Author IF EXISTS");
        st.executeUpdate("DROP TABLE Reading IF EXISTS");
        st.executeUpdate("CREATE TABLE Person(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), address VARCHAR(20))");
        st.executeUpdate("CREATE TABLE Book(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), yearOfPubl INT, authorId INT, description VARCHAR(512))");
        st.executeUpdate("CREATE TABLE Author(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20))");
        st.executeUpdate("CREATE TABLE Reading(id INT AUTO_INCREMENT PRIMARY KEY, personId INT, bookId INT)");

        st.executeUpdate("INSERT INTO Person (name, address) VALUES('Петров И.И.', 'Школьная 20')");
        st.executeUpdate("INSERT INTO Person (name, address) VALUES('Суворов А.А.', 'Успенская 5')");
        st.executeUpdate("INSERT INTO Person (name, address) VALUES('Кутузов М.И.', 'Гагарина 7')");

        st.executeUpdate("INSERT INTO Author (name) VALUES('Нет Автора')");
        st.executeUpdate("INSERT INTO Author (name) VALUES('Пушкин А.С.')");
        st.executeUpdate("INSERT INTO Author (name) VALUES('Толстой Л.Н.')");
        st.executeUpdate("INSERT INTO Author (name) VALUES('Чехов А.П.')");

        st.executeUpdate("INSERT INTO Book (name, yearOfPubl, authorId, description) VALUES('Война и мир', 1973, 3, 'роман-эпопея Льва Николаевича Толстого, описывающий русское общество в эпоху войн против Наполеона в 1805—1812 годах.')");
        st.executeUpdate("INSERT INTO Book (name, yearOfPubl, authorId, description) VALUES('Евгений Онегин', 2001, 2, 'роман в стихах русского поэта А. С. Пушкина, одно из самых значительных произведений русской словесности')");
        st.executeUpdate("INSERT INTO Book (name, yearOfPubl, authorId, description) VALUES('Русские народные сказки', 2015, 1, 'Сивка-бурка, Теремок, Петушок-золотой гребешок и другие сказки для детей и не только')");

        st.executeUpdate("INSERT INTO Reading(personId, bookId) VALUES(1, 1)");
    }

    @SneakyThrows
    public static void main(String[] args) {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:C:\\Users\\User\\IdeaProjects\\biblioteka\\DB");
        createDb(connection);
    }
}
