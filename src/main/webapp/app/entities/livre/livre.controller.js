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
               // console.log(vm.livres)
            });
        }

        $scope.filteredTodos = []
            ,$scope.currentPage = 1
            ,$scope.numPerPage = 10
            ,$scope.maxSize = 5;

        $scope.makeTodos = function() {
            $scope.todos = [];
            for (var i=1;i<=1000;i++) {
                $scope.todos.push({ text:'todo '+i, done:false});
            }
        };
        $scope.makeTodos();

        $scope.numPages = function () {
            return Math.ceil($scope.todos.length / $scope.numPerPage);
        };

        $scope.$watch('currentPage + numPerPage', function() {
            var begin = (($scope.currentPage - 1) * $scope.numPerPage)
                , end = begin + $scope.numPerPage;

            $scope.filteredTodos = $scope.todos.slice(begin, end);
        });

    }
})();
