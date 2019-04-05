package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.model.Course;
import ca.mcgill.ecse321.academicmanager.service.CooperatorService;
import ca.mcgill.ecse321.academicmanager.service.CourseService;
import ca.mcgill.ecse321.academicmanager.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

abstract class ListenerForCourse extends Listener {
    public static final int DEFAULT_COOPERATOR_ID = 1;
    /** The list of Courses need to be synced with the internal database. */
    HashSet<ExternalCourseDto> courses = new HashSet<>();
    /** All the required Terms to be created before creating Courses. */
    HashSet<ExternalTermDto> terms = new HashSet<>();
    @Autowired
    CourseService courseService;
    @Autowired
    TermService termService;
    @Autowired
    protected CooperatorService cooperatorService;

    @Override
    protected void handleDependencies() {
        if (!cooperatorService.exists(DEFAULT_COOPERATOR_ID)) {
            cooperatorService.create(DEFAULT_COOPERATOR_ID);
        }
        for (ExternalTermDto externalTerm : terms) {
            if (!termService.exists(externalTerm.getTermID())) {
                termService.create(externalTerm.getTermID(), externalTerm.getTermName(),
                        externalTerm.getDate1(), externalTerm.getDate2()
                );
            }
        }
    }

    @Override
    protected void removeObsolete() {
        for (Course course : courseService.getAll()) {
            if (courses.contains(new ExternalCourseDto(course))) {
                courseService.delete(course);
            }
        }
    }

    @Override
    protected void postData() {
        for (ExternalCourseDto externalCourse : courses) {
            String externalId = externalCourse.getCourseID();
            String externalTerm = externalCourse.getTerm();
            String externalName = externalCourse.getCourseName();
            int externalRank = externalCourse.getCourseRank();
            if (courseService.exists(externalId, externalTerm)) {
                courseService.updateName(courseService.get(externalId, externalTerm), externalCourse.getCourseName());
                courseService.updateRank(courseService.get(externalId, externalTerm), externalCourse.getCourseRank());
            } else {
                courseService.create(externalId, externalTerm, externalName, externalRank,
                        cooperatorService.get(DEFAULT_COOPERATOR_ID));
            }
        }
    }
}
