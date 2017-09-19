(function () {
    'use strict';
    
angular.module('app.user').factory('UserService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = '/uaa/v1/user';

    var factory = {
        fetchAllUsers: fetchAllUsers,
        createUser: createUser,
        changePassword: changePassword,
        pwReset: pwReset,
        updateUser:updateUser,
        deleteUser:deleteUser
    };

    return factory;

     function changePassword(passwordDto) {
        var deferred = $q.defer();
        $http.post('/uaa/v1/savePassword', passwordDto)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while password change'+ errResponse);
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function pwReset(user) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI+'/resetPassword', user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while password reset'+ errResponse);
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function fetchAllUsers() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function createUser(user) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating User in Service'+ errResponse);
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function updateUser(user, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deleteUser(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}])

})();
