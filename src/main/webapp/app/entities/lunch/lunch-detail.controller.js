(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchDetailController', LunchDetailController);

    LunchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Lunch', 'Person'];

    function LunchDetailController($scope, $rootScope, $stateParams, entity, Lunch, Person) {
        var vm = this;
        vm.lunch = entity;
        
        var unsubscribe = $rootScope.$on('luuncherApp:lunchUpdate', function(event, result) {
            vm.lunch = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
