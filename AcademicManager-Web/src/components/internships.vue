<template>
  <div class="page-content">
	<h2>Internships</h2>

	<div class="filters-section">
	  <h4 class="search-title">Search</h4>
	  <div class ="filters-entries">
	    <input name ="input-student-id" type="text" v-model="studentID" placeholder="Internship ID">
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
	   	  <button name="search-for-internships" @click="listCTRs(studentID)">Search</button>
	    </div>
	  </div>
	</div>

	<div style="overflow-x:auto;">
		<table class="table table-striped table-hover table-users">
			<tr>
		      <th>Registration ID</th> 
		      <th>Job ID</th>
		      <th>Student ID</th>
		      <th>Term name</th>
		      <th>Status</th>
		      <th>Employer form link</th>
		      <th>Student form link</th>
		      <th></th>
		      <th></th>
		      <th>Grade</th>
		  	</tr>
			<tr v-for="ctr in ctrs">
			  <td>{{ ctr.registrationID }}</td>
			  <td>{{ ctr.jobID }}</td>
			  <td>{{ ctr.studentID}}</td>
			  <td>{{ ctr.termName }}</td>
			  <td>{{ ctr.termStatus }}</td>
			  <td>{{ ctr.employerFormLink }}</td>
			  <td>{{ ctr.studentFormLink }}</td>
			  <td>
			  	<a href="#popupFail">
			  		<button class="btn btn-secondary btn-danger" @click="adjudicateFailureCTR(ctr.registrationID)">Fail</button>
			  	</a>
			  </td>
			  <td>
			  	<a href="#popupPass">
			  		<button class="btn btn-secondary btn-Success" @click="adjudicateSuccessCTR(ctr.registrationID)">Succeed</button>
			  	</a>
			  </td>
			  <td>{{ ctr.grade }}</td>
			</tr>
		</table>
	</div>
	
	<div id="popupFail" class="overlay">
		<div class="popup">
			<h4>Are you sure you want to Fail this student?</h4>
			<a class="close" href="#">&times;</a>
			<div class="content text-right">
			  <a href="#">
			  	<button class="btn btn-secondary">Yes</button>
			  </a>
			  <a href="#">
			  	<button class="btn btn-secondary">No</button>
			  </a>
			</div>
		</div>
	</div>


	<div id="popupPass" class="overlay">
		<div class="popup">
			<h4>Are you sure you want to Pass this student?</h4>
			<a class="close" href="#">&times;</a>
			<div class="content text-right">
			  <a href="#">
			  	<button class="btn btn-secondary" @click="adjudicateSuccessCTR(ctr.registrationID)">Yes</button>
			  </a>
			  <a href="#">
			  	<button class="btn btn-secondary">No</button>
			  </a>
			</div>
		</div>
	</div>


  </div>
</template>


<script src="./internshipsjs.js">
</script>


<style>
.box {
  width: 40%;
  margin: 0 auto;
  background: rgba(255,255,255,0.2);
  padding: 35px;
  border: 2px solid #fff;
  border-radius: 20px/50px;
  background-clip: padding-box;
  text-align: center;
}
.button {
  font-size: 1em;
  padding: 10px;
  color: #fff;
  border: 2px solid #06D85F;
  border-radius: 20px/50px;
  text-decoration: none;
  cursor: pointer;
  transition: all 0.3s ease-out;
}
.button:hover {
  background: #06D85F;
}
.overlay {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.7);
  transition: opacity 500ms;
  visibility: hidden;
  opacity: 0;
}
.overlay:target {
  visibility: visible;
  opacity: 1;
}

.popup {
  margin: 70px auto;
  padding: 20px;
  background: #fff;
  border-radius: 5px;
  width: 30%;
  position: relative;
  transition: all 5s ease-in-out;
}

.popup h2 {
  text-align: center;
  color: #333;
  font-family: Tahoma, Arial, sans-serif;
}
.popup .close {
  position: absolute;
  top: 20px;
  right: 30px;
  font-size: 30px;
  font-weight: bold;
  text-decoration: none;
  color: #333;
}
.popup .close:hover {
  color: #06D85F;
}
.popup .content {
  max-height: 30%;
  overflow: auto;
}

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
