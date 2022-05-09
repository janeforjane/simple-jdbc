package entities;

public class CatOwner {

    private int id;
    private String name;
    private Cat cat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "CatOwner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cat=" + cat +
                '}';
    }
}
