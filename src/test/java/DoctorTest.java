import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DoctorTest {
  private Doctor doctor = new Doctor();

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/office_test", null, null);
  }

  // @After
  // public void tearDown() {
  //   try(Connection cn = DB.sql2o.open()) {
  //     String deleteDoctorsQuery = "DELETE from doctors *;";
  //   }
  // }


  // @Test
  // public void Task_instantiates_true() {
  //   assertEquals(true, task instanceof Task);
  // }

  // @Test
  // public void equals_returnsTrueIfDescriptionsAretheSame() {
  //   Task firstTask = new Task("Mow the lawn");
  //   Task secondTask = new Task("Mow the lawn");
  //   assertTrue(firstTask.equals(secondTask));
  // }

  // @After
  // public void tearDown() {
  //   Task.clear();
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "DELETE FROM tasks *;";
  //     con.createQuery(sql).executeUpdate();
  //   }
  // }
}
