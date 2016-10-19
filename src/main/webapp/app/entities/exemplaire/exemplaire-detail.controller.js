(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('ExemplaireDetailController', ExemplaireDetailController);

    ExemplaireDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Exemplaire', 'Emprunt', 'Oeuvre'];

    function ExemplaireDetailController($scope, $rootScope, $stateParams, previousState, entity, Exemplaire, Emprunt, Oeuvre) {
        var vm = this;

        vm.exemplaire = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bibliothequeApp:exemplaireUpdate', function(event, result) {
            vm.exemplaire = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
