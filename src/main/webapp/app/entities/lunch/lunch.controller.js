(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchController', LunchController);

    LunchController.$inject = ['$scope', '$state', 'Lunch'];

    function LunchController ($scope, $state, Lunch) {
        var vm = this;
        vm.lunches = [];
        vm.loadAll = function() {
            Lunch.query(function(result) {
                vm.lunches = result;
            });
        };

        vm.loadAll();
        
    }
})();
