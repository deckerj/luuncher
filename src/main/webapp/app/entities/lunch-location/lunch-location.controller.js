(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchLocationController', LunchLocationController);

    LunchLocationController.$inject = ['$scope', '$state', 'LunchLocation'];

    function LunchLocationController ($scope, $state, LunchLocation) {
        var vm = this;
        vm.lunchLocations = [];
        vm.loadAll = function() {
            LunchLocation.query(function(result) {
                vm.lunchLocations = result;
            });
        };

        vm.loadAll();
        
    }
})();
