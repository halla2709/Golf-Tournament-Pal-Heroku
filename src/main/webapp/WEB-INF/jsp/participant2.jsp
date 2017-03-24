<!-- Configurations -->
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<link rel="shortcut icon" href="../images/images.jpg" />
<link rel="stylesheet" href="../extras/style.css">
<script src="../extras/functions.js" language="Javascript"
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
		<h1>Create Tournament</h1>
		<br>
		<h3>2. Fill out information about participants:</h3>
		<br>
		<div>
			<div class="w3-card-8 w3-col l4 s6"
				style="padding: 2em; margin-right: 2em;">
				<sf:form method="POST" commandName="golfer" action="/addplayers2">
					<table>
						<tr>
							<td>Name:</td>
							<td><sf:input required="required" path="name" type="text"
									placeholder="Enter Name" class="w3-input" autofocus="autofocus" /></td>
						</tr>
						<tr>
							<td>Handicap:</td>
							<td><sf:input required="required" path="handicap"
									type="number" max="54" step="0.1" class="w3-input "
									style="width:30%" /></td>
						</tr>
						<tr>
							<td>Social:</td>
							<td><sf:input id="ssn" required="required" path="social"
									type="text" name="ssn" class="w3-input" style="width:60%"
									min="0" /></td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><sf:input required="required" path="email" type="email"
									class="w3-input" placeholder="Enter Email" /></td>
						</tr>
					</table>
					<br>
					<input id="normalbutton" class="w3-theme w3-btn" type="submit"
						VALUE="Add Player" />
				</sf:form>
			</div>
			<div class="w3-card-8 w3-col 18 s6 w3-center">
				<c:choose>
					<c:when test="${not empty golfers}">
						<table class="w3-table-all w3-hoverable">
							<thead>
								<tr id="table">
									<td>First Name</td>
									<td>Social Security Number</td>
									<td>Email</td>
									<td>Handicap</td>
								</tr>
							</thead>
							<c:forEach var="golfer" items="${golfers}">
								<tr id="dismiss">
									<td>${golfer.name}</td>
									<td>${golfer.social}</td>
									<td>${golfer.email}</td>
									<td>${golfer.handicap}<span onclick="dismiss()"
										class="w3-closebtn w3-margin-right w3-medium">&times;</span>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<table class="w3-table-all w3-hoverable">
							<thead>
								<tr id="table">
									<td>First Name</td>
									<td>Social Security Number</td>
									<td>Email</td>
									<td>Handicap</td>
								</tr>
							</thead>
						</table>
						<p>You have not entered any participants yet!</p>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="w3-col s12">
				<br>
				<p>After selecting participants in the tournament, create the
					tournament!</p>
				<br>
				<form method="POST" action="/scoreboard2">
					<input id="normalbutton"
						class="w3-theme w3-center w3-btn w3-col s4 w3-medium"
						type="submit" value="Create Tournament" /><br>
				</form>
			</div>
		</div>
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

