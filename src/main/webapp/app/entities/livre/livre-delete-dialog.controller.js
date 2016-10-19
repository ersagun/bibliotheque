(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('LivreDeleteController',LivreDeleteController);

    LivreDeleteController.$inject = ['$uibModalInstance', 'entity', 'Livre'];

    function LivreDeleteController($uibModalInstance, entity, Livre) {
        var vm = this;

        vm.livre = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Livre.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
