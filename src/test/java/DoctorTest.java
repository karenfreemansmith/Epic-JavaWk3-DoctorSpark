import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DoctorTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection cn = DB.sql2o.open()) {
      String deleteDoctorsQuery = "DELETE from doctors *;";
      String deleteSpecialtiesQuery = "DELETE from specialties *;";
      cn.createQuery(deleteDoctorsQuery).executeUpdate();
      cn.createQuery(deleteSpecialtiesQuery).executeUpdate();
    }
  }

  @Test
  public void Doctor_instantiatesDoctor_true() {
    Specialty specialty = new Specialty("Podiatrist");
    specialty.save();
    Doctor doctor = new Doctor ("Dr. Carter", specialty.getId());
    assertTrue(doctor instanceof Doctor);
  }

  @Test
  public void save_savesDoctorsIntoDatabase() {
    Specialty specialty = new Specialty("Podiatrist");
    specialty.save();
    Doctor doctor = new Doctor ("Dr. Carter", specialty.getId());
    doctor.save();
    String sql = "SELECT COUNT (id) FROM doctors;";
      try(Connection cn = DB.sql2o.open()) {
        assertTrue(cn.createQuery(sql).executeScalar(Integer.class)>0);
      }
    }

  @Test
  public void getId_returnsIdOfDoctor() {
    Specialty specialty = new Specialty("Podiatrist");
    specialty.save();
    Doctor doctor = new Doctor ("Dr. Carter", specialty.getId());
    doctor.save();
    assertTrue(doctor.getId() > 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame_true() {
    Specialty specialty = new Specialty("Podiatrist");
    specialty.save();
    Doctor doctor = new Doctor ("Dr. Carter", specialty.getId());
    doctor.save();
    Doctor secondDoctor = new Doctor ("Dr. Carter", specialty.getId());
    doctor.save();
    assertTrue(doctor.equals(secondDoctor));
  }

  @Test
  public void all_returnsAllInstancesOfDoctor_true() {
    Specialty specialty = new Specialty("Podiatrist");
    specialty.save();
    Doctor doctor = new Doctor ("Dr. Carter", specialty.getId());
    doctor.save();
    Doctor secondDoctor = new Doctor ("Dr. Carter", specialty.getId());
    secondDoctor.save();
    assertTrue(Doctor.all().get(0).equals(doctor));
    assertTrue(Doctor.all().get(1).equals(secondDoctor));
  }

  @Test
  public void find_returnsCorrectDoctor_int() {
    Specialty specialty = new Specialty("Podiatrist");
    specialty.save();
    Doctor doctor = new Doctor ("Dr. Carter", specialty.getId());
    doctor.save();
    Doctor secondDoctor = new Doctor ("Dr. Zed", specialty.getId());
    secondDoctor.save();
    assertEquals(Doctor.find(secondDoctor.getId()), secondDoctor);
  }


}
