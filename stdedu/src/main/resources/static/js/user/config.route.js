(function() {
    angular.module('app.user').config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    /* @ngInject */
    function config($stateProvider, $urlRouterProvider) {
        $stateProvider

            .state('landingPage', {

                url: '/landingPage',
                
                views: {
                        header: {
					        templateUrl : 'landingPage/view/headers.html',	
                            controller: "UserController as vm"				
                	    },
                
                        container: {
                            templateUrl: 'landingPage/view/landingPage.html',
                            controller: "UserController as vm"
                    },

                }
            }) 
            .state('profile', {

                url: '/profile',
                
                views: {
                        header: {
					        templateUrl : 'landingPage/view/profileHeaders.html',	
                            controller: "UserController as vm"				
                	    },
                
                        container: {
                            templateUrl: 'landingPage/view/profileRegister.html',
                            controller: "UserController as vm"
                    },

                }
            })          
            
    }
})();