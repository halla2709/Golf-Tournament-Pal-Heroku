<!-- Configurations -->
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<link rel="shortcut icon" href="../../images/images.jpg" />
<link rel="stylesheet" href="../../extras/style.css">
<script src="../../extras/functions.js" language="Javascript"
	type="text/javascript"></script>
<html lang="is">
<title>Golf-Tournament Pal</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.theme.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css">

<body>
	<!-- Navigation (&Header)-->
	<div class="w3-top">
		<ul class="w3-navbar w3-container w3-theme">
			<li><a class="w3-padding-16 w3-hover-white" href="/index">Home</a></li>
			<li class="w3-dropdown-hover"><a
				class="w3-padding-16 w3-hover-white" href="javascript:void(0)">Create
					Tournament</a>
				<div class="w3-dropdown-content theme w3-card-4">
					<a class="w3-padding-16 theme w3-hover-white" href="/matchplay">Matchplay
						Tournament</a> <a class="w3-padding-16 theme w3-hover-white"
						href="/scoreboard">Scoreboard Tournament</a>
				</div></li>
			<li><a class="w3-padding-16 w3-hover-white" href="/results">Results</a></li>
			<li><a class="w3-padding-16 w3-hover-white" href="/mypage">My
					Page</a></li>
			<li><a class="w3-padding-16 w3-hover-white" href="/about">About</a></li>
			<!-- Will only be available once login has been created -->
			<!-- <li id="login"><a class="w3-padding-16 w3-hover-white" href="/login">Login</a></li> -->


			<!-- Header(&Navigation) -->
			<br>
			<br>
			<header class="w3-container w3-theme w3-padding" id="myHeader">
				<div class="w3-center">
					<h2>Golf-Tournament Pal!</h2>
					<h5 class="w3-large w3-animate-bottom">Your best Golf friend</h5>
			</header>
		</ul>
	</div>
	<hr>

	<!-- Main Text -->
	<div class="w3-row w3-container" id="main">
		<h1>Tournament Information:</h1>
		<table class="w3-table-all w3-hoverable" style="width: 47%">
			<thead>
				<tr id="table">
					<td>Tournament name:</td>
					<td>Course:</td>
					<td>Start Date:</td>
				</tr>
			</thead>
			<tr>
				<td>${name}</td>
				<td>${course}</td>
				<td>${startdate}</td>
			</tr>
		</table>

		<br>
		<h3>Participants Information:</h3>
		<%--Choose what code to generate based on tests that we implement--%>
		<c:choose>
			<%--If the model has an attribute with the name `postitNotes`--%>
			<c:when test="${not empty golfers}">
				<%--Create a table for the Postit Notes--%>
				<table class="w3-table-all w3-hoverable">
					<thead>
						<tr id="table">
							<td>First Name</td>
							<td>Social Security Number</td>
							<td>Email</td>
							<td>Handicap</td>

						</tr>
					</thead>


					<%--For each postit note, that is in the list that was passed in the model--%>
					<%--generate a row in the table--%>
					<%--Here we set `postit` as a singular item out of the list `postitNotes`--%>
					<c:forEach var="golfer" items="${golfers}">
						<tr>
							<%--We can reference attributes of the Entity by just entering the name we gave--%>
							<%--it in the singular item var, and then just a dot followed by the attribute name--%>

							<%--Create a link based on the name attribute value--%>
							<td>${golfer.name}</td>
							<td>${golfer.social}</td>
							<td>${golfer.email}</td>
							<td>${golfer.handicap}</td>
						</tr>
					</c:forEach>
				</table>
				<br>
			</c:when>

			<%--If all tests are false, then do this--%>
			<c:otherwise>
				<h3>No notes!</h3>
			</c:otherwise>
		</c:choose>


		<c:choose>
			<c:when test="${ not empty scoreboard }">
				<h3>Scoreboard:</h3>
				<h4>Click on rounds to view or insert scores</h4>
				<table class="w3-table-all w3-hoverable">
					<thead>
						<tr id="table">
							<th>Player</th>
							<c:forEach var="num" begin="1" end="${numberOfRounds}">
								<th>Round ${num}</th>
							</c:forEach>
							<th>Total</th>
	
						</tr>
					</thead>

					<c:forEach var="row" begin="0" end="${scorecards.size()-1}">
						<tr>
							<td>${scorecards.get(row).getPlayer().getName()}</td>
							<c:forEach var="column" begin="0" end="${numberOfRounds-1}">
								<td><a href="${scorecards.get(row).getPlayer().getSocial()}/${column+1}">${scoreboard[row][column]}</a></td>
							</c:forEach>
							<td>${scoreboard[row][numberOfRounds]}</td>
						</tr>
					</c:forEach>
				</table>

			</c:when>

		</c:choose>
	</div>
	<hr>


	<!-- Footer -->
	<div id="footer">
		<div id="footer1"
			class="w3-third w3-center w3-container w3-theme w3-medium">
			<h3>Contact Info</h3>
			<p>
				<i class="fa fa-map-marker"></i> Korpúlfsstaðir
			</p>
			<p>
				<i class="fa fa-phone"></i> Phone: +354 5850200
			</p>
			<p>
				<i class="fa fa-envelope"> </i> Email: gr@grgolf.is
			</p>
		</div>
		<div id="footer2"
			class="w3-third w3-center w3-medium w3-theme w3-text-white">
			<br> <br>
			<p>Golfing,</p>
			<p>It's a way of life</p>
		</div>
		<div id="footer3"
			class=" w3-third w3-center w3-medium w3-theme w3-text-white">
			<h3>Like Us!</h3>
			<ul>
				<a href="http://www.facebook.com"><img
					src='http://www.womenactionmedia.org/cms/assets/themes/crate/images/social/facebook.png' /></a>
			</ul>
			<ul>
				<a href="http://www.twitter.com"><img
					src='http://grfx.cstv.com/schools/wis/graphics/icon_twitter24.jpg' /></a>
			</ul>
		</div>
	</div>

</body>
</html>

