import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

const TEST_COOPERATOR_ID = -1


export default {
  name: 'developer-only',
  data () {
    return {
      courses: [],
      courseID: '',
      term: '',
      error: '',
      quantity: '',
      sort_order: '',
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
    generateTestInstances : function() {
      // create a test cooperator
      AXIOS.post('/cooperators/create?id=' + TEST_COOPERATOR_ID)
      // create students
      let i = 0;
      for (i = 0; i < 10; i++) {
        // create students
        AXIOS.post(`/students/create/?id=${-i}&firstname=Mock Student&lastname=${-i}&cooperatorid=${TEST_COOPERATOR_ID}`)
        // create terms
        AXIOS.post(`/terms/create?id=${-i}&name=MockTerm${-i}&studentdeadline=2019-01-${i}&coopdeadline=2019-01-${i+1}`)
        // create courses
        AXIOS.post(`/courses/create?id=MOCK${-i}&term=-1&name=MockCourse${-i}&rank=${-i}&cooperatorid=${TEST_COOPERATOR_ID}`)
        // create coopTermRegistrations
        AXIOS.post(`/coopTermRegistrations/create?registrationid=${-i}&jobid=${-i}&studentid=-1&termid=${-i}&termstat={termstat}&gradeid={gradeid}`)
        // create forms
        AXIOS.post(`/students/report/create/?formid=${-i}&pdflink=http://samplelink.com&ctrid=${-i}`)
        AXIOS.post(`/students/employereval/create/?formid=${-i}&pdflink=http://samplelink.com&ctrid=${-i}`)
      }
    }
  }
}
