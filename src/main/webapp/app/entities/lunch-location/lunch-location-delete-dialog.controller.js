(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchLocationDeleteController',LunchLocationDeleteController);

    LunchLocationDeleteController.$inject = ['$uibModalInstance', 'entity', 'LunchLocation'];

    function LunchLocationDeleteController($uibModalInstance, entity, LunchLocation) {
        var vm = this;
        vm.lunchLocation = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            LunchLocation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
