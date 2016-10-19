'use strict';

describe('Controller Tests', function() {

    describe('Usager Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUsager, MockReservation, MockEmprunt;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUsager = jasmine.createSpy('MockUsager');
            MockReservation = jasmine.createSpy('MockReservation');
            MockEmprunt = jasmine.createSpy('MockEmprunt');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Usager': MockUsager,
                'Reservation': MockReservation,
                'Emprunt': MockEmprunt
            };
            createController = function() {
                $injector.get('$controller')("UsagerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bibliothequeApp:usagerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
