(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .controller('LunchEventDialogController', LunchEventDialogController);

    LunchEventDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LunchEvent', 'LunchLocation', 'LunchGroup'];

    function LunchEventDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LunchEvent, LunchLocation, LunchGroup) {
        var vm = this;
        vm.lunchEvent = entity;
        vm.lunchlocations = LunchLocation.query({filter: 'lunchevent-is-null'});
        $q.all([vm.lunchEvent.$promise, vm.lunchlocations.$promise]).then(function() {
            if (!vm.lunchEvent.lunchLocationId) {
                return $q.reject();
            }
            return LunchLocation.get({id : vm.lunchEvent.lunchLocationId}).$promise;
        }).then(function(lunchLocation) {
            vm.lunchlocations.push(lunchLocation);
        });
        vm.lunchgroups = LunchGroup.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('luuncherApp:lunchEventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.lunchEvent.id !== null) {
                LunchEvent.update(vm.lunchEvent, onSaveSuccess, onSaveError);
            } else {
                LunchEvent.save(vm.lunchEvent, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.startDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
