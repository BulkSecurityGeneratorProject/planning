'use strict';

describe('Controller Tests', function() {

    describe('Task Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTask, MockUser, MockSecurityReview, MockTc, MockNote, MockLink, MockProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTask = jasmine.createSpy('MockTask');
            MockUser = jasmine.createSpy('MockUser');
            MockSecurityReview = jasmine.createSpy('MockSecurityReview');
            MockTc = jasmine.createSpy('MockTc');
            MockNote = jasmine.createSpy('MockNote');
            MockLink = jasmine.createSpy('MockLink');
            MockProject = jasmine.createSpy('MockProject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Task': MockTask,
                'User': MockUser,
                'SecurityReview': MockSecurityReview,
                'Tc': MockTc,
                'Note': MockNote,
                'Link': MockLink,
                'Project': MockProject
            };
            createController = function() {
                $injector.get('$controller')("TaskDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'planningApp:taskUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
