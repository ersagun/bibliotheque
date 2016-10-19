(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('MagazineDialogController', MagazineDialogController);

    MagazineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Magazine', 'Oeuvre'];

    function MagazineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Magazine, Oeuvre) {
        var vm = this;

        vm.magazine = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.oeuvres = Oeuvre.query({filter: 'magazine-is-null'});
        $q.all([vm.magazine.$promise, vm.oeuvres.$promise]).then(function() {
            if (!vm.magazine.oeuvreId) {
                return $q.reject();
            }
            return Oeuvre.get({id : vm.magazine.oeuvreId}).$promise;
        }).then(function(oeuvre) {
            vm.oeuvres.push(oeuvre);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.magazine.id !== null) {
                Magazine.update(vm.magazine, onSaveSuccess, onSaveError);
            } else {
                Magazine.save(vm.magazine, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bibliothequeApp:magazineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.parution = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
