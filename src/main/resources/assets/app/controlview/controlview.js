'use strict';

angular.module('myApp.controlview', ['ngRoute', 'angular.atmosphere'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/controlview', {
                    templateUrl: 'controlview/controlview.html',
                    controller: 'ControlViewCtrl'
                });
            }])

        .controller('ControlViewCtrl', ['$scope', '$http', '$log', 'atmosphereService', function ($scope, $http, $log, atmosphereService, settingsService) {
                $scope.model = {
                    transport: 'websocket',
                    messages: []
                };


                $scope.ringDoorBell = function () {
                    $http.get('api/bell/ring');
                };
                 $http.get('api/bell/silent').then(function(resp){
                    
                $scope.silentMode=resp.data;     
                });

                $scope.updateSilentMode = function () {
                    $scope.silentMode = $http.post('api/bell/silent', $scope.silentMode.toString());
                };
                var socket;

                var request = {
                    url: '/websocket/round',
                    contentType: 'application/json',
                    logLevel: 'debug',
                    transport: 'websocket',
                    trackMessageLength: true,
                    reconnectInterval: 5000,
                    enableXDR: true,
                    timeout: 60000
                };

                request.onOpen = function (response) {
                    $log.info("yaaaa open");
                    $scope.model.transport = response.transport;
                    $scope.model.connected = true;
                    $scope.model.content = 'Atmosphere connected using ' + response.transport;

                };

                request.onClientTimeout = function (response) {
                    $scope.model.content = 'Client closed the connection after a timeout. Reconnecting in ' + request.reconnectInterval;
                    $scope.model.connected = false;

                };

                request.onReopen = function (response) {
                    $scope.model.connected = true;
                    $scope.model.content = 'Atmosphere re-connected using ' + response.transport;
                };

                //For demonstration of how you can customize the fallbackTransport using the onTransportFailure function
                request.onTransportFailure = function (errorMsg, request) {
                    atmosphere.util.info(errorMsg);
                    request.fallbackTransport = 'long-polling';
                    $scope.model.header = 'Atmosphere Chat. Default transport is WebSocket, fallback is ' + request.fallbackTransport;
                };

                request.onMessage = function (response) {
                    $log.info(response);
                    var responseText = response.responseBody;
                    try {
                        if ("X" !== responseText) {
                            var message = atmosphere.util.parseJSON(responseText);
                            if (message.hasOwnProperty("silent")) {
                                $scope.silentMode=message.silent;
                            }
                        }
                    } catch (e) {
                        console.error("Error parsing JSON: ", responseText);
                        throw e;
                    }
                };

                request.onClose = function (response) {
                    $scope.model.connected = false;
                    $scope.model.content = 'Server closed the connection after a timeout';
                };

                request.onError = function (response) {
                    $scope.model.content = "Sorry, but there's some problem with your socket or the server is down";
                    $scope.model.logged = false;
                };

                request.onReconnect = function (request, response) {
                    $scope.model.content = 'Connection lost. Trying to reconnect ' + request.reconnectInterval;
                    $scope.model.connected = false;
                };

                socket = atmosphereService.subscribe(request);




            }]);
