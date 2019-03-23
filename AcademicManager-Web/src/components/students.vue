<template>
  <div class="page-content">
    <h2 class="pagetitle">Students</h2>
    <div class="filters-section">
      <h4 class="search-title">Search</h4>
      <div class ="filters-entries">
        <input type="text" name="id" placeholder="Student ID">
        <input type="text" name="firstname" placeholder="First Name">
        <input type="text" name="lastname" placeholder="Last Name">
        <div>
          <select>
            <option value="all">All Students</option>
            <option value="problematic">Problematic</option>
          </select>
          <button>Search</button>
        </div>
      </div>
    </div>

    <div>
      <b-form inline @submit="createStudent" @reset="onReset" v-if="show">
        <label class="sr-only" for="studentid">Name</label>
        <b-input class="mb-2 mr-sm-2 mb-sm-0" id="studentid" v-model="this.newStudent.studentid" placeholder="Student ID" />

        <label class="sr-only" for="firstname">Name</label>
        <b-input class="mb-2 mr-sm-2 mb-sm-0" id="firstname" v-model="this.newStudent.first_name" placeholder="First Name" />

        <label class="sr-only" for="lastname">Name</label>
        <b-input class="mb-2 mr-sm-2 mb-sm-0" id="lastname" v-model="this.newStudent.last_name" placeholder="Last Name" />

        <b-button variant="primary">Add</b-button>
      </b-form>
    </div>

    <div>
      <b-table striped bordered hover :items="students" :fields="fields" :caption-top="true" :busy="isBusy">
        <div slot="table-busy" class="text-center text-danger my-2">
          <b-spinner class="align-middle" />
          <strong>Loading...</strong>
        </div>
      </b-table>
    </div>
  </div>
</template>


<script>
  import axios from 'axios'

  var config = require('../../config')

  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
        newStudent: {
          studentid: '',
          first_name: '',
          last_name: '',
          is_problematic: null
        },
        errorStudent: '',
        response: [],
        show: true
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
        AXIOS.post('/students/create/?id=${studentid}&firstname=${first_name}&lastname=${last_name}&cooperatorid=1')
        const s = new studentDto(studentid, first_name, last_name, is_problematic)
        this.students.push(s)
        this.newStudent = ''
      },
      onReset: function (evt) {
        evt.preventDefault()
        this.newStudent.studentid = ''
        this.newStudent.first_name = ''
        this.newStudent.last_name = ''
        this.newStudent.is_problematic = null

        this.show = false
        this.$nextTick(() => {
          this.show = true
        })
      }
    }
  }
</script>


<style scoped>
  .filters-entries input{
    width: 30%;
    padding-left: 5px;
    border: 1px solid;
    border-radius: 4px;
  }

  .filters-entries select{
    width: 20%;
    height: 100%;
    padding: 3.5px 5px;
    border: 1px solid;
    border-radius: 4px;
  }

  .filters-entries button{
    margin-left: 5%;
    background-color: #dc241f;
    border: 1px solid #dc241f;
    color: white;
    padding: 5px 20px;
    border-radius: 4px;
  }

  .filters-entries button:hover{
    background-color: #8b0c00;
  }
</style>
