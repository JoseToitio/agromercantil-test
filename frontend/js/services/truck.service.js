(function() {
    'use strict';

    angular
        .module('truckManagementApp')
        .factory('TruckService', TruckService);

    TruckService.$inject = ['$http'];

    function TruckService($http) {
        var API_URL = 'http://localhost:8080/api/trucks';

        var service = {
            findAll: findAll,
            findById: findById,
            save: save,
            update: update
        };

        return service;

        /**
         * Busca todos os caminhões cadastrados
         * @returns {Promise} Promessa com a lista de caminhões
         */
        function findAll() {
            return $http.get(API_URL)
                .then(function(response) {
                    return response.data;
                })
                .catch(function(error) {
                    console.error('Erro ao buscar caminhões:', error);
                    return Promise.reject(error);
                });
        }

        /**
         * Busca um caminhão pelo ID
         * @param {number} id ID do caminhão
         * @returns {Promise} Promessa com o caminhão encontrado
         */
        function findById(id) {
            return $http.get(API_URL + '/' + id)
                .then(function(response) {
                    return response.data;
                })
                .catch(function(error) {
                    console.error('Erro ao buscar caminhão:', error);
                    return Promise.reject(error);
                });
        }

        /**
         * Cadastra um novo caminhão
         * @param {Object} truck Dados do caminhão
         * @returns {Promise} Promessa com o caminhão cadastrado
         */
        function save(truck) {
            return $http.post(API_URL, truck)
                .then(function(response) {
                    return response.data;
                })
                .catch(function(error) {
                    console.error('Erro ao cadastrar caminhão:', error);
                    return Promise.reject(error);
                });
        }

        /**
         * Atualiza os dados de um caminhão existente
         * @param {number} id ID do caminhão
         * @param {Object} truck Novos dados do caminhão
         * @returns {Promise} Promessa com o caminhão atualizado
         */
        function update(id, truck) {
            return $http.put(API_URL + '/' + id, truck)
                .then(function(response) {
                    return response.data;
                })
                .catch(function(error) {
                    console.error('Erro ao atualizar caminhão:', error);
                    return Promise.reject(error);
                });
        }
    }
})();