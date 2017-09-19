
(function () {

	angular.module('app').config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    /* @ngInject */
    function config($stateProvider, $urlRouterProvider) {
            /*var header = {
                templateUrl: '/view/headers.html',
            };*/
    	 	$urlRouterProvider.when('/login', '/loginPage');
            $urlRouterProvider.when('/updatePassword', '/updatePassword');
            $urlRouterProvider.when('/passwordResetSuccessPage', '/passwordResetSuccessPage'); 
            $urlRouterProvider.otherwise('/login');

        }
}) ();