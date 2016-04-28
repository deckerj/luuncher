(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Person', 'LunchGroup', 'User'];

    function PersonDetailController($scope, $rootScope, $stateParams, entity, Person, LunchGroup, User) {
        var vm = this;
        vm.person = entity;
        
        var unsubscribe = $rootScope.$on('luuncherApp:personUpdate', function(event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
