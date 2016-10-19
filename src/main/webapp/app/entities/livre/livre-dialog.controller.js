(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('LivreDialogController', LivreDialogController);

    LivreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Livre', 'Oeuvre', 'Auteur'];

    function LivreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Livre, Oeuvre, Auteur) {
        var vm = this;

        vm.livre = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.oeuvres = Oeuvre.query({filter: 'livre-is-null'});
        $q.all([vm.livre.$promise, vm.oeuvres.$promise]).then(function() {
            if (!vm.livre.oeuvreId) {
                return $q.reject();
            }
            return Oeuvre.get({id : vm.livre.oeuvreId}).$promise;
        }).then(function(oeuvre) {
            vm.oeuvres.push(oeuvre);
        });
        vm.auteurs = Auteur.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.livre.id !== null) {
                Livre.update(vm.livre, onSaveSuccess, onSaveError);
            } else {
                Livre.save(vm.livre, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bibliothequeApp:livreUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateEdition = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
