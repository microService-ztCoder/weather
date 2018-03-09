package com.ztCoder.microservice.weather.service.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztCoder.microservice.weather.service.WeatherDataService;
import com.ztCoder.microservice.weather.vo.WeatherResponse;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	private final static Logger logger=LoggerFactory.getLogger(WeatherDataServiceImpl.class);
	
	private static final String WEATHER_URI="http://wthrcdn.etouch.cn/weather_mini?";
	
	private static final long TIME_OUT=1800L;//常用是1800s
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
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
		String strBody=null;
		
		ValueOperations<String, String> ops=stringRedisTemplate.opsForValue();
		//先查缓存，缓存有的取缓存中的数据
		if(stringRedisTemplate.hasKey(uri)){
			logger.info("Redis has data");
			strBody=ops.get(uri);
		}else{
			ResponseEntity<String> respString=restTemplate.getForEntity(uri, String.class);
			
			if(respString.getStatusCodeValue()==200){
				strBody=respString.getBody();
			}
			logger.info("Redis  don`t has data");
			
			//数据写入缓存
			ops.set(uri, strBody,TIME_OUT,TimeUnit.SECONDS);
		}
		
		try {
			weather=mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error!",e);
		}
		
		return weather;
	}

}
