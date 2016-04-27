(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchLocationDetailController', LunchLocationDetailController);

    LunchLocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'LunchLocation'];

    function LunchLocationDetailController($scope, $rootScope, $stateParams, entity, LunchLocation) {
        var vm = this;
        vm.lunchLocation = entity;
        
        var unsubscribe = $rootScope.$on('luuncherApp:lunchLocationUpdate', function(event, result) {
            vm.lunchLocation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
