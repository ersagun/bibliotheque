(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('AuteurDetailController', AuteurDetailController);

    AuteurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Auteur', 'Livre'];

    function AuteurDetailController($scope, $rootScope, $stateParams, previousState, entity, Auteur, Livre) {
        var vm = this;

        vm.auteur = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bibliothequeApp:auteurUpdate', function(event, result) {
            vm.auteur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
