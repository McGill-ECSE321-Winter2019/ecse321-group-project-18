<template>
  <div id="app">
    <div id="banner">
      <a href="#/app"><img id="logo" src="./assets/mcgill.png" height="80" width="200"></a>
    </div>
    <div id="sidebar">
      <ul id="sidebar-list">
        <li><a href="#/app" class="sidebar-element">Home</a></li>
        <li><a href="#/students" class="sidebar-element">Students</a></li>
        <li><a href="#/internships" class="sidebar-element">Internships</a></li>
        <li><a href="#/courses" class="sidebar-element">Courses</a></li>
      </ul>
    </div>
    <router-view></router-view>
  </div>

</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
  background-color: #f5f5f5;
  height: 100vh;
  width: 100wh;
}

#logo{
  align: center;
  margin-top: 0px;
}

#banner {
  background-color: #607d8b;
  text-align: center;
  margin: -60px 89.5px 15px 0px;
  width: 100%;
  height: 80px;
}

#sidebar{
  margin-left: 40px;
  padding: 15px;
  float: left;
}

#sidebar-list {
  list-style: none;
}

#sidebar-list li{
  padding: 10px 0px;
}

#sidebar-list li:hover{
  background-color:#607d8b;
  border-radius: 4px;
}

#sidebar-list li a{
  padding: 15px 10px;
  font-size: 22px;
  color: rgba(51, 51, 51, 0.7);
}

#sidebar-list li a:hover{
  text-decoration: none;
}

.page-content{
  margin: 35px 100px 0px 300px;
}

.search-title{
  background-color: #e0e0e0;
  margin: 0px;
  padding: 15px 25px;
}

.filters-section{
  margin: 20px 0px;
  border: 4px solid;
  border-radius: 8px;
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
