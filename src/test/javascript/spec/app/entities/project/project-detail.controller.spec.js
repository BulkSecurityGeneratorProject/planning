'use strict';

describe('Controller Tests', function() {

    describe('Project Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProject, MockUser, MockTask, MockLink;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProject = jasmine.createSpy('MockProject');
            MockUser = jasmine.createSpy('MockUser');
            MockTask = jasmine.createSpy('MockTask');
            MockLink = jasmine.createSpy('MockLink');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Project': MockProject,
                'User': MockUser,
                'Task': MockTask,
                'Link': MockLink
            };
            createController = function() {
                $injector.get('$controller')("ProjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'planningApp:projectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
