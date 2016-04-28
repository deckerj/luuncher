(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchEventDetailController', LunchEventDetailController);

    LunchEventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'LunchEvent', 'LunchLocation', 'LunchGroup'];

    function LunchEventDetailController($scope, $rootScope, $stateParams, entity, LunchEvent, LunchLocation, LunchGroup) {
        var vm = this;
        vm.lunchEvent = entity;
        
        var unsubscribe = $rootScope.$on('luuncherApp:lunchEventUpdate', function(event, result) {
            vm.lunchEvent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
