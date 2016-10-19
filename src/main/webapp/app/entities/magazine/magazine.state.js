(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('magazine', {
            parent: 'entity',
            url: '/magazine',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Magazines'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/magazine/magazines.html',
                    controller: 'MagazineController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('magazine-detail', {
            parent: 'entity',
            url: '/magazine/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Magazine'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/magazine/magazine-detail.html',
                    controller: 'MagazineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Magazine', function($stateParams, Magazine) {
                    return Magazine.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'magazine',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('magazine-detail.edit', {
            parent: 'magazine-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/magazine/magazine-dialog.html',
                    controller: 'MagazineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Magazine', function(Magazine) {
                            return Magazine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('magazine.new', {
            parent: 'magazine',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/magazine/magazine-dialog.html',
                    controller: 'MagazineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                parution: null,
                                periodicite: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('magazine', null, { reload: 'magazine' });
                }, function() {
                    $state.go('magazine');
                });
            }]
        })
        .state('magazine.edit', {
            parent: 'magazine',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/magazine/magazine-dialog.html',
                    controller: 'MagazineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Magazine', function(Magazine) {
                            return Magazine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('magazine', null, { reload: 'magazine' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('magazine.delete', {
            parent: 'magazine',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/magazine/magazine-delete-dialog.html',
                    controller: 'MagazineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Magazine', function(Magazine) {
                            return Magazine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('magazine', null, { reload: 'magazine' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
