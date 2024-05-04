package logic;

import db.CatsRepo;
import db.CatsRepositoryImpl;
import entities.Cat;
import entities.CatOwner;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        CatsRepo catsRepo = new CatsRepositoryImpl();

        for (Cat cat : catsRepo.getAllCats()) {
            System.out.println(cat);
        }

        Cat cat = catsRepo.getCat(2);
        System.out.println("Cat is: " + cat);
        catsRepo.createTwoNewCatOwners(cat);


    }
}
