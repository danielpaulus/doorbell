'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute','ngCookies',
  'myApp.version','myApp.controlview',
  'ngFileUpload', 'ui.grid',
  'ui.grid.selection','ui.grid.edit',
  'textAngular','angular.atmosphere','ngAudio'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/controlview'});
}]);
