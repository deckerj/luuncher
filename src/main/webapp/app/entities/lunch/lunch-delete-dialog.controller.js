(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchDeleteController',LunchDeleteController);

    LunchDeleteController.$inject = ['$uibModalInstance', 'entity', 'Lunch'];

    function LunchDeleteController($uibModalInstance, entity, Lunch) {
        var vm = this;
        vm.lunch = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Lunch.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
