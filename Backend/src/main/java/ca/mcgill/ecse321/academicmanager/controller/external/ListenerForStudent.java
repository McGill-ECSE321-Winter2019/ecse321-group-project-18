package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.service.CooperatorService;
import ca.mcgill.ecse321.academicmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

abstract class ListenerForStudent extends Listener {
    public static final int DEFAULT_COOPERATOR_ID = 1;
    protected HashMap<String, ExternalStudentDto> students = new HashMap<>();
    @Autowired
    protected CooperatorService cooperatorService;
    @Autowired
    protected StudentService studentService;

    @Override
    protected void handleDependencies() {
        // dependency: Cooperator
        if (!cooperatorService.exists(DEFAULT_COOPERATOR_ID)) {
            cooperatorService.create(DEFAULT_COOPERATOR_ID);
        }
    }

    @Override
    protected void postData() {
        // persists new student to the database
        for (String externalStudentID : students.keySet()) {
            if (studentService.exists(externalStudentID)) {
                // updates the existing student
                Student student = studentService.get(externalStudentID);
                studentService.updateFirstName(student, students.get(externalStudentID).firstName);
                studentService.updateLastName(student, students.get(externalStudentID).lastName);
            } else {
                // create new, non-problematic student
                studentService.create(externalStudentID,
                        students.get(externalStudentID).firstName,
                        students.get(externalStudentID).lastName,
                        cooperatorService.get(DEFAULT_COOPERATOR_ID));
            }
        }
    }

    @Override
    protected void removeObsolete() {
        for (Student academicMangerStudent : studentService.getAll()) {
            // deletes all obsolete students from the AcademicManager's database
            if (!students.containsKey(academicMangerStudent.getStudentID())) {
                studentService.delete(academicMangerStudent.getStudentID());
            }
        }
    }
}
