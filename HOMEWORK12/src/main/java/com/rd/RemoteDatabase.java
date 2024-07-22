package com.rd;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.customizer.Bind;


public class RemoteDatabase {

    public interface PersonDAO {
        @SqlUpdate("CREATE TABLE IF NOT EXISTS person (id INT PRIMARY KEY, name VARCHAR(50), age INT)")
        void createTable();

        @SqlUpdate("INSERT INTO person (id, name, age) VALUES (:id, :name, :age)")
        void insertPerson(@Bind("id") int id, @Bind("name") String name, @Bind("age") int age);

        @SqlQuery("SELECT name FROM person WHERE id = :id")
        String findNameById(@Bind("id") int id);

        @SqlUpdate("UPDATE person SET name = :name WHERE id = :id")
        void updatePerson(@Bind("id") int id, @Bind("name") String name);

        @SqlUpdate("DELETE FROM person WHERE id = :id")
        void deletePerson(@Bind("id") int id);
    }

    public static void main(String[] args) {
        String remoteUrl = "jdbc:mysql://sql7.freemysqlhosting.net/sql7721541";
        String user = "sql7721541";
        String password = "fM9SN9rcnI";

        Jdbi jdbi = Jdbi.create(remoteUrl, user, password);
        jdbi.installPlugin(new SqlObjectPlugin());

        PersonDAO dao = jdbi.onDemand(PersonDAO.class);

        dao.createTable();
        dao.insertPerson(1, "hacer bay", 29);
        System.out.println(dao.findNameById(1));  // Output: hacer bay
        dao.insertPerson(2, "Nimet bay", 29);
        dao.updatePerson(2, "serdar bay");
        dao.deletePerson(1);
    }
}

