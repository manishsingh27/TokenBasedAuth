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
					class="alignCenter" flex="33"><p class="fnt16">Sign Up</p></span>
				<!-- <span
					class="alignRight" flex="33"><a class="btn" 
						class="redFont fnt16 fntWgt600 alignRight">Sign Up</a></span> -->
				<span class="alignRight" flex="33"><a href="login"> <md-button
						class="redFont signUpBtn">Log In</md-button></a>
				</span>
			</div>

		</div>
		</md-toolbar>
	</header>
	<section>
		<div layout="row" layout-align="center center">
			<md-card flex-xs="90" flex-sm="70" flex-gt-sm="55"> <md-content
				class="loginWrapper" layout-padding layout="row"
				layout-align="center">
			<div flex="80" >
				<form name="userForm" action="register" method="post">
				<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<md-input-container class="md-block" flex="100">
					<label>Email Id</label> <input ng-model="user.email"> </md-input-container>

					<md-input-container class="md-block"> <label>Password</label>
					<input type="password" ng-model="user.password"> </md-input-container>

					<md-input-container class="md-block"> <label>Full
						Name</label> <input type="text" ng-model="user.passwordConfirm"> </md-input-container>
					<div layout-gt-xs="row">



						<md-input-container class="md-block" flex-gt-sm>
						<label>Gender</label> <md-select ng-model="user.state">
						<md-option value="male"> Male </md-option> <md-option
							value="female"> Female </md-option> </md-select> </md-input-container>

						<md-input-container> <label>DOB</label> <md-datepicker
							ng-model="user.submissionDate"></md-datepicker> </md-input-container>
					</div>
					<md-input-container class="md-block"> <md-checkbox
						name="tos" ng-model="project.tos" required> I accept
					the terms of service. </md-checkbox> <md-button type="submit"
						class="md-raised md-primary wdth100Per">Sign Up</md-button> <!-- <md-input-container class="md-block"> <md-checkbox
						name="rememberme" ng-model="rememberme" required>
					Remember me </md-checkbox> </md-input-container> -->
				</form>


			</div>
			</md-content> </md-card>
		</div>
	</section>




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