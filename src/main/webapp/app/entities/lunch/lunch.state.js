(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lunch', {
            parent: 'entity',
            url: '/lunch',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'luuncherApp.lunch.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lunch/lunches.html',
                    controller: 'LunchController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lunch');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lunch-detail', {
            parent: 'entity',
            url: '/lunch/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'luuncherApp.lunch.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lunch/lunch-detail.html',
                    controller: 'LunchDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lunch');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Lunch', function($stateParams, Lunch) {
                    return Lunch.get({id : $stateParams.id});
                }]
            }
        })
        .state('lunch.new', {
            parent: 'lunch',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch/lunch-dialog.html',
                    controller: 'LunchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lunchId: null,
                                start: null,
                                end: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lunch', null, { reload: true });
                }, function() {
                    $state.go('lunch');
                });
            }]
        })
        .state('lunch.edit', {
            parent: 'lunch',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch/lunch-dialog.html',
                    controller: 'LunchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lunch', function(Lunch) {
                            return Lunch.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('lunch', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lunch.delete', {
            parent: 'lunch',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch/lunch-delete-dialog.html',
                    controller: 'LunchDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Lunch', function(Lunch) {
                            return Lunch.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('lunch', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
