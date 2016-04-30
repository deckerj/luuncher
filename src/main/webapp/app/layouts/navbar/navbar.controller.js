(function() {
	'use strict';

	angular.module('luuncherApp').controller('NavbarController',
			NavbarController);

	NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ENV', 'LoginService' ];

	function NavbarController($state, Auth, Principal, ENV, LoginService) {
		var vm = this;

		vm.isNavbarCollapsed = true;
		vm.isAuthenticated = Principal.isAuthenticated;
		vm.inProduction = ENV === 'prod';
		vm.login = login;
		vm.logout = logout;
		vm.toggleNavbar = toggleNavbar;
		vm.collapseNavbar = collapseNavbar;
		vm.$state = $state;
		
		getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

		function login() {
			collapseNavbar();
			LoginService.open();
			getAccount();
		}

		function logout() {
			collapseNavbar();
			Auth.logout();
			$state.go('home');
		}

		function toggleNavbar() {
			vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
		}

		function collapseNavbar() {
			vm.isNavbarCollapsed = true;
		}
	}
})();
