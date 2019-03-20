<template>
  <div class="page-content">
	  <h2 class="pagetitle">Students</h2>

	  <div class="filters-section">
	    <h4 class="search-title">Search</h4>
	    <div class ="filters-entries">
	      <input type="text" name="name" placeholder="Student ID">
	      <input type="text" name="lastname" placeholder="Student name">
	      <select>
	        <option value="all">All Students</option>
	        <option value="problematic">Problematic</option>
	      </select>
	      <button>Search</button>
	    </div>
	  </div>
  </div>
</template>


<script>
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