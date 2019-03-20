<template>
  <div class="page-content">
	  <h2>Internships</h2>

	  <div class="filters-section">
	    <h4 class="search-title">Search</h4>
	    <div class ="filters-entries">
	      <input type="text" name="name" placeholder="Internship ID">
	      <select>
	        <option value="Winter19">Winter 2019</option>
	        <option value="Fall19">Fall 2019</option>
	        <option value="Winter18">Winter 2018</option>
	        <option value="Fall18">Fall 2018</option>
	        <option value="Winter17">Winter 2017</option>
	        <option value="Fall17">Fall 2017</option>
	        <option value="Winter16">Winter 2016</option>
	        <option value="Fall16">Fall 2016</option>
	      </select>
	      <button>Search</button>
	    </div>
	  </div>
  </div>
</template>


<script>
</script>


<style>
.filters-entries{
	background-color: #d0d0d0;
	padding: 0px 25px;
}

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