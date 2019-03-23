<template>
<div id=studentslist>
  <h2>Students List</h2>
  <button @click="studentsList()">Students List</button>
  <ul v-if="posts && posts.length">
    <li v-for="post of posts" v-bind:key="post.id">
      <p><strong>{{post.title}}</strong></p>
      <p>{{post.body}}</p>
    </li>
  </ul>
  
  <ul>
  <li v-if="errorStudentsList" style="color:red">Error: {{errorStudentsList}} </li>
  <li v-else-if="newStudentsList"" style="color:red">Success: {{thisStudentsList}} </li>
  </ul>
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

export default {
  name: 'studentslist',
  data () {
    return {
      thisStudentsList: [],
      newStudentsList: '',
      errorStudentsList: '',
      response: []
    }
  },
  methods: {
    studentsList: function () {
       AXIOS.get('/students/list',{},{}) 
       
	  .then(response => {
	    // JSON responses are automatically parsed.
	    this.thisStudentsList=[]
	    this.thisStudentsList.push(response.data)
	    this.newStudentsList = 'ok'
	    this.errorStudentsList = ''
	  })
	  .catch(e => {
	    var errorMsg = e.message
	    console.log(errorMsg)
	    this.errorStudentsList = errorMsg
	  });
	}
  }
}

</script>


<style>
  #studentslist {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>


