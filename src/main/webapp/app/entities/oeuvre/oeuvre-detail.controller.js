(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('OeuvreDetailController', OeuvreDetailController);

    OeuvreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Oeuvre', 'Reservation', 'Exemplaire', 'Livre', 'Magazine'];

    function OeuvreDetailController($scope, $rootScope, $stateParams, previousState, entity, Oeuvre, Reservation, Exemplaire, Livre, Magazine) {
        var vm = this;

        vm.oeuvre = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bibliothequeApp:oeuvreUpdate', function(event, result) {
            vm.oeuvre = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
