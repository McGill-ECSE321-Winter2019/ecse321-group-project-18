<template>
  <div class="page-content">
	  <h2 class="pagetitle">Students</h2>

	  <div class="filters-section">
	    <h4 class="search-title">Search</h4>
	    <div class ="filters-entries">
	      <input type="text" name="name" placeholder="Student ID">
	      <input type="text" name="lastname" placeholder="Student name">
	      <select id="filterBy">
	        <option value="all">All Students</option>
	        <option value="problematic">Problematic</option>
	      </select>
   	      <div class="text-right">
	      	<button @click="listStudent()">Search</button>
	      </div>
	    </div>
	  </div>
	<table>
	  <tr v-for="student in students" >
	      <td>{{ student.studentID }}</td>
	  </tr>
	</table>
  </div>
</template>


<script src="./student.js">
</script>


<style>
.filters-entries input{
	width: 30%;
	margin-rigth: 0px;
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
