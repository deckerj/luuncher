(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchEventDeleteController',LunchEventDeleteController);

    LunchEventDeleteController.$inject = ['$uibModalInstance', 'entity', 'LunchEvent'];

    function LunchEventDeleteController($uibModalInstance, entity, LunchEvent) {
        var vm = this;
        vm.lunchEvent = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            LunchEvent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
