(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchDialogController', LunchDialogController);

    LunchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lunch', 'Person'];

    function LunchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Lunch, Person) {
        var vm = this;
        vm.lunch = entity;
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('luuncherApp:lunchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.lunch.id !== null) {
                Lunch.update(vm.lunch, onSaveSuccess, onSaveError);
            } else {
                Lunch.save(vm.lunch, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.start = false;
        vm.datePickerOpenStatus.end = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
