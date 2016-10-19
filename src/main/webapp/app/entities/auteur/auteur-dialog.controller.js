(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('AuteurDialogController', AuteurDialogController);

    AuteurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Auteur', 'Livre'];

    function AuteurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Auteur, Livre) {
        var vm = this;

        vm.auteur = entity;
        vm.clear = clear;
        vm.save = save;
        vm.livres = Livre.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.auteur.id !== null) {
                Auteur.update(vm.auteur, onSaveSuccess, onSaveError);
            } else {
                Auteur.save(vm.auteur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bibliothequeApp:auteurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
