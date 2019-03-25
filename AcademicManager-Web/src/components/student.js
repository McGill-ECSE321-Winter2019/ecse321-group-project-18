import axios from 'axios'

var config = require('../../config')


if (process.env.NODE_ENV != 'production') {
  	var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
	var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
} 
else
{
	var frontendUrl = 'https://' + config.build.host + ':' + config.build.port
	var backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort
}


var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function studentDto(studentid, first_name, last_name, is_problematic) {
  this.studentid = studentid
  this.first_name = first_name
  this.last_name = last_name
  this.is_problematic = is_problematic
}

export default {
  name: 'students',
  data() {
    return {
      students: [],
      fields: ['studentID', 'firstName', 'lastName', 'studentProblematicStatus'],
      newStudent: {
        studentid: '',
        first_name: '',
        last_name: '',
        is_problematic: null
      },
      errorStudent: '',
      response: []
    }
  },
  created: function () {
    this.isBusy = true
    AXIOS.get('/students/list')
      .then(response => {
        this.students = response.data
      })
      .catch(e => {
        this.errorStudent = e
      });
    this.isBusy = false
  },
  methods: {
    createStudent: function(studentid, first_name, last_name, is_problematic) {
      this.isBusy = true
      AXIOS.post('/students/create/?id=${studentid}&firstname=${first_name}&lastname=${last_name}&cooperatorid=1')
      if(is_problematic == true) {
        AXIOS.put('/students/update/?id=${studentid}&?status=${is_problematic}')
      }
      const s = new studentDto(studentid, first_name, last_name, is_problematic)
      this.students.push(s)

      this.newStudent = ''
      this.isBusy = false
    },
    updateStatus: function(studentid, is_problematic) {
      AXIOS.put('/students/update/?id=${studentid}&?status=${is_problematic}')
      // this.students.
    },
    onReset: function(evt) {
      evt.preventDefault()
      this.newStudent.studentid = ''
      this.newStudent.first_name = ''
      this.newStudent.last_name = ''
      this.newStudent.is_problematic = null

      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    },
    listStudents: function (id) {
    var e = document.getElementById("filterBy");
    if ((id == undefined || id == ``)  && 
      e.options[e.selectedIndex].value == "all"){
      AXIOS.get(`/students/list`)
      .then(response => {
        this.students = response.data
      })
      .catch(e => {
        this.errorStudent = e;
      });
    }
    else if ((id == undefined || id == ``)  && 
      e.options[e.selectedIndex].value == "problematic"){
      AXIOS.get(`/students/problematic`)
      .then(response => {
        this.students = response.data
      })
      .catch(e => {
        this.errorStudent = e;
      });
    }
    else if ((id != undefined && id != ``)  && 
      e.options[e.selectedIndex].value == "all"){
      AXIOS.get(`/students/listByID/?studentid=` + id)
      .then(response => {
        this.students = response.data
      })
      .catch(e => {
        this.students = [];
      });
    }
    else if ((id != undefined && id != ``)  && 
      e.options[e.selectedIndex].value == "problematic"){
      AXIOS.get(`/students/problematic/listByID/?studentid=` + id)
      .then(response => {
        this.students = response.data
      })
      .catch(e => {
        this.errorStudent = e;
      });
    }
    }
  }
}