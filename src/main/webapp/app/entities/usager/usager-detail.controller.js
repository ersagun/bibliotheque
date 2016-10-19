(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('UsagerDetailController', UsagerDetailController);

    UsagerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Usager', 'Reservation', 'Emprunt'];

    function UsagerDetailController($scope, $rootScope, $stateParams, previousState, entity, Usager, Reservation, Emprunt) {
        var vm = this;

        vm.usager = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bibliothequeApp:usagerUpdate', function(event, result) {
            vm.usager = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
