(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchEventController', LunchEventController);

    LunchEventController.$inject = ['$scope', '$state', 'LunchEvent'];

    function LunchEventController ($scope, $state, LunchEvent) {
        var vm = this;
        vm.lunchEvents = [];
        vm.loadAll = function() {
            LunchEvent.query(function(result) {
                vm.lunchEvents = result;
            });
        };

        vm.loadAll();
        
    }
})();
