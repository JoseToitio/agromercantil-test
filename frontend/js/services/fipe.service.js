(function() {
    'use strict';

    angular
        .module('truckManagementApp')
        .factory('FipeService', FipeService);

    FipeService.$inject = ['$http'];

    function FipeService($http) {
        var API_URL = 'http://localhost:8080/api/fipe';

        var service = {
            getBrands: getBrands,
            getModels: getModels,
            getYears: getYears,
            getFipeInfo: getFipeInfo
        };

        return service;

        function getBrands() {
            return $http.get(API_URL + '/brands')
                .then(response => response.data)
                .catch(error => Promise.reject(error));
        }

        function getModels(brandCode) {
            return $http.get(API_URL + '/brands/' + brandCode + '/models')
                .then(response => response.data)
                .catch(error => Promise.reject(error));
        }

        function getYears(brandCode, modelCode) {
            return $http.get(API_URL + '/brands/' + brandCode + '/models/' + modelCode + '/years')
                .then(response => response.data)
                .catch(error => Promise.reject(error));
        }

        function getFipeInfo(brandCode, modelCode, yearCode) {
            var params = {
                brand: brandCode,
                model: modelCode,
                year: yearCode
            };

            return $http.get(API_URL + '/validate', { params: params })
                .then(response => {
                    console.log(response.data)
                    return response.data;
                })
                .catch(error => Promise.reject(error));
        }
    }
})();
