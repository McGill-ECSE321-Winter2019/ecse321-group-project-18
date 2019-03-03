[1mdiff --git a/Backend/src/main/java/ca/mcgill/ecse321/academicmanager/controller/AcademicManagerRestController.java b/Backend/src/main/java/ca/mcgill/ecse321/academicmanager/controller/AcademicManagerRestController.java[m
[1mindex 7d9eeeb..fec3038 100644[m
[1m--- a/Backend/src/main/java/ca/mcgill/ecse321/academicmanager/controller/AcademicManagerRestController.java[m
[1m+++ b/Backend/src/main/java/ca/mcgill/ecse321/academicmanager/controller/AcademicManagerRestController.java[m
[36m@@ -258,4 +258,80 @@[m [mpublic class AcademicManagerRestController {[m
 		}[m
 		return grades;[m
 	}[m
[32m+[m[41m	[m
[32m+[m	[32m/*---------------------------- COURSE ----------------------------*/[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Wrap a Course object to its DTO version.[m
[32m+[m[32m     * @deprecated we have created a constructor in the CourseDto class.[m
[32m+[m[32m     * @throws IllegalArgumentException if course == null.[m
[32m+[m[32m     * @param course the Course object to be converted.[m
[32m+[m[32m     * @return a CourseDto object representing the Course object.[m
[32m+[m[32m     */[m
[32m+[m[32m    @SuppressWarnings("unused")[m
[32m+[m	[32mprivate static CourseDto course2Dto(Course course) throws IllegalArgumentException {[m
[32m+[m[41m    [m	[32mif (course == null) throw new IllegalArgumentException("No course to convert!");[m
[32m+[m[41m    [m	[32mreturn new CourseDto(course.getCourseID(), course.getTerm(), course.getCourseName(), course.getCourseRank());[m
[32m+[m[32m    }[m
[32m+[m[41m    [m
[32m+[m[32m    /**[m
[32m+[m[32m     * RESTful service: retrieves a specific Course by its ID.[m
[32m+[m[32m     * @author Bach Tran[m
[32m+[m[32m     * @throws IllegalArgumentException if courseID == null or courseID is an empty String.[m
[32m+[m[32m     * @return a CourseDto object having that ID specified.[m
[32m+[m[32m     * */[m
[32m+[m[32m    @GetMapping(value = {"/courses/specific", "/courses/specific/"})[m
[32m+[m[32m    @ResponseBody[m
[32m+[m[32m    public CourseDto getCourse(@RequestParam("id") String courseID,[m[41m [m
[32m+[m[41m    [m		[32m@RequestParam("term") String term) {[m
[32m+[m[41m    [m	[32mif (courseID == null || courseID.isEmpty()) throw new IllegalArgumentException();[m
[32m+[m[41m    [m	[32mreturn new CourseDto(service.getCourse(courseID, term));[m
[32m+[m[32m    }[m
[32m+[m[32m    /**[m
[32m+[m[32m     * RESTful service: retrieves a list of all courses, sorted by their usefulness (courseRank).[m
[32m+[m[32m     * @author Bach Tran[m
[32m+[m[32m     * @return a sorted List<CourseDto> object by courseRank.[m
[32m+[m[32m     * */[m
[32m+[m[32m    @GetMapping(value = {"/courses", "/courses/"})[m
[32m+[m[32m    @ResponseBody[m
[32m+[m[32m    public List<CourseDto> getCourses() {[m
[32m+[m[41m    [m	[32m// dummy...[m
[32m+[m[41m    [m	[32mSet<Course> courseSet = service.getAllCourses();[m
[32m+[m[41m    [m	[32mList<CourseDto> courseList = new ArrayList<CourseDto>();[m
[32m+[m[41m    [m	[32mfor (Course course : courseSet) {[m
[32m+[m[41m    [m		[32mcourseList.add(new CourseDto(course));[m
[32m+[m[41m    [m	[32m}[m
[32m+[m[41m    [m	[32mCollections.sort(courseList);[m
[32m+[m[41m    [m	[32mreturn courseList;[m
[32m+[m[32m    }[m
[32m+[m[32m    /**[m
[32m+[m[32m     * RESTful service: retrieves n first useful course, using courseRank to make comparison.[m
[32m+[m[32m     * @param quantity number of courses wanted to retrieve.[m
[32m+[m[32m     * @return a list of n useful courses.[m
[32m+[m[32m     * */[m
[32m+[m[32m    @GetMapping(value = {"/courses/filter", "courses/filter/"})[m
[32m+[m[32m    @ResponseBody[m
[32m+[m[32m    public List<CourseDto> getCourses(@RequestParam("quantity")int quantity) {[m
[32m+[m[41m    [m	[32mreturn new ArrayList<CourseDto>(getCourses().subList(0, (quantity < getCourses().size()) ? quantity : getCourses().size()));[m
[32m+[m[32m    }[m
[32m+[m[41m    [m
[32m+[m[32m    /**[m
[32m+[m[32m     * RESTful service: Creates a new course.[m
[32m+[m[32m     * @param id (primary key) a unique id of the Course[m
[32m+[m[32m     * @param term (primary key) the term that Course belong to.[m
[32m+[m[32m     * @param name the name of the Course[m
[32m+[m[32m     * @param rank the rank of the Course, this is a String will then be converted to an int.[m
[32m+[m[32m     * @author Bach Tran[m
[32m+[m[32m     * */[m
[32m+[m[32m    @PostMapping(value = { "/courses/create", "/events/create/" })[m
[32m+[m[32m    @ResponseBody[m
[32m+[m[32m    public CourseDto createCourse(@RequestParam("id") String id,[m[41m [m
[32m+[m[41m    [m		[32m@RequestParam("term") String term,[m[41m [m
[32m+[m[41m    [m		[32m@RequestParam("name") String name,[m[41m [m
[32m+[m[41m    [m		[32m@RequestParam("rank") String rank) {[m
[32m+[m[41m    	[m
[32m+[m[41m    [m	[32mCooperator c = service.createCooperator(1);[m
[32m+[m[41m    	[m
[32m+[m[41m    [m	[32mCourse course = service.createCourse(id, term, name, Integer.parseInt(rank), c);[m
[32m+[m[41m    [m	[32mreturn new CourseDto(course);[m
[32m+[m[32m    }[m
 }[m
\ No newline at end of file[m
[1mdiff --git a/Backend/src/main/java/ca/mcgill/ecse321/academicmanager/service/AcademicManagerService.java b/Backend/src/main/java/ca/mcgill/ecse321/academicmanager/service/AcademicManagerService.java[m
[1mindex fa57e10..bf87a2c 100644[m
[1m--- a/Backend/src/main/java/ca/mcgill/ecse321/academicmanager/service/AcademicManagerService.java[m
[1m+++ b/Backend/src/main/java/ca/mcgill/ecse321/academicmanager/service/AcademicManagerService.java[m
[36m@@ -139,8 +139,8 @@[m [mpublic class AcademicManagerService {[m
 		course.setCourseName(courseName);[m
 		course.setCourseRank(rank);[m
 		[m
[31m-//		course.setCooperator(c);[m
[31m-		c.addCourse(course);[m
[32m+[m		[32mcourse.setCooperator(c);[m
[32m+[m		[32m//c.addCourse(course);[m
 		[m
 		return courseRepository.save(course);[m
 	}[m
