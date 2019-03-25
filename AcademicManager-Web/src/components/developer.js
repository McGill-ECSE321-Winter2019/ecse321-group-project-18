import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.build.backendHost
//var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

const TEST_COOPERATOR_ID = 1


export default {
  name: 'developer-only',
  data () {
    return {
      response: [],
      message: ``
    }
  },
  methods: {
    generateTestInstances : function() {
      this.message = `Generating test data... please wait`
      // create a test cooperator
      AXIOS.post('/cooperators/create/?id=' + TEST_COOPERATOR_ID)
      // create students
      let i = 0;
      for (i = 1; i < 9; i++) {
        // create students
        AXIOS.post(`/students/create/?id=${i}&firstname=Mock Student&lastname=${i}&cooperatorid=${TEST_COOPERATOR_ID}`)

        // create terms
        AXIOS.post(`/terms/create/?id=${i}&name=Winter${2013+i}&studentdeadline=2019-01-${i}&coopdeadline=2019-01-${i+1}`)
        // create courses
        AXIOS.post(`/courses/create/?id=MOCK${i}&term=${i}&name=MockCourse${i}&rank=${i}&cooperatorid=${TEST_COOPERATOR_ID}`)
      }
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=1&jobid=1&studentid=1&termid=1&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=2&jobid=1&studentid=2&termid=2&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=3&jobid=1&studentid=3&termid=3&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=4&jobid=1&studentid=4&termid=4&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=5&jobid=1&studentid=5&termid=5&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=6&jobid=1&studentid=6&termid=6&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=7&jobid=1&studentid=7&termid=7&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=8&jobid=1&studentid=8&termid=8&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=9&jobid=1&studentid=9&termid=9s&termstat=0&gradeid=5`)
      AXIOS.post(`/coopTermRegistrations/create/?registrationid=10&jobid=1&studentid=10&termid=10&termstat=0&gradeid=5`)

      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink1.com&ctrid=1`)
      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink2.com&ctrid=2`)
      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink3.com&ctrid=3`)
      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink4.com&ctrid=4`)
      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink5.com&ctrid=5`)
      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink6.com&ctrid=6`)
      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink7.com&ctrid=7`)
      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink8.com&ctrid=8`)
      AXIOS.post(`/students/report/create/?formid=1&pdflink=http://samplelink9.com&ctrid=9`)

      AXIOS.post(`/students/employereval/create/?formid=11&pdflink=http://samplelink11.com&ctrid=1`)
      AXIOS.post(`/students/employereval/create/?formid=12&pdflink=http://samplelink12.com&ctrid=2`)
      AXIOS.post(`/students/employereval/create/?formid=13&pdflink=http://samplelink13.com&ctrid=3`)
      AXIOS.post(`/students/employereval/create/?formid=14&pdflink=http://samplelink14.com&ctrid=4`)
      AXIOS.post(`/students/employereval/create/?formid=15&pdflink=http://samplelink15.com&ctrid=5`)
      AXIOS.post(`/students/employereval/create/?formid=16&pdflink=http://samplelink16.com&ctrid=6`)
      AXIOS.post(`/students/employereval/create/?formid=17&pdflink=http://samplelink17.com&ctrid=7`)
      AXIOS.post(`/students/employereval/create/?formid=18&pdflink=http://samplelink18.com&ctrid=8`)
      AXIOS.post(`/students/employereval/create/?formid=19&pdflink=http://samplelink19.com&ctrid=9`)

      AXIOS.put(`students/update/?id=1&status=true`)
      AXIOS.put(`students/update/?id=2&status=true`)
      AXIOS.put(`students/update/?id=3&status=true`)
      AXIOS.put(`students/update/?id=4&status=true`)
      AXIOS.put(`students/update/?id=5&status=true`)

      this.message = `Test data created`
    }
  }
}
