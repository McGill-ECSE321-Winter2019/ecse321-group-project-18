<template>
  <div class="page-content">
	  <h2 class="pagetitle">Courses</h2>

	  <div class="filters-section">
	    <h4 class="search-title">Search</h4>
	    <div class ="filters-entries">
	      <input type="text" name="name" placeholder="Course ID">
	      <select>
	        <option value="mostToLeastUseful">Most To Least Useful</option>
	        <option value="problematic">Least To Most Useful</option>
	      </select>

	      <select>
	        <option value="first5">5</option>
	        <option value="first10">10</option>
	        <option value="first20">20</option>
	        <option value="all">All</option>
	      </select>
	      <div class="text-right">	      
	      	<button>Search</button>
	      </div>
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