<!DOCTYPE >
<template >
  <html lang="en" >

  <head style="background-color:powderblue;">
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  </head>

  <body style="background-color:white;">


    <div class="container-fluid" id="top-container">
      <div class="container text-center">
      <div class="container text-center">
          <img src="@/assets/mcgill2.png"/>
      </div>
      </div>
    </div>

    <div class="container-fluid text-center" id="background">
      <div class="col-sm-2 sidenav"></div>
      <div class="col-sm-8">
        <div class="row">
          <div class="col-sm-2"></div>
          <div class="col-sm-8">
            <br>
            <div class="container-fluid">
              <div class="container text-left" id="welcome">
                <h2>
                  <font>Welcome!</font>
                </h2>
              </div>
              <hr>
            </div>

            <div class="panel panel-default text-center">
              <div class="panel-body">
                <br><br><br><br>
                <div class="form-group">
                  <label for="usr">
                    <font size="4">Please Enter your ID</font>
                  </label>
                  <input type="text" class="form-control form-control-lg" id="usr">
                  <p id="demo"></p>
                </div>
                <button @click="goToDashboard" type="button" class="btn btn-primary btn-block" id="login">
                  <font size="4" face="Times"><b>Login</b></font>
                </button>
                <br>
                <a href="#"><i><b>Need help?</b></i></a>
              </div>
            </div>
          </div>
          <div class="col-sm-2"> </div>
        </div>
      </div>
      <div class="col-sm-2 sidenav"></div>
    </div>
  </body>

  </html>
</template>

<style>
  .panel {
    min-height: 80%;
    max-width: 100%;
  }




  #welcome {
    max-width: 100%;
    margin-top: 0px;
  }

  #welcome h2 {
    text-align: center;
    margin-top: 15px;
    margin-bottom: 10px;
    font-size: 37px
  }


</style>

<script>
  import axios from 'axios'
  var config = require('../../config')

  /* AXIOS object setup */
if (process.env.NODE_ENV != 'production') {
  	var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
	var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
} 
else
{
	var frontendUrl = 'https://' + config.build.host + ':' + config.build.port
	var backendUrl = 'https://' + config.build.backendHost + ':' + config.build.backendPort
}


  var AXIOS = axios.create({
    baseURL: backendUrl
  })

  var checkInput = async (input) => {
    if (input.length !== 9) {
      console.log("length is not 9 😠 ");
      return false;
    } else {
      try {
        let response = await AXIOS.get(/students/ + input);
        if (response.data !== {}) {
          console.log("successful request! 🙂 ");
          return true;
        } else {
          console.log("successful request but data is empty 😞");
          return false;
        }
      } catch (e) {
        console.log("unsuccessful request 😞");
        return false;
      }

    }
    console.log("none of the if/else statements got entered");
    return false;
  }

  export default {
    name: 'login',
    data() {
      return {
        student: null
      }
    },
    methods: {
      printOut: async function() {
        var result = await created();
        console.log(result);
      },

      goToDashboard: async function() {
        var input = document.getElementById("usr").value;
        var result = await checkInput(input);
        if (result) {
          this.$router.push({
            name: 'Dashboard',
            params: {
              id: input
            }
          })
        } else {
          document.getElementById("usr").className = 'form-control form-control-lg is-invalid'
          console.log("b");
          document.getElementById("demo").innerHTML = "Please Enter Correct CooperatorID ";
          document.getElementById("demo").style.color = 'red';
          this.$router.push("/#/app");
        };
        //var result = checkInput(input).then(ret);
        console.log("hey")

      },
    }
  }
</script>