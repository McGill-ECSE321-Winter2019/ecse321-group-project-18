<template>
  <div class="page-content">
    <h2 class="pagetitle">Developer page</h2>
    <p> Warning! This area is reserved for the developers only! </p>
    <div class="filters-section">
      <h4 class="search-title">Generate test instances</h4>
      <div class = filters-entries>
        <button name="generate-test-instances" @click="generateTestInstances()"> Generate test instances </button>
      </div>
      <!--<div class="filters-entries">-->
      <!--<input type="text" name="input-course-id" v-model="courseID" placeholder="Course ID" />-->
      <!--<input type="text" name="input-term-id" v-model="term" placeholder="Term">-->
      <!--<button name="filter-course-by-id" @click="filterCourseById(courseID, term)"> Search </button>-->
      <!--<p>-->
      <!--<span name="filter-course-by-id-error" v-if="error" style="color:red">{{error}}</span>-->
      <!--</p>-->
      <!--</div>-->
    </div>
  </div>

</template>


<script src="./developer.js">
</script>


<style scoped>
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
