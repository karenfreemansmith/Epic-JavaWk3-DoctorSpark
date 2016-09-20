import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PatientTest {
  private Patient patient = new Patient();

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/office_test", null, null);
  }

  // @After
  // public void tearDown() {
  //   try(Connection cn = DB.sql2o.open()) {
  //     String deletePatientsQuery = "DELETE from patients *;";
  //   }
  // }

  @Test
  public void Patient_instantiatesAPatientObject_true() {
  assertEquals(true, patient instanceof Patient);
  }

}
