package com.wuyuan.spring.cloud.weather.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wuyuan.spring.cloud.weather.service.CityListService;
import com.wuyuan.spring.cloud.weather.service.WeatherDataCollectionService;
import com.wuyuan.spring.cloud.weather.vo.City;

/**
 * Weather Data Sync Job.
 * 
 * @since 1.0.0 2017年11月23日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public class WeatherDataSyncJob extends QuartzJobBean {
	
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);  

	@Autowired
	private CityListService citylistservice;
	@Autowired
	private WeatherDataCollectionService weatherDataCollectionService;
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Weather Data Sync Job. Start！");
		// 获取城市ID列表
		// TODO 改为由城市数据API微服务来提供数据
		List<City> cityList = null;
		
		try {
			
			// TODO 改为由城市数据API微服务提供数据
//			cityList = new ArrayList<>();
//			City city = new City();
//			city.setCityId("101280601");
//			cityList.add(city);
			cityList=citylistservice.listCity();
			
		} catch (Exception e) {
			logger.error("Exception!", e);
		}
		
		// 遍历城市ID获取天气
		for (City city : cityList) {
			String cityId = city.getCityId();
			logger.info("Weather Data Sync Job, cityId:" + cityId);
			
			weatherDataCollectionService.syncDateByCityId(cityId);
		}
		
		logger.info("Weather Data Sync Job. End！");
	}

}
