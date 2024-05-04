package db;

import entities.Cat;
import entities.CatOwner;

import java.sql.SQLException;
import java.util.List;

public interface CatsRepo {

    List<Cat> getAllCats() throws SQLException;
    List<CatOwner> getAllCatOwners() throws SQLException;
    Cat getCat(int id) throws SQLException;
    CatOwner getCatOwner(Cat cat) throws SQLException;

    void createNewCat(List<Cat> cat) throws SQLException;
    void createNewCatOwner(Cat cat) throws SQLException;
    void createTwoNewCatOwners(Cat cat) throws SQLException;
    void createNewCatsOwnerWithStoredProcedure(String name) throws SQLException;

    void updateCat(Cat cat) throws SQLException;
    void updateCatOwner(CatOwner catOwner);
    void removeCat(int id) throws SQLException;
    void removeCatOwner(int id);

    List<Cat> getCatsSortedByName() throws SQLException;

}
