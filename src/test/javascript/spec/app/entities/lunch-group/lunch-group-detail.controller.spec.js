'use strict';

describe('Controller Tests', function() {

    describe('LunchGroup Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLunchGroup, MockLunchEvent, MockPerson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLunchGroup = jasmine.createSpy('MockLunchGroup');
            MockLunchEvent = jasmine.createSpy('MockLunchEvent');
            MockPerson = jasmine.createSpy('MockPerson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LunchGroup': MockLunchGroup,
                'LunchEvent': MockLunchEvent,
                'Person': MockPerson
            };
            createController = function() {
                $injector.get('$controller')("LunchGroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'luuncherApp:lunchGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
