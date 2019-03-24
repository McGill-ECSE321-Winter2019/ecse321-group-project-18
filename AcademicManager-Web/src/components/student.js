import axios from 'axios'
const invocation = new XMLHttpRequest();
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function StudentDto (studentID, firstName, lastName, studentProblematicStatus) {
  this.studentID = studentID
  this.firstName = firstName
  this.lastName = lastName
  this.studentProblematicStatus = studentProblematicStatus
}

export default {
  name: 'students',
  data () {
    return {
      students: [],
      errorStudent: '',
      response: []
    }
  },
  methods: {
  listStudent: function () {
    var e = document.getElementById("filterBy");
    if (e.options[e.selectedIndex].value == "all"){
    // Initializing people from backend
      AXIOS.get(`/students/list`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.students = response.data
      })
      .catch(e => {
        this.errorStudent = e;
      });
    }
    else{
      AXIOS.get(`/students/problematic`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.students = response.data
      })
      .catch(e => {
        this.errorStudent = e;
      });
    }
  }
}
}
