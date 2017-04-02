<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Angular Material style sheet -->
<link rel="stylesheet"
	href="http://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.css">
<link rel="stylesheet" href="css/app.css">
</head>
<body ng-app="app" ng-cloak>
	<header>
		<md-toolbar class="md-hue-2 toolbarOuter" md-theme="toolbar">
		<div class="md-toolbar-tools toolbarInner" layout="row"
			layout-align="center center">
			<div layout="row" layout-align="center center" flex-xs="90"
				flex-sm="70" flex-gt-sm="55">
				<span class="alignLeft" flex="33"><p
						class="redFont fnt16 fntWgt600">Student +</p></span> <span
					class="alignCenter" flex="33"><p class="fnt16">Log In</p></span> 
					<!-- <span
					class="alignRight" flex="33"><a class="btn" 
						class="redFont fnt16 fntWgt600 alignRight">Sign Up</a></span> -->
						<span
					class="alignRight" flex="33"><a href="register">
						<md-button class="redFont signUpBtn">Sign Up</md-button></a>
						</span>
			</div>

		</div>
		</md-toolbar>
	</header>
	<section>
		<div class="" layout="row" layout-align="center center">
			<md-card flex-xs="90" flex-sm="70" flex-gt-sm="55"> <md-content class="loginWrapper"
				layout-padding layout="row" layout-align="center">
			<div flex="80">
				<form name="userForm" action="login" method="post">
				<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<md-input-container class="md-block" flex="100">
					<label>User Id</label> <input name=username ng-model="user.email"> </md-input-container>

					<md-input-container class="md-block"> <label>Password</label>
					<input type="password" name="password" ng-model="user.password"> </md-input-container>
					<md-button type="submit" class="md-raised md-primary wdth100Per">Log
					in</md-button>
					<a class="redFont frgtPwd pull-right" href="/login/recover">Forgot
						password?</a>
					
				</form>

				<div class="sociallogin">
					<h2 class="strikeThrough">
						<span>Login with</span>
					</h2>
					<div><a href="/uaa/auth/facebook">
						<span layout="row" layout-align="center center" flex="100">
							<img flex-gt-sm="45" flex-xs="80" flex-sm="80"
							src="images/icon/facebookLogin.png"
							alt="enter image description here">
						</span>
						</a>
						<!-- <span layout="row" layout-align="center center" flex="100">
						<img flex="50" src="images/icon/googleLogin.png"
							alt="enter image description here">
							</span> -->
					</div>


				</div>
			</div>
			</md-content>  </md-card>
		</div>
	</section>

<footer>
</footer>


	<!-- Angular Material requires Angular.js Libraries -->
	<script
		src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
	<script
		src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js"></script>
	<script
		src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js"></script>
	<script
		src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js"></script>

	<!-- Angular Material Library -->
	<script
		src="http://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.js"></script>

	<!-- Your application bootstrap  -->
	<script type="text/javascript">
		/**
		 * You must include the dependency on 'ngMaterial' 
		 */
		/**angular.module('app', [ 'ngMaterial' ]); **/
	</script>
	<script src="js/app.js">
		
	</script>

</body>
</html>

<!--
Copyright 2016 Google Inc. All Rights Reserved. 
Use of this source code is governed by an MIT-style license that can be in foundin the LICENSE file at http://material.angularjs.org/license.
-->