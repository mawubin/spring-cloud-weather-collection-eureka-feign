package com.wuyuan.spring.cloud.weather.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.wuyuan.spring.cloud.weather.vo.City;


@FeignClient("weather-city-server-eureka")
public interface CityListService {

		@GetMapping("/cities")
		 List<City>  listCity();
}
