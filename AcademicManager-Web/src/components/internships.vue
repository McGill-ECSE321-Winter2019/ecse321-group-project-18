<template>
  <div class="page-content">
	<h2>Internships</h2>

	<div class="filters-section">
	  <h4 class="search-title">Search</h4>
	  <div class ="filters-entries">
	    <input type="text" v-model="studentID" placeholder="Internship ID">
	    <select id="termFilter">
	      <option value="-">-</option>	
	      <option value="Winter2019">Winter 2019</option>
	      <option value="Fall2019">Fall 2019</option>
	      <option value="Winter2018">Winter 2018</option>
	      <option value="Fall2018">Fall 2018</option>
	      <option value="Winter2017">Winter 2017</option>
	      <option value="Fall2017">Fall 2017</option>
	      <option value="Winter2016">Winter 2016</option>
	      <option value="Fall2016">Fall 2016</option>
	    </select>
	    <div class="text-right">
	   	  <button @click="listCTRs(studentID)">Search</button>
	    </div>
	  </div>
	</div>

	<table class="table table-striped table-hover table-users">
		<tr>
	      <th>Registration ID</th> 
	      <th>Job ID</th>
	      <th>Student ID</th>
	      <th>Term name</th>
	      <th>Status</th>
	      <th>Employer form link</th>
	      <th>Student form link</th>
	  	</tr>
		<tr v-for="ctr in ctrs">
		  <td>{{ ctr.registrationID }}</td>
		  <td>{{ ctr.jobID }}</td>
		  <td>{{ ctr.studentID}}</td>
		  <td>{{ ctr.termName }}</td>
		  <td>{{ ctr.termStatus }}</td>
		  <td>{{ ctr.employerFormLink }}</td>
		  <td>{{ ctr.studentFormLink }}</td>
		  <td><button class="btn btn-secondary btn-danger" @click="adjudicateFailureCTR(ctr.registrationID)">Fail</button></td>
		  <td><button class="btn btn-secondary btn-success" @click="adjudicateSuccessCTR(ctr.registrationID)">Succeed</button></td>
		</tr>
	</table>
  </div>
</template>


<script src="./internshipsjs.js">
</script>


<style>
.filters-entries{
	background-color: #d0d0d0;
	padding: 0px 25px;
	padding-top: 5px;
}

.filters-entries input{
	width: 30%;
	padding-left: 5px;
	border: 1px solid;
	border-radius: 4px;
	float: left;
}

.filters-entries select{
	width: 20%;
	height: 100%;
	padding: 3.5px 5px;
	border: 1px solid;
	border-radius: 4px;
	margin-left: 15px;
	float: left;
}
</style>

.filters-entries button{
	background-color: #dc3545;
	border: 1px solid #dc3545;
	color: white;
	margin: 0px 0px 3px 0px;
	padding: 3px 15px;
	border-radius: 4px;
}

.filters-entries button:hover{
	background-color: #c82333;
	border-color: #c82333;
}

</style>