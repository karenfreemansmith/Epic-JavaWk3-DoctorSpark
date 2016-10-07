import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PatientTest {
  private Patient patient;

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/office_test", null, null);
    patient=new Patient("John Doe", "1/1/1980", 1);
  }

  @After
  public void tearDown() {
    try(Connection cn = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE from patients *;";
      cn.createQuery(deletePatientsQuery).executeUpdate();
    }
  }

  @Test
  public void Patient_instantiatesPatient_true() {
    assertTrue(patient instanceof Patient);
  }

  @Test
  public void save_savesPatientsIntoDatabase() {
    String sql = "SELECT COUNT (id) FROM patients;";
    int i=0;
      try(Connection cn = DB.sql2o.open()) {
        i=cn.createQuery(sql).executeScalar(Integer.class);
      }
      assertEquals(1, i);
    }

  @Test
  public void getId_returnsIdOfPatient() {
    assertTrue(patient.getId() > 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame_true() {
    assertTrue(patient.equals(Patient.find(patient.getId())));
  }

  @Test
  public void all_returnsAllInstancesOfPatient_true() {
    assertTrue(Patient.all().size()>0);
  }

  @Test
  public void find_returnsCorrectPatient_int() {
    assertEquals(Patient.find(patient.getId()), patient);
  }
}
