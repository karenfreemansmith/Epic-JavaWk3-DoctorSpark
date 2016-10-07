import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("patients", Patient.all());
      model.put("doctors", Doctor.all());
      model.put("specialties", Specialty.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add/patient", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Patient patient = new Patient(request.queryParams("patient"), request.queryParams("birthday"), Integer.parseInt(request.queryParams("doctorId")));
      patient.save();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add/doctor", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      System.out.println(request.queryParams("doctor"));
      Doctor doctor = new Doctor(request.queryParams("doctor"), Integer.parseInt(request.queryParams("specialtyId")));
      System.out.println(doctor.getName());
      doctor.save();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add/specialty", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Specialty specialty = new Specialty(request.queryParams("specialty"));
      specialty.save();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
