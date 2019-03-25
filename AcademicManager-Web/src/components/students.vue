<template>
  <div class="page-content">
    <h2 class="pagetitle">Students</h2>
    <div class="filters-section">
      <h4 class="search-title">Search</h4>
      <div class ="filters-entries">
        <input type="text" v-model="id" placeholder="Student ID">
        <div class="text-left">
          <select id="filterBy">
            <option value="all">All Students</option>
            <option value="problematic">Problematic</option>
          </select>
          <div class="text-right">
          	<button @click="listStudents(id)">Search</button>          
          </div>
        </div>
      </div>
    </div>

    <table class="table table-striped table-hover table-users">
		<tr>
	      <th>StudentID</th> 
	      <th>First Name</th>
	      <th>Last Name</th>
	      <th>Student Problematic Status</th>
	  	</tr>
		<tr v-for="student in students">
		  <td>{{ student.studentID }}</td>
		  <td>{{ student.firstName }}</td>
		  <td>{{ student.lastName }}</td>
		  <td>{{ student.studentProblematicStatus }}</td>
		</tr>
	</table>
  </div>
</template>


<script src="./student.js">
</script>


<style>
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
