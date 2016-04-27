'use strict';

describe('Controller Tests', function() {

    describe('LunchLocation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLunchLocation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLunchLocation = jasmine.createSpy('MockLunchLocation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LunchLocation': MockLunchLocation
            };
            createController = function() {
                $injector.get('$controller')("LunchLocationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'luuncherApp:lunchLocationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
