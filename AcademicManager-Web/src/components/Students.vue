<template>
  <div id="Students">
    <table>
      <thead>
        <tr>
          <td>Student ID</td>
          <td>First Name</td>
          <td>Last Name</td>
          <td>Problematic Status</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="student in students" >
          <td>{{ student.studentID }}</td>
          <td>{{ student.firstName }}</td>
          <td>{{ student.lastName }}</td>
          <td>{{ student.isProblematic }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script src="./students.js">

</script>

<style scoped>

</style>
