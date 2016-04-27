(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchLocationDialogController', LunchLocationDialogController);

    LunchLocationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LunchLocation'];

    function LunchLocationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LunchLocation) {
        var vm = this;
        vm.lunchLocation = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('luuncherApp:lunchLocationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.lunchLocation.id !== null) {
                LunchLocation.update(vm.lunchLocation, onSaveSuccess, onSaveError);
            } else {
                LunchLocation.save(vm.lunchLocation, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
