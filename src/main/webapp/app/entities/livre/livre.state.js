(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('livre', {
            parent: 'entity',
            url: '/livre',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Livres'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/livre/livres.html',
                    controller: 'LivreController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('livre-detail', {
            parent: 'entity',
            url: '/livre/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Livre'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/livre/livre-detail.html',
                    controller: 'LivreDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Livre', function($stateParams, Livre) {
                    return Livre.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'livre',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('livre-detail.edit', {
            parent: 'livre-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/livre/livre-dialog.html',
                    controller: 'LivreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Livre', function(Livre) {
                            return Livre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('livre.new', {
            parent: 'livre',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/livre/livre-dialog.html',
                    controller: 'LivreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateEdition: null,
                                resume: null,
                                url: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('livre', null, { reload: 'livre' });
                }, function() {
                    $state.go('livre');
                });
            }]
        })
        .state('livre.edit', {
            parent: 'livre',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/livre/livre-dialog.html',
                    controller: 'LivreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Livre', function(Livre) {
                            return Livre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('livre', null, { reload: 'livre' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('livre.delete', {
            parent: 'livre',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/livre/livre-delete-dialog.html',
                    controller: 'LivreDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Livre', function(Livre) {
                            return Livre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('livre', null, { reload: 'livre' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
