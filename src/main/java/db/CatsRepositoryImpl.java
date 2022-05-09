package db;

import entities.Cat;
import entities.CatOwner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatsRepositoryImpl implements CatsRepo {

    DBCPDataSource dataSource = new DBCPDataSource();
    HikariCPDataSource hikariCPDataSource = new HikariCPDataSource();

    // connection's pool
    // apache dbcp
    // hikari pool

    /**
     * Для использования SQL запросов существуют 3 типа объектов:
     * 1.Statement: используется для простых случаев без параметров
     * 2.PreparedStatement: предварительно компилирует запросы,
     * которые могут содержать входные параметры
     * 3.CallableStatement: используется для вызова хранимых функций,
     * которые могут содержать входные и выходные параметры
     */

    @Override
    public List<Cat> getAllCats() throws SQLException {

        /** SIMPLE STATEMENT: SELECT
         * getNewConnection()
         * createStatement()
         * executeQuery()
         * resultSet
         */

        List<Cat> cats = new ArrayList<>();

        //try используется для того чтобы не выполнять close всего

        try(
                Connection newConnection = hikariCPDataSource.getConnection();
                Statement statement = newConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM cats");
                ) {
            while (resultSet.next()) {
                //каждая итерация цикла - строка таблицы
                Cat cat = new Cat();

                cat.setId(resultSet.getInt("idCat"));
                cat.setName(resultSet.getString("name"));

                cats.add(cat);
            }

        }
        return cats;
    }

    @Override
    public List<CatOwner> getAllCatOwners() throws SQLException {


        List<CatOwner> catsOwners = new ArrayList<>();

        Connection newConnection = getNewConnection();
        Statement statement = newConnection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "SELECT id, cats_owners.name, cat_id, c.name FROM cats_owners INNER JOIN cats c on cats_owners.cat_id = c.\"idCat\"");

        while (resultSet.next()) {

            CatOwner catOwner = new CatOwner();
            Cat cat = new Cat();

            catOwner.setId(resultSet.getInt("id"));
            catOwner.setName(resultSet.getString("name"));

            cat.setId(resultSet.getInt(3));
            cat.setName(resultSet.getString(4));

            catOwner.setCat(cat);

            catsOwners.add(catOwner);
        }

        statement.close();
        resultSet.close();
        newConnection.close();

        return catsOwners;
    }

    @Override
    public Cat getCat(int id) throws SQLException {


        /** PREPARED STATEMENT: SELECT
         * getNewConnection()
         * prepareStatement()
         * preparedStatement.setInt()
         * preparedStatement.setString()
         * executeQuery()
         * resultSet
         */

        Connection connection = getNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM cats where \"idCat\"= ?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();


        Cat cat = new Cat();

        while (resultSet.next()){

            cat.setId(resultSet.getInt(1));
            cat.setName(resultSet.getString(2));
        }


        preparedStatement.close();
        resultSet.close();
        connection.close();

        return cat;
    }

    @Override
    public CatOwner getCatOwner(Cat cat) throws SQLException {

        Connection connection = getNewConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM cats_owners where cat_id = ?");

        preparedStatement.setInt(1, cat.getId());

        //выполняем запрос
        ResultSet resultSet = preparedStatement.executeQuery();

        CatOwner catOwner = new CatOwner();

        while (resultSet.next()){

            catOwner.setId(resultSet.getInt(1));
            catOwner.setName(resultSet.getString(2));
            catOwner.setCat(cat);
        }

        preparedStatement.close();
        resultSet.close();
        connection.close();

        return catOwner;
    }

    @Override
    public void createNewCat(List<Cat> cats) throws SQLException {

        /** PREPARED STATEMENT: INSERT
         * getNewConnection()
         * prepareStatement()
         * preparedStatement.setInt()
         * preparedStatement.setString()
         * executeUpdate()
         */

        Connection connection = getNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO public.cats (name, numberphone, deliveryaddress, color) " +
                        "VALUES (?,?,?,?);");

        //для того чтобы выполнить только один запрос используем batch
        for (Cat cat: cats) {

            preparedStatement.setString(1, cat.getName());
            preparedStatement.setString(2, "fffff");
            preparedStatement.setString(3, "fff");
            preparedStatement.setString(4, cat.getColor());

            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        preparedStatement.close();
        connection.close();

    }

    @Override
    public void createNewCatsOwnerWithStoredProcedure(String name) throws SQLException {

        CallableStatement callableStatement = null;
        //Вызываем функцию myFunc (хранится в БД)
        callableStatement = getNewConnection().prepareCall(
                "CALL insert_data(?)");
        //Задаём входные параметры
        callableStatement.setString(1, name);
        //Выполняем запрос для insert/update
        callableStatement.executeUpdate();
        //Выполняем запрос для select
        //ResultSet result = callableStatement.executeQuery();
        //result.next();
    }

    @Override
    public void createNewCatOwner(Cat cat) {

    }

    @Override
    public void updateCat(Cat cat) throws SQLException {


        Connection connection = getNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE public.cats SET name = 'Sofa' WHERE \"idCat\" = ?");

        preparedStatement.setInt(1, 8);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();


    }

    @Override
    public void updateCatOwner(CatOwner catOwner) {

    }

    @Override
    public void removeCat(int id) throws SQLException {

        Connection connection = getNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM public.cats WHERE \"idCat\" = ?");

        preparedStatement.setInt(1, 9);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

    }

    @Override
    public void removeCatOwner(int id) {

    }

    public List<Cat> getCatsSortedByName() throws SQLException {

        List<Cat> cats = new ArrayList<>();

        Connection newConnection = getNewConnection();
        Statement statement = newConnection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM cats ORDER BY name");

        //resultSet это указатель на первую строку с выборки

        while (resultSet.next()) {
            //каждая итерация цикла - строка таблицы
            Cat cat = new Cat();

            cat.setId(resultSet.getInt("idCat"));
            cat.setName(resultSet.getString("name"));

            cats.add(cat);
        }

        statement.close();
        resultSet.close();
        newConnection.close();

        return cats;

    }

    public Connection getNewConnection() throws SQLException {
        String url ="jdbc:postgresql://localhost:5432/postgres";
        String user = "user";
        String pwd = "passw0rd";

        Connection connection = DriverManager.getConnection(url,user,pwd);

        return connection;

    };

}
