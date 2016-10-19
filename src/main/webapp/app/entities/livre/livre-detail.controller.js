(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('LivreDetailController', LivreDetailController);

    LivreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Livre', 'Oeuvre', 'Auteur'];

    function LivreDetailController($scope, $rootScope, $stateParams, previousState, entity, Livre, Oeuvre, Auteur) {
        var vm = this;

        vm.livre = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bibliothequeApp:livreUpdate', function(event, result) {
            vm.livre = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
