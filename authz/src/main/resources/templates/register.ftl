<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Angular Material style sheet -->
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.css">
<link rel="stylesheet" href="css/app.css">
</head>
<body ng-app="app" ng-cloak>
<div ng-controller="UserController as ctrl">
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
				<form name="userForm" ng-submit="userForm.$valid && ctrl.createUser()"  method="post" role="form">
								
					<md-input-container class="md-block" flex="100">
						<label>Email Id</label> 
						<input ng-model="ctrl.user.email" type="email" name="email" value="" required> 
						<div ng-messages="userForm.email.$error">
							<div ng-message="required">You must enter a email id.</div>
              				<div ng-message="email">Not valid email.</div>
						</div>
					</md-input-container>

					<md-input-container class="md-block"> 
						<label>Password</label>
						<input name="password" type="password" ng-model="ctrl.user.password" ng-minlength="5" ng-maxlength="50" required> 
						<div ng-messages="userForm.password.$error">
							<div ng-message="required">You must enter a password.</div>
							<div ng-message="minlength">Your field is too short.</div>
              				<div ng-message="maxlength">Your field is too long.</div>
						</div>
					</md-input-container>
					
					<md-input-container class="md-block"> 
					
						<md-checkbox name="tou" ng-model="ctrl.user.isTOSAccepted" required>
						  I accept the Terms of Use. 
						</md-checkbox> 
					
					</md-input-container>
					
						<div ng-messages="userForm.tou.$error" ng-if="userForm.tou.$touched">
							<div ng-message="required">Please Agree to Terms of Use</div>
						</div>
					
					<md-button type="submit" ng-disabled="userForm.$invalid" class="md-raised md-primary wdth100Per">Sign Up</md-button>

				</form>
			</div>
			</md-content> </md-card>
		</div>
	</section>


	<!-- Angular Material requires Angular.js Libraries -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js"></script>

		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-route.min.js"></script>

	<!-- Angular Material Library -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.js"></script>

	<!-- Your application bootstrap  -->
	<script type="text/javascript">
		/**
		 * You must include the dependency on 'ngMaterial' 
		 */
		/**angular.module('app', [ 'ngMaterial' ]); **/
	</script>
		<script type="text/javascript" src="js/app.js"> </script>
		<script type="text/javascript" src="js/userController.js"> </script>
		<script type="text/javascript" src="js/userService.js"> </script>		
</div>
</body>
</html>