<?php
  include "navbar.php";
?>
	<!DOCTYPE html>
	<html lang="en">
	<head>
		<title>About Page</title>
		<link href="css/login_signup.css" rel="stylesheet">
	 <style> html, body {
     width: 100%;
}
 #home_container {
     width: 50%;
     margin-top: 100px;
     margin-left: auto;
     margin-right: auto;
     text-align: center;
}
 @media only screen and (max-width: 600px) {
     #home_container {
         width: 80%;
    }
}
 #myBtn3 {
     display: block;
     margin: auto;
     float: right;
     background-color: #4CAF50;
}
 #home_container>p {
     font-size:32px;
}
 </style>
	</head>

	<body>
		<div id="home_container">
			<h1>ABOUT OUR COMPANY</h1>
			<p>Our mission is to give customers an easy online rental site that will make them excited and thrilled. It was founded in 2020 by three students who decided to build this site for their school project.</p>
			<p>Location: Saint Paul, MN <br> Address: 700 E 7th St. St Paul, MN 55106 <br> Phone: (651)793-1300</p>
		</div>
      
<?php include "login_signup.php"; ?>
<script>
    // GET MODEL #1 - Sign in
    var modal = document.getElementById("myModal1");
    var btn = document.getElementById("myBtn1");
    var span = document.getElementsByClassName("close")[0];
    btn.onclick = function() {
      modal.style.display = "block";
    }
    span.onclick = function() {
      modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
    // GET MODEL #2 - Signout
    var modal2 = document.getElementById("myModal2");
    var btn2 = document.getElementById("myBtn");
    var span2 = document.getElementsByClassName("close2")[0];
    btn2.onclick = function() {
      modal2.style.display = "block";
    }
    span2.onclick = function() {
      modal2.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal2) {
            modal2.style.display = "none";
        }
    }
    function resetFunction() {
      document.getElementById("logOUT").reset();
    }
</script>       
<?php
  include "footer.php";
?>
</body>

</html>