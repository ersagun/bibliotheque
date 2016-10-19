(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('OeuvreDialogController', OeuvreDialogController);

    OeuvreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Oeuvre', 'Reservation', 'Exemplaire', 'Livre', 'Magazine'];

    function OeuvreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Oeuvre, Reservation, Exemplaire, Livre, Magazine) {
        var vm = this;

        vm.oeuvre = entity;
        vm.clear = clear;
        vm.save = save;
        vm.reservations = Reservation.query();
        vm.exemplaires = Exemplaire.query();
        vm.livres = Livre.query();
        vm.magazines = Magazine.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.oeuvre.id !== null) {
                Oeuvre.update(vm.oeuvre, onSaveSuccess, onSaveError);
            } else {
                Oeuvre.save(vm.oeuvre, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bibliothequeApp:oeuvreUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
