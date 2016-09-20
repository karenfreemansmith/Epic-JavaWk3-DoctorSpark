import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class SpecialtyTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection cn = DB.sql2o.open()) {
      String deleteSpecialtiesQuery = "DELETE from specialties *;";
      cn.createQuery(deleteSpecialtiesQuery).executeUpdate();
    }
  }

  @Test
  public void Specialty_instantiatesAnObject_true() {
    Specialty specialty = new Specialty("Orthopedics");
    assertTrue(specialty instanceof Specialty);
  }

  @Test
  public void getDescription_returnsDescription_true() {
    Specialty specialty = new Specialty("Orthopedics");
    assertEquals("Orthopedics", specialty.getDescription());
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Specialty specialty = new Specialty("Orthopedics");
    Specialty secondSpecialty = new Specialty("Orthopedics");
    assertTrue(specialty.equals(secondSpecialty));
  }

  @Test
  public void all_returnsAllInstances_true() {
    Specialty specialty = new Specialty("Orthopedics");
    Specialty secondSpecialty = new Specialty("Internal Medicine");
    specialty.save();
    secondSpecialty.save();
    assertTrue(Specialty.all().get(0).equals(specialty));
    assertTrue(Specialty.all().get(1).equals(secondSpecialty));
  }

  @Test
  public void save_savesToDatabase_true() {
    Specialty specialty = new Specialty("Orthopedics");
    specialty.save();
    String sql = "SELECT COUNT (id) FROM specialties;";
      try(Connection cn = DB.sql2o.open()) {
        assertTrue(cn.createQuery(sql).executeScalar(Integer.class)>0);
      }
  }

  @Test
  public void find_returnsCorrectSpecialty_true() {
    Specialty specialty = new Specialty("Orthopedics");
    Specialty secondSpecialty = new Specialty("Internal Medicine");
    specialty.save();
    secondSpecialty.save();
    assertEquals(Specialty.find(secondSpecialty.getId()), secondSpecialty);
  }

  @Test
  public void getId_returnsIdofSpecialty_int() {
    Specialty specialty = new Specialty("Orthopedics");
    specialty.save();
    assertTrue(specialty.getId() > 0);
  }

}
