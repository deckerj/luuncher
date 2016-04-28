(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchGroupController', LunchGroupController);

    LunchGroupController.$inject = ['$scope', '$state', 'LunchGroup'];

    function LunchGroupController ($scope, $state, LunchGroup) {
        var vm = this;
        vm.lunchGroups = [];
        vm.loadAll = function() {
            LunchGroup.query(function(result) {
                vm.lunchGroups = result;
            });
        };

        vm.loadAll();
        
    }
})();
