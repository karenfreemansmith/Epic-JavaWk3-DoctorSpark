import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Patient {
  private int id;
  private String name;
  private String birthday;
  private int doctorId;

  public Patient(String name, String birthday,int dId) {
    this.name = name;
    this.birthday = birthday;
    this.doctorId = dId;
  }

  public String getName() {
    return this.name;
  }

  public String getBirthday() {
    return this.birthday;
  }

  public int getDoctorId() {
    return this.doctorId;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients (name, birthday, doctor_id) VALUES (:name, :birthday, :doctorId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("birthday", this.birthday)
        .addParameter("doctorId", this.doctorId)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherPatient) {
    if(!(otherPatient instanceof Patient)) {
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return this.id==newPatient.getId() &&
             this.name.equals(newPatient.getName()) &&
             this.birthday.equals(newPatient.getBirthday());
    }
  }

  public static List<Patient> all() {
    String sql = "SELECT * FROM patients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addColumnMapping("doctor_id", "doctorId")
        .executeAndFetch(Patient.class);
    }
  }

  public static Patient find(int id) {
    String sql = "SELECT * FROM patients WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
       Patient patient = con.createQuery(sql)
       .addParameter("id", id)
       .addColumnMapping("doctor_id", "doctorId")
       .executeAndFetchFirst(Patient.class);
       return patient;
    }
  }
}
