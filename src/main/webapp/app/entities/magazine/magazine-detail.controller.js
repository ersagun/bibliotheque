(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .controller('MagazineDetailController', MagazineDetailController);

    MagazineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Magazine', 'Oeuvre'];

    function MagazineDetailController($scope, $rootScope, $stateParams, previousState, entity, Magazine, Oeuvre) {
        var vm = this;

        vm.magazine = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bibliothequeApp:magazineUpdate', function(event, result) {
            vm.magazine = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
