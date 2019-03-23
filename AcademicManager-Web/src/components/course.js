import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost //+ ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function CourseDto(id, name, term, rank) {
  this.courseID = id
  this.courseName = name
  this.term = term
  this.courseRank = rank
}

export default {
  name: 'course',
  data () {
    return {
      courses: [],
      courseID: '',
      error: '',
      response: []
    }
  },
  created: function () {
    // Initializing people from backend
    AXIOS.get('/courses')
      .then(response => {
        // JSON responses are automatically parsed.
        this.courses = response.data
      })
      .catch(e => {
        this.error = e;
      });
  },
  methods: {
    createCourse: function (courseID, courseName, term, courseRank) {
      // Create a new person and add it to the list of people
      var c = new CourseDto(courseID, courseName, term, courseRank)
      this.courses.push(c)
      // Reset the name field for new people
      this.newCourse = ''
    },
    filterCourseById: function(courseID) {
      AXIOS.get('/courses' + '/' + courseID)
        .then(response => {
          // JSON responses are automatically parsed.
          if (courseID != '') {
            var c = response.data
            this.courses= [c]
          } else {
            this.courses = response.data
          }
        })
        .catch(e => {
          this.error = e;
        });
    }
  }
}
