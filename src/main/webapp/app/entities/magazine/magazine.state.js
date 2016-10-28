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
            url: '/magazine?page&sort&search',
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
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
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
