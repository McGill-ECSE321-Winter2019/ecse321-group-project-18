<template>
<div id=termscreate>
  <h2>Term</h2>
  <input type="text" v-model="postID" placeholder="TermID"/>
  <input type="text" v-model="postName" placeholder="TermName"/>
  <input type="text" v-model="postSdline" placeholder="StudentDeadline"/>
  <input type="text" v-model="postCdline" placeholder="CoopDeadline"/>
  <button @click="createTerm(postID,postName,postSdline,postCdline)">Create Term</button>
  <ul v-if="posts && posts.length">
    <li v-for="post of posts" v-bind:key="post.id">
      <p><strong>{{post.title}}</strong></p>
      <p>{{post.body}}</p>
    </li>
  </ul>
  
  <ul>
  <li v-if="errorTerm" style="color:red">Error: {{errorTerm}} </li>
  <li v-else-if="newTerm"" style="color:red">Success: {{thisTerm}} </li>
  </ul>
</div>
</template>

<script>
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'https://' + config.build.host + ':' + config.build.port
var backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort


var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'termscreate',
  data () {
    return {
      thisTerm: [],
      newTerm: '',
      errorTerm: '',
      response: []
    }
  },
  methods: {
    createTerm: function (postID,postName,postSdline,postCdline) {
       AXIOS.post('/terms/create/?id=' + postID + '&name=' + postName + '&studentdeadline=' + postSdline + '&coopdeadline=' + postCdline,{},{}) 
       
	  .then(response => {
	    // JSON responses are automatically parsed.
	    this.thisTerm=[]
	    this.thisTerm.push(response.data)
	    this.newTerm = 'ok'
	    this.errorTerm = ''
	  })
	  .catch(e => {
	    var errorMsg = e.message
	    console.log(errorMsg)
	    this.errorTerm = errorMsg
	  });
	}
  }
}

</script>


<style>
  #termscreate {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>


