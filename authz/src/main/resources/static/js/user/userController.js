(function () {
    'use strict';

angular.module('app.user').controller('UserController', ['$scope', '$state', 'UserService', '$stateParams', 
	function($scope, $state, UserService, $stateParams) {
    var vm = this;
    vm.user={email:'',password:''};
    vm.PasswordDto={newPassword:''};
    vm.users=[];
    vm.successMsg= $stateParams.success;
    console.info($stateParams.success);

    vm.submit = submit;
    vm.pwReset = pwReset;
    vm.edit = edit;
    vm.remove = remove;
    vm.reset = reset;
    vm.createUser = createUser;
    vm.changePassword = changePassword;
    var registration = registration;
  
    //fetchAllUsers();

   
     function changePassword(){
        UserService.changePassword(vm.PasswordDto)
            .then(
            function(successResponse){
                console.info('Password has been  changed');
               // $state.go("passwordResetSuccessPage");
               
                window.location.href = successResponse.payload;
            },
            function(errResponse){
                console.error('Error while password chnange');
            }
        );
    }

     function pwReset(){
        UserService.pwReset(vm.user)
            .then(
            function(successResponse){
                console.info('Password eMail has been sent');
                $state.go("forgotPWSuccessPage");
            },
            function(errResponse){
                console.error('Error while password reset');
            }
        );
    }

    function fetchAllUsers(){
        UserService.fetchAllUsers()
            .then(
            function(d) {
                vm.users = d;
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
        );
    }

    function createUser(){
        UserService.createUser(vm.user)
            .then(
                userSuccess,
                userFail
            );
       // $state.go("loginPage");
    }

    function updateUser(user, id){
        UserService.updateUser(user, id)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while updating User');
            }
        );
    }

    function deleteUser(id){
        UserService.deleteUser(id)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while deleting User');
            }
        );
    }

    function submit() {
        if(vm.user.id===null){
            console.log('Saving New User', vm.user);
            createUser(vm.user);
        }else{
            updateUser(vm.user, vm.user.id);
            console.log('User updated with id ', vm.user.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < vm.users.length; i++){
            if(vm.users[i].id === id) {
                vm.user = angular.copy(vm.users[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(vm.user.id === id) {//clean form if the user to be deleted is shown there.
            reset();
        }
        deleteUser(id);
    }


    function reset(){
        vm.user={id:null,username:'',address:'',email:''};
        $scope.userForm.$setPristine(); //reset Form
    }

var userSuccess = function(data) {
     //$scope.user = data;
     
     if(!data.hasError){
         console.info('User created successfully-'+ data);
         vm.successMsg = data.payload;
         $state.go("loginPage", {success:data.payload}, { reload: true });
     }else if(data.hasError){

         console.info('Error Occured-'+ data);
         vm.errorMsg = data.error.fieldErrors[0].message;
         vm.user.email='';
         vm.user.password='';
         vm.user.isTOSAccepted='false';
     }else{
         console.info('Validation error'+ data);
         vm.errorMsg = data.fieldErrors[0].message;
     }
     
     //$location.path('/login');
    // $state.go("loginPage");
    };

var userFail = function(reason) {
      //$scope.error = "Some error occured";
      vm.errorMsg = reason.message;
      console.error('Error while fetching Users-'+reason);
     // $location.path('/register');
    };
    

}])

})();