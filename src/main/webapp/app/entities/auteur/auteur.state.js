(function() {
    'use strict';

    angular
        .module('bibliothequeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('auteur', {
            parent: 'entity',
            url: '/auteur',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Auteurs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auteur/auteurs.html',
                    controller: 'AuteurController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('auteur-detail', {
            parent: 'entity',
            url: '/auteur/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Auteur'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auteur/auteur-detail.html',
                    controller: 'AuteurDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Auteur', function($stateParams, Auteur) {
                    return Auteur.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'auteur',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('auteur-detail.edit', {
            parent: 'auteur-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auteur/auteur-dialog.html',
                    controller: 'AuteurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Auteur', function(Auteur) {
                            return Auteur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auteur.new', {
            parent: 'auteur',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auteur/auteur-dialog.html',
                    controller: 'AuteurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('auteur', null, { reload: 'auteur' });
                }, function() {
                    $state.go('auteur');
                });
            }]
        })
        .state('auteur.edit', {
            parent: 'auteur',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auteur/auteur-dialog.html',
                    controller: 'AuteurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Auteur', function(Auteur) {
                            return Auteur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auteur', null, { reload: 'auteur' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auteur.delete', {
            parent: 'auteur',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auteur/auteur-delete-dialog.html',
                    controller: 'AuteurDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Auteur', function(Auteur) {
                            return Auteur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auteur', null, { reload: 'auteur' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
