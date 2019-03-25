import axios from 'axios'
const invocation = new XMLHttpRequest();
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
  created: function () {
    AXIOS.get('/coopTermRegistrations/list')
      .then(response => {
        this.ctrs = response.data
      })
      .catch(e => {
        this.error = e;
      });
  },
  methods: {
  listCTRs: function (studentID) {
    var e = document.getElementById("termFilter")
    if((studentID != undefined && studentID != ``) && e.value == `-`){
    AXIOS.get(`/coopTermRegistrations/listByStudent/?studentid=` + studentID)
    .then(response => {
        // JSON responses are automatically parsed.
        this.ctrs = response.data
      })
      .catch(e => {
        this.error = e;
      });
    }
    else if((studentID == undefined || studentID == ``) && e.value != `-`) {
    AXIOS.get(`/coopTermRegistrations/listByTerm/?termname=` + e.value)
    .then(response => {
        // JSON responses are automatically parsed.
        this.ctrs = response.data
      })
      .catch(e => {
        this.error = e;
      });
    }
    else if((studentID != undefined && studentID != ``) && e.value != `-`) {
    AXIOS.get(`/coopTermRegistrations/listByTermAndStudent/?termname=` + e.value + `&studentid=` + studentID)
    .then(response => {
        // JSON responses are automatically parsed.
        this.ctrs = response.data
      })
      .catch(e => {
        this.error = e;
      });
    }
    else if((studentID == undefined || studentID == ``) && e.value == `-`){
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
        AXIOS.get('/coopTermRegistrations/list')
      .then(response => {
        this.ctrs = response.data
      })
      .catch(e => {
        this.error = e;
      });
      })
      .catch(e => {
        this.error = e;
      });
  },

  adjudicateFailureCTR: function (ctrID) {
    AXIOS.post(`/coopTermRegistrations/` + ctrID + `/adjudicate/?success=false`)
    .then(response => {
        // JSON responses are automatically parsed.
        AXIOS.get('/coopTermRegistrations/list')
      .then(response => {
        this.ctrs = response.data
      })
      .catch(e => {
        this.error = e;
      });
      })
      .catch(e => {
        this.error = e;
      });
  }

  }
  
}