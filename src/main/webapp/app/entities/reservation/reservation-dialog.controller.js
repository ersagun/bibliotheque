(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('ReservationDialogController', ReservationDialogController);

    ReservationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reservation', 'Usager', 'Oeuvre'];

    function ReservationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reservation, Usager, Oeuvre) {
        var vm = this;
        var isReserved={reserved:""};
        vm.reservation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.usagers = Usager.query();
       // vm.oeuvres = Oeuvre.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            Reservation.isReserved(vm.reservation,saveCheck);

        }
        function saveCheck(result){
            if(result.reserved){
                window.alert("Usager a déjà reservé cet oeuvre");
                }else {
                    vm.isSaving = true;
                    if (vm.reservation.id !== null) {
                        Reservation.update(vm.reservation, onSaveSuccess, onSaveError);
                    } else {
                        Reservation.save(vm.reservation, onSaveSuccess, onSaveError);
                    }
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bibliothequeApp:reservationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDemande = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
