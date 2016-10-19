(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('oeuvre', {
            parent: 'entity',
            url: '/oeuvre',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Oeuvres'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/oeuvre/oeuvres.html',
                    controller: 'OeuvreController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('oeuvre-detail', {
            parent: 'entity',
            url: '/oeuvre/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Oeuvre'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/oeuvre/oeuvre-detail.html',
                    controller: 'OeuvreDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Oeuvre', function($stateParams, Oeuvre) {
                    return Oeuvre.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'oeuvre',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('oeuvre-detail.edit', {
            parent: 'oeuvre-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oeuvre/oeuvre-dialog.html',
                    controller: 'OeuvreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Oeuvre', function(Oeuvre) {
                            return Oeuvre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('oeuvre.new', {
            parent: 'oeuvre',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oeuvre/oeuvre-dialog.html',
                    controller: 'OeuvreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titre: null,
                                editeur: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('oeuvre', null, { reload: 'oeuvre' });
                }, function() {
                    $state.go('oeuvre');
                });
            }]
        })
        .state('oeuvre.edit', {
            parent: 'oeuvre',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oeuvre/oeuvre-dialog.html',
                    controller: 'OeuvreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Oeuvre', function(Oeuvre) {
                            return Oeuvre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('oeuvre', null, { reload: 'oeuvre' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('oeuvre.delete', {
            parent: 'oeuvre',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oeuvre/oeuvre-delete-dialog.html',
                    controller: 'OeuvreDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Oeuvre', function(Oeuvre) {
                            return Oeuvre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('oeuvre', null, { reload: 'oeuvre' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
