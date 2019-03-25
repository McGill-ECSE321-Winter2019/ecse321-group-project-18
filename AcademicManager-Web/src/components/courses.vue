<template>
  <div class="page-content">
	  <h2 class="pagetitle">Courses</h2>

    <div class="filters-section">
      <div class="search-title">
      	<h4>Search By CourseID and Term</h4>
        <p style="">Both fields must be filled</p>
      </div>
      <div class="filters-entries" style="margin-top:-30px;">
        <input type="text" v-model="courseID" placeholder="Course ID" />
        <input type="text" v-model="term" placeholder="Term">
        <button @click="filterCourseById(courseID, term)"> Search </button>
        <br>
        <span v-if="error" style="color:red">{{error}}</span>
      </div>
      <h4 class="search-title">Search</h4>
	  <div class ="filters-entries">
	    <select v-model="sort_order">
	      <option value="descending">Sort descending</option>
	      <option value="ascending">Sort ascending</option>
	    </select>
        <input type="text" v-model="quantity" placeholder="Quantity" />
	    <div class="text-right">
	      <button @click="filterByQuantity(quantity, sort_order)">Search</button>	      	
	    </div>
	  </div>
    </div>
    <b-table striped hover :items="courses" />
  </div>
</template>


<script src="./course.js">
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
