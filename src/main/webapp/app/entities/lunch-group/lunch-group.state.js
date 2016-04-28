(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lunch-group', {
            parent: 'entity',
            url: '/lunch-group',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'luuncherApp.lunchGroup.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lunch-group/lunch-groups.html',
                    controller: 'LunchGroupController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lunchGroup');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lunch-group-detail', {
            parent: 'entity',
            url: '/lunch-group/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'luuncherApp.lunchGroup.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lunch-group/lunch-group-detail.html',
                    controller: 'LunchGroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lunchGroup');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LunchGroup', function($stateParams, LunchGroup) {
                    return LunchGroup.get({id : $stateParams.id});
                }]
            }
        })
        .state('lunch-group.new', {
            parent: 'lunch-group',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-group/lunch-group-dialog.html',
                    controller: 'LunchGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lunch-group', null, { reload: true });
                }, function() {
                    $state.go('lunch-group');
                });
            }]
        })
        .state('lunch-group.edit', {
            parent: 'lunch-group',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-group/lunch-group-dialog.html',
                    controller: 'LunchGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LunchGroup', function(LunchGroup) {
                            return LunchGroup.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('lunch-group', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lunch-group.delete', {
            parent: 'lunch-group',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-group/lunch-group-delete-dialog.html',
                    controller: 'LunchGroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LunchGroup', function(LunchGroup) {
                            return LunchGroup.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('lunch-group', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
