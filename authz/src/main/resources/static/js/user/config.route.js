(function() {
    angular.module('app.user').config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    /* @ngInject */
    function config($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('loginPage', {

                url: '/loginPage',
                
                /*resolve : { @ngInject 
                	responseData : [ '$stateParams',function($stateParams) {
        				console.info($stateParams.success);
        				
        			} ]
                },*/
                
                views: {
                        header: {
					        templateUrl : 'view/headers.html',	
                            //controller: "UserController as vm"				
                	    },
                
                        container: {
                            templateUrl: 'view/login.html',
                            params: {'success': null},
                            controller: "UserController as vm"
                           
                    }

                }
            })

            .state('registration', {

                url: '/registration',
                
                views: {
                        header: {
					        templateUrl : 'view/regHeaders.html',	
                            controller: "UserController as vm"				
                	    },
                
                        container: {
                            templateUrl: 'view/register.html',
                            controller: "UserController as vm"
                    }

                }
            })
            
    }
})();