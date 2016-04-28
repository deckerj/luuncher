(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchGroupDetailController', LunchGroupDetailController);

    LunchGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'LunchGroup', 'LunchEvent', 'Person'];

    function LunchGroupDetailController($scope, $rootScope, $stateParams, entity, LunchGroup, LunchEvent, Person) {
        var vm = this;
        vm.lunchGroup = entity;
        
        var unsubscribe = $rootScope.$on('luuncherApp:lunchGroupUpdate', function(event, result) {
            vm.lunchGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
