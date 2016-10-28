(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('EmpruntDeleteController',EmpruntDeleteController);

    EmpruntDeleteController.$inject = ['$uibModalInstance','$scope','$state', 'entity', 'Emprunt'];

    function EmpruntDeleteController($uibModalInstance,$scope,$state,  entity, Emprunt) {
        var vm = this;

        vm.emprunt = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            $scope.changeState = function () {
                $state.go('exemplaire-detail.edit', {id: vm.emprunt.exemplaireId});
            };

            Emprunt.delete({id: id},
                function () {
                    $scope.changeState();
                });
            $uibModalInstance.close(true);



        }
    }
})();
