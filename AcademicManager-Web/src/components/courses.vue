<template>
  <div class="page-content">
	  <h2 class="pagetitle">Courses</h2>

    <div class="filters-section">
      <h4 class="search-title">Search specific</h4>
      <div class="filters-entries">
        <input type="text" v-model="courseID" placeholder="Course ID" />
        <input type="text" v-model="term" placeholder="Term">
        <button @click="filterCourseById(courseID, term)"> Search </button>
        <p>
          <span v-if="error" style="color:red">{{error}}</span>
        </p>
      </div>
    </div>
	  <div class="filters-section">
	    <h4 class="search-title">Search</h4>
	    <div class ="filters-entries">
	      <select v-model="sort_order">
	        <option value="descending">Sort descending</option>
	        <option value="ascending">Sort ascending</option>
	      </select>

	      <input type="text" v-model="quantity" placeholder="Quantity" />
	      <button @click="filterByQuantity(quantity, sort_order)">Search</button>
	    </div>
	  </div>
    <div class="filters-section">
      <h4 class="search-title">Query results</h4>
      <b-table striped hover :items="courses" />
    </div>

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
