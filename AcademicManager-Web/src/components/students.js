function StudentDto(studentID, firstName, lastName, isProblematic) {
  this.studentID = studentID
  this.firstName = firstName
  this.lastName = lastName
  this.isProblematic = isProblematic
}

export default {
  name: 'studentsTest',
  data() {
    return {
      students: [],
      newStudent: '',
      errorStudent: '',
      response: []
    }
  },
  created: function() {
    //Test data
    const s1 = new StudentDto('260632353', 'Saleh', 'Bakhit', false)
    const s2 = new StudentDto('260632353', 'Omar', 'Bakhit', true)
    this.students = [s1, s2]
  },
  methods: {
    createStudent: function(studentID, firstName, lastName, isProblematic) {
      var s = new StudentDto(studentID, firstName, lastName, isProblematic)
      this.students.push(s)
      this.newStudent = ''
    }
  }
}
