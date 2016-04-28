'use strict';

describe('Controller Tests', function() {

    describe('LunchEvent Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLunchEvent, MockLunchLocation, MockLunchGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLunchEvent = jasmine.createSpy('MockLunchEvent');
            MockLunchLocation = jasmine.createSpy('MockLunchLocation');
            MockLunchGroup = jasmine.createSpy('MockLunchGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LunchEvent': MockLunchEvent,
                'LunchLocation': MockLunchLocation,
                'LunchGroup': MockLunchGroup
            };
            createController = function() {
                $injector.get('$controller')("LunchEventDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'luuncherApp:lunchEventUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
