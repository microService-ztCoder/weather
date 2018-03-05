package com.ztCoder.microservice.weather.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztCoder.microservice.weather.service.WeatherDataService;
import com.ztCoder.microservice.weather.vo.WeatherResponse;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	private static final String WEATHER_URI="http://wthrcdn.etouch.cn/weather_mini?";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		// TODO Auto-generated method stub
		String uri=WEATHER_URI+"citykey="+cityId;
		return this.doGetWeather(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		// TODO Auto-generated method stub
		String uri=WEATHER_URI+"city="+cityName;
		return this.doGetWeather(uri);
	}
	
	private WeatherResponse doGetWeather(String uri){
		WeatherResponse weather=null;
		ObjectMapper mapper=new ObjectMapper();
		ResponseEntity<String> respString=restTemplate.getForEntity(uri, String.class);
		
		String strBody=null;
		if(respString.getStatusCodeValue()==200){
			strBody=respString.getBody();
		}
		try {
			weather=mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weather;
	}

}
