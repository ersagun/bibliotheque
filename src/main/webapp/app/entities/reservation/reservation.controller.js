(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('ReservationController', ReservationController);

    ReservationController.$inject = ['$scope', '$state', 'Reservation','Usager','Oeuvre'];

    function ReservationController ($scope, $state, Reservation,Usager, Oeuvre) {
        var vm = this;

        vm.reservations = [];

        loadAll();

        function loadAll() {
            Reservation.query(function(result) {
                result.forEach(function(item){
                    Usager.get({id: item.usagerId}).$promise.then(function(todo) {
                        item.usagerNom=todo.nom+' ' + todo.prenom;
                    });

                    Oeuvre.get({id: item.oeuvreId}).$promise.then(function(todo) {
                        item.oeuvreTitre=todo.titre;
                    });

                });
                vm.reservations = result;
            });
        }
    }
})();
