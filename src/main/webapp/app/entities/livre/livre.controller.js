(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('LivreController', LivreController);

    LivreController.$inject = ['$scope', '$state', 'Livre'];

    function LivreController ($scope, $state, Livre) {
        var vm = this;
        
        vm.livres = [];

        loadAll();

        function loadAll() {
            Livre.query(function(result) {
                vm.livres = result;
            });
        }
    }
})();
