(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchGroupDeleteController',LunchGroupDeleteController);

    LunchGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'LunchGroup'];

    function LunchGroupDeleteController($uibModalInstance, entity, LunchGroup) {
        var vm = this;
        vm.lunchGroup = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            LunchGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
