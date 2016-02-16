'use strict';

angular.module('planningApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tc', {
                parent: 'entity',
                url: '/tcs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Tcs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tc/tcs.html',
                        controller: 'TcController'
                    }
                },
                resolve: {
                }
            })
            .state('tc.detail', {
                parent: 'entity',
                url: '/tc/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Tc'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tc/tc-detail.html',
                        controller: 'TcDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Tc', function($stateParams, Tc) {
                        return Tc.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tc.new', {
                parent: 'tc',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tc/tc-dialog.html',
                        controller: 'TcDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    plannedDate: null,
                                    startDate: null,
                                    endDate: null,
                                    status: null,
                                    dummy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tc', null, { reload: true });
                    }, function() {
                        $state.go('tc');
                    })
                }]
            })
            .state('tc.edit', {
                parent: 'tc',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tc/tc-dialog.html',
                        controller: 'TcDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Tc', function(Tc) {
                                return Tc.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tc', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tc.delete', {
                parent: 'tc',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/tc/tc-delete-dialog.html',
                        controller: 'TcDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Tc', function(Tc) {
                                return Tc.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tc', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
