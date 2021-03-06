'use strict';

angular.module('planningApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('task', {
                parent: 'entity',
                url: '/tasks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Tasks'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/task/tasks.html',
                        controller: 'TaskController'
                    }
                },
                resolve: {
                }
            })
            .state('task.detail', {
                parent: 'entity',
                url: '/task/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Task'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/task/task-detail.html',
                        controller: 'TaskDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Task', function($stateParams, Task) {
                        return Task.get({id : $stateParams.id});
                    }]
                }
            })
            .state('task.new', {
                parent: 'task',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/task/task-dialog.html',
                        controller: 'TaskDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    status: null,
                                    criticalness: null,
                                    startDate: null,
                                    endDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('task', null, { reload: true });
                    }, function() {
                        $state.go('task');
                    })
                }]
            })
            .state('task.edit', {
                parent: 'task',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/task/task-dialog.html',
                        controller: 'TaskDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Task', function(Task) {
                                return Task.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('task', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('task.delete', {
                parent: 'task',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/task/task-delete-dialog.html',
                        controller: 'TaskDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Task', function(Task) {
                                return Task.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('task', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
