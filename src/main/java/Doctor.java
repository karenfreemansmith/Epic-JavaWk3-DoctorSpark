import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
  private String name;
  private int id;
  private int specialtyId;

  public Doctor(String name, int sId) {
    this.name = name;
    this.specialtyId = sId;
  }

  public String getName() {
    return name;
  }

  public int getSpecialtyId() {
    return specialtyId;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors (name, specialty_id) VALUES (:name, :specialtyId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("specialtyId", this.specialtyId)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if(!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName());
    }
  }

  public static List<Doctor> all() {
    String sql = "SELECT * FROM doctors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addColumnMapping("specialty_id", "specialtyId")
        .executeAndFetch(Doctor.class);
    }
  }

  public static Doctor find(int id) {
    String sql = "SELECT * FROM doctors WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
       Doctor doctor = con.createQuery(sql)
       .addParameter("id", id)
       .addColumnMapping("specialty_id", "specialtyId")
       .executeAndFetchFirst(Doctor.class);
       return doctor;
    }
  }

}
