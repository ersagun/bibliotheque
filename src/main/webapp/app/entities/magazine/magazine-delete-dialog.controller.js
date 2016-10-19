(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('MagazineDeleteController',MagazineDeleteController);

    MagazineDeleteController.$inject = ['$uibModalInstance', 'entity', 'Magazine'];

    function MagazineDeleteController($uibModalInstance, entity, Magazine) {
        var vm = this;

        vm.magazine = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Magazine.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
