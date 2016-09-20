import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Specialty {
  private String description;
  private int id;

  public Specialty(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object otherSpecialty) {
    if(!(otherSpecialty instanceof Specialty)) {
      return false;
    } else {
      Specialty newSpecialty = (Specialty) otherSpecialty;
      return this.getDescription().equals(newSpecialty.getDescription()) &&
             this.getId() == newSpecialty.getId();
    }
  }

  public String getDescription() {
    return description;
  }

  public static List<Specialty> all() {
    String sql = "SELECT id, description FROM specialties";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Specialty.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO specialties (description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public int getId() {
    return id;
  }

  public static Specialty find(int id) {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "SELECT * FROM specialties WHERE id=:id";
      Specialty specialty = cn.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Specialty.class);
      return specialty;
    }
  }


}
