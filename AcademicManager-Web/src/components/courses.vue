<template>
  <div class="page-content">
    <h2 class="pagetitle">Courses</h2>

    <div class="filters-section">
      <div class="search-title">
      	<h4>Search By CourseID and Term</h4>
        <p style="">Both fields must be filled</p>
      </div>
      <div class="filters-entries" style="margin-top:-30px;">
        <input name="input-course-id" type="text" v-model="courseID" placeholder="Course ID"/>
        <input name="input-term-id" type="text" v-model="term" placeholder="Term">
        <div class="text-right">
          <button name="filter-course-by-id" @click="filterCourseById(courseID, term)">Search</button>
        </div>
      </div>
      <h4 class="search-title">Or Sort by Order Specify Quantity of Course Displayed</h4>
	  <div class ="filters-entries">
	    <select v-model="sort_order" style="margin-left:0px; margin-right:15px;">
	      <option value="descending">Sort descending</option>
	      <option value="ascending">Sort ascending</option>
	    </select>
        <input type="text" v-model="quantity" placeholder="Quantity" />
	    <div class="text-right">
	      <button name="filter-course-by-quantity" @click="filterByQuantity(quantity, sort_order)">Search</button>
	    </div>
	  </div>
    </div>
    
    <table class="table table-striped table-hover table-users">
		<tr>
	      <th>Course ID</th> 
	      <th>Course Name</th>
	      <th>Term ID</th>
	      <th>Course Rank</th>
	  	</tr>
		<tr v-for="course in courses">
		  <td>{{ course.courseID }}</td>
		  <td>{{ course.courseName }}</td>
		  <td>{{ course.term }}</td>
		  <td>{{ course.courseRank }}</td>
		</tr>
	</table>
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
	margin-right: 15px;
}

.filters-entries select{
	width: 30%;
	padding-left: 5px;
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
