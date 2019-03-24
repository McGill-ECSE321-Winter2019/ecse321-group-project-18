import axios from 'axios'
const invocation = new XMLHttpRequest();
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost  + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function CoopTermRegistrationDto (registrationID, studentID, termName, grade, jobID, termStatus, employerFormLink, studentFormLink){
  this.registrationID = registrationID
  this.termName = termName
  this.jobID = jobID
  this.termStatus = termStatus
  this.grade = grade
  this.studentID = studentID
  this.employerFormLink = employerFormLink
  this.studentFormLink = studentFormLink
}

export default {
  name: 'coopTermRegistrations',
  data () {
    return {
      ctrs: [],
      error: ''
    }
  },
  methods: {
  listCTRs: function (studentID) {
    if(studentID != undefined){
    AXIOS.get(`/coopTermRegistrations/list/` + studentID)
    .then(response => {
        // JSON responses are automatically parsed.
        this.ctrs = response.data
      })
      .catch(e => {
        this.error = e;
      });
    }
    else{
    AXIOS.get(`/coopTermRegistrations/list/`)
    .then(response => {
        // JSON responses are automatically parsed.
        this.ctrs = response.data
      })
      .catch(e => {
        this.error = e;
      });
    }
  },


  adjudicateSuccessCTR: function (ctrID) {
    AXIOS.post(`/coopTermRegistrations/` + ctrID + `/adjudicate/?success=true`)
    .then(response => {
        // JSON responses are automatically parsed.
        this.ctrs = [];
      })
      .catch(e => {
        this.error = e;
      });
  },

  adjudicateFailureCTR: function (ctrID) {
    AXIOS.post(`/coopTermRegistrations/` + ctrID + `/adjudicate/?success=false`)
    .then(response => {
        // JSON responses are automatically parsed.
        this.ctrs = [];
      })
      .catch(e => {
        this.error = e;
      });
  }

  }
  
}