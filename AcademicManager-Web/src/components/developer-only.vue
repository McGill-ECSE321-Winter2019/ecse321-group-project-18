<template>
  <div class="page-content">
    <h2 class="pagetitle">Developer page</h2>
    <p> Warning! This area is reserved for the developers only! </p>
    <div class="filters-section">
      <h4 class="search-title">Generate test instances</h4>
      <div class = filters-entries>
        <button name="generate-test-instances" @click="generateTestInstances()"> Generate test instances </button>
        <button name="wipe-databse" @click="wipeDatabase()"> Wipe database </button>
      </div>
      <span> {{ message }} </span>
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
