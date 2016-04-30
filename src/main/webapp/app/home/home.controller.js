(function () {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'LunchEvent'];

    function HomeController($scope, Principal, LoginService, $state, LunchEvent) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        $scope.$on('authenticationSuccess', function () {
            getAccount();
            getLunchEvents();
        });

        getAccount();
        getLunchEvents();

        function getLunchEvents() {
            vm.lunchEvents = [];

            LunchEvent.query(function (result) {
                vm.lunchEvents = result;
            });
        }

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register() {
            $state.go('register');
        }
    }
})();
