package com.ztCoder.microservice.weather.service;

import com.ztCoder.microservice.weather.vo.WeatherResponse;

public interface WeatherDataService {
	
	/**
	 * 根据城市ID查询天气数据
	 * @author ztCoder
	 *
	 */
	WeatherResponse getDataByCityId(String cityId);
	
	/**
	 * 根据城市名称查询天气数据
	 * @author ztCoder
	 *
	 */
	WeatherResponse getDataByCityName(String cityName);

}
