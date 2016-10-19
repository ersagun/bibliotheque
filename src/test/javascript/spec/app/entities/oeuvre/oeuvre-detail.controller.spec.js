'use strict';

describe('Controller Tests', function() {

    describe('Oeuvre Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOeuvre, MockReservation, MockExemplaire, MockLivre, MockMagazine;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOeuvre = jasmine.createSpy('MockOeuvre');
            MockReservation = jasmine.createSpy('MockReservation');
            MockExemplaire = jasmine.createSpy('MockExemplaire');
            MockLivre = jasmine.createSpy('MockLivre');
            MockMagazine = jasmine.createSpy('MockMagazine');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Oeuvre': MockOeuvre,
                'Reservation': MockReservation,
                'Exemplaire': MockExemplaire,
                'Livre': MockLivre,
                'Magazine': MockMagazine
            };
            createController = function() {
                $injector.get('$controller')("OeuvreDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bibliothequeApp:oeuvreUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
