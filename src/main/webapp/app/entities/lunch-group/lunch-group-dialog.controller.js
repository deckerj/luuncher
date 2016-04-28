(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchGroupDialogController', LunchGroupDialogController);

    LunchGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LunchGroup', 'LunchEvent', 'Person'];

    function LunchGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LunchGroup, LunchEvent, Person) {
        var vm = this;
        vm.lunchGroup = entity;
        vm.lunchevents = LunchEvent.query();
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('luuncherApp:lunchGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.lunchGroup.id !== null) {
                LunchGroup.update(vm.lunchGroup, onSaveSuccess, onSaveError);
            } else {
                LunchGroup.save(vm.lunchGroup, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
