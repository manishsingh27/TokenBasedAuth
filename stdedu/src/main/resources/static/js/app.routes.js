
(function () {

	angular.module('app').config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    /* @ngInject */
    function config($stateProvider, $urlRouterProvider) {
            /*var header = {
                templateUrl: '/view/headers.html',
            };*/
    	 	$urlRouterProvider.when('/', '/landingPage');  
            $urlRouterProvider.otherwise('/profile','/profile') 	 	
            $urlRouterProvider.otherwise('/landingPage');

        }
}) ();